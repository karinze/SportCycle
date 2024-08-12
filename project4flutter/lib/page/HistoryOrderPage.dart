import 'package:flutter/material.dart';
import 'package:project4flutter/model/Items.dart';
import 'package:project4flutter/model/Orders.dart';
import 'package:project4flutter/model/OrderItems.dart';
import 'package:project4flutter/service/ItemsService.dart';
import 'package:project4flutter/service/OrdersService.dart';
import 'package:project4flutter/service/OrderItemsService.dart';
import 'package:project4flutter/service/UsersService.dart';
import '../model/Users.dart';
import '../utils/color.dart';
import 'LoginPage.dart';

class OrderHistoryPage extends StatefulWidget {
  @override
  _OrderHistoryPageState createState() => _OrderHistoryPageState();
}

class _OrderHistoryPageState extends State<OrderHistoryPage> {
  List<Orders> _orders = [];
  List<OrderItems> _orderItems = [];
  bool _isLoggedIn = false;
  bool _isLoading = true;
  String? _userId;

  @override
  void initState() {
    super.initState();
    _checkLoginStatus();
  }

  Future<void> _checkLoginStatus() async {
    UsersService usersService = UsersService();
    bool isLoggedIn = await usersService.isLoggedIn();
    if (isLoggedIn) {
      _userId = await usersService.getUserId();
      await _fetchOrders();
    }
    setState(() {
      _isLoggedIn = isLoggedIn;
      _isLoading = false; // Stop loading once data is fetched
    });
  }

  Future<void> _fetchOrders() async {
    if (_userId == null) return;
    OrdersService ordersService = OrdersService();
    List<Orders> orders = await ordersService.findUser(_userId!);
    setState(() {
      _orders = orders;
    });
  }

  Future<void> _fetchOrderItems(int orderId) async {
    OrderItemsService orderItemsService = OrderItemsService();
    Orders order = await OrdersService().findOne(orderId);
    List<OrderItems> orderItems = await orderItemsService.findOrder(order);
    setState(() {
      _orderItems = orderItems;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: Center(
          child: Text(
            'Order History',
            style: TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.w600,
              color: Colors.deepPurple,
            ),
          ),
        ),
        backgroundColor: Colors.white,
        elevation: 0,
      ),
      body: _isLoading ? _buildLoadingIndicator() : _isLoggedIn ? _buildOrderList() : _buildLoginButton(),
    );
  }

  Widget _buildLoadingIndicator() {
    return Center(
      child: CircularProgressIndicator(
        color: Colors.deepPurple,
      ),
    );
  }

  Widget _buildOrderList() {
    if (_orders.isEmpty) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.remove_shopping_cart_outlined,
              size: 100,
              color: Colors.grey[350],
            ),
            SizedBox(height: 20),
            Text(
              'No Orders Found',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.w500,
                color: Colors.grey[600],
              ),
            ),
          ],
        ),
      );
    }

    return ListView.builder(
      itemCount: _orders.length,
      itemBuilder: (context, index) {
        final order = _orders[index];
        final reversedIndex = _orders.length - index; // Reverse the ID

        return Card(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(16),
          ),
          elevation: 6,
          margin: EdgeInsets.symmetric(horizontal: 16, vertical: 10),
          child: ListTile(
            contentPadding: EdgeInsets.all(20),
            title: Text(
              'Order #${reversedIndex}', // Display ID in decreasing order
              style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 20,
                color: Colors.black,
              ),
            ),
            subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                SizedBox(height: 8),
                Text(
                  'Total Amount: \$${order.totalAmount}',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 16,
                    color: Colors.grey[800],
                  ),
                ),
                Text(
                  'Order Date: ${order.orderDate}',
                  style: TextStyle(
                    fontSize: 16,
                    color: Colors.grey[800],
                  ),
                ),
                Text(
                  'Status: ${order.status}',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 16,
                    color: AppColor.mainColor,
                  ),
                ),
                if (order.status == 'Pending')
                  Align(
                    alignment: Alignment.centerRight,
                    child: ElevatedButton.icon(
                      icon: Icon(Icons.cancel, color: Colors.white),
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.redAccent,
                        padding: EdgeInsets.symmetric(
                            horizontal: 16, vertical: 8),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(12),
                        ),
                      ),
                      onPressed: () {
                        _cancelOrder(order.orderId);
                      },
                      label: Text(
                        'Cancel Order',
                        style: TextStyle(color: Colors.white),
                      ),
                    ),
                  ),
              ],
            ),
            trailing: Icon(Icons.arrow_forward_ios, color: Colors.black),
            onTap: () async {
              await _fetchOrderItems(order.orderId);
              _showOrderItemsDialog(context);
            },
          ),
        );
      },
    );
  }


  Widget _buildLoginButton() {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(
            Icons.person_outline,
            size: 100,
            color: Colors.grey[400],
          ),
          SizedBox(height: 20),
          Text(
            'No user found. Please login.',
            style: TextStyle(fontSize: 18, color: Colors.grey[600]),
          ),
          SizedBox(height: 20),
          ElevatedButton(
            onPressed: () => Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => LoginPage()),
            ),
            child: Text("Login",
                style: TextStyle(fontSize: 18, color: Colors.white)),
            style: ElevatedButton.styleFrom(
              backgroundColor: AppColor.login,
              padding: EdgeInsets.symmetric(horizontal: 40, vertical: 12),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(30),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Future<void> _cancelOrder(int orderId) async {
    String? userId = await UsersService().getUserId();
    Users user = await UsersService().findOne(userId!);
    OrdersService ordersService = OrdersService();
    Orders order = await ordersService.findOne(orderId);
    Orders updatedOrder = Orders(
      orderId: order.orderId,
      orderDate: DateTime.parse(order.orderDate),
      status: "Cancelled",
      totalAmount: order.totalAmount,
      createdDt: DateTime.parse(order.createdDt),
      users: user,
    );
    await ordersService.saveOrder(updatedOrder);
    List<OrderItems> orderItems = await OrderItemsService().findOrder(order);
    for (var item in orderItems) {
      Items i = await ItemsService().findOne(item.item.itemId);
      int stock = i.stock + item.quantity;

      Items updatedItem = Items(
          itemId: i.itemId,
          name: i.name,
          image: i.image,
          brand: i.brand,
          description: i.description,
          price: i.price,
          type: i.type,
          createdDt: DateTime.parse(i.createdDt),
          isVisible: stock > 0,
          stock: stock,
          rentalquantity: i.rentalquantity
      );
      await ItemsService().saveItems(updatedItem);
    }
    _fetchOrders(); // Refresh the list after updating the status
  }

  void _showOrderItemsDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) {
        return Dialog(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(16.0),
          ),
          elevation: 8,
          backgroundColor: Colors.white,
          child: Padding(
            padding: EdgeInsets.all(20.0),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Text(
                  'Order Items',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 22.0,
                    color: Colors.black,
                  ),
                ),
                SizedBox(height: 10.0),
                Divider(color: Colors.grey),
                SizedBox(height: 10.0),
                _orderItems.isEmpty
                    ? Text(
                  'No items found for this order.',
                  style: TextStyle(
                    color: Colors.grey,
                    fontSize: 16.0,
                  ),
                )
                    : ListView.builder(
                  shrinkWrap: true,
                  itemCount: _orderItems.length,
                  itemBuilder: (BuildContext context, int index) {
                    final orderItem = _orderItems[index];
                    return ListTile(
                      contentPadding: EdgeInsets.all(0),
                      title: Text(
                        orderItem.item.name,
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 18.0,
                        ),
                      ),
                      subtitle: Text(
                        'Quantity: ${orderItem.quantity}',
                        style: TextStyle(fontSize: 16.0),
                      ),
                      trailing: Text(
                        '\$${orderItem.item.price * orderItem.quantity}',
                        style: TextStyle(
                          fontSize: 16.0,
                          color: Colors.black,
                        ),
                      ),
                    );
                  },
                ),
                SizedBox(height: 10.0),
                Align(
                  alignment: Alignment.centerRight,
                  child: ElevatedButton(
                    onPressed: () => Navigator.pop(context),
                    child: Text('Close'),
                  ),
                ),
              ],
            ),
          ),
        );
      },
    );
  }
}
