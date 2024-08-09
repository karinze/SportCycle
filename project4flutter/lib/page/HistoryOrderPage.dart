import 'package:flutter/material.dart';
import 'package:project4flutter/model/Items.dart';
import 'package:project4flutter/model/Orders.dart';
import 'package:project4flutter/model/OrderItems.dart';
import 'package:project4flutter/service/ItemsService.dart';
import 'package:project4flutter/service/OrdersService.dart';
import 'package:project4flutter/service/OrderItemsService.dart';
import 'package:project4flutter/service/UsersService.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../model/Users.dart';
import 'LoginPage.dart';

class OrderHistoryPage extends StatefulWidget {
  @override
  _OrderHistoryPageState createState() => _OrderHistoryPageState();
}

class _OrderHistoryPageState extends State<OrderHistoryPage> {
  List<Orders> _orders = [];
  List<OrderItems> _orderItems = [];
  bool _isLoggedIn = false;
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
      _fetchOrders();
    }
    setState(() {
      _isLoggedIn = isLoggedIn;
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
        title: Text('Order History', style: TextStyle(fontWeight: FontWeight.normal)),

      ),
      body: _isLoggedIn ? _buildOrderList() : _buildLoginButton(),
    );
  }

  Widget _buildOrderList() {
    if (_orders.isEmpty) {
      return Center(child: Text('No orders found.', style: TextStyle(fontSize: 18, color: Colors.grey)));
    }

    return ListView.builder(
      itemCount: _orders.length,
      itemBuilder: (context, index) {
        return Card(
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
          elevation: 5,
          margin: EdgeInsets.symmetric(horizontal: 15, vertical: 10),
          child: ListTile(
            title: Text(
              'Order',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
            ),
            subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                SizedBox(height: 5),
                Text('Total Amount: \$${_orders[index].totalAmount}', style: TextStyle(fontSize: 14)),
                Text('Order Date: ${_orders[index].orderDate}', style: TextStyle(fontSize: 14)),
                Text('Status: ${_orders[index].status}', style: TextStyle(fontSize: 14, color: Colors.blueAccent)),
                if (_orders[index].status == 'Pending') // Add Cancel Button if Status is Pending
                  Align(
                    alignment: Alignment.centerRight,
                    child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.red,
                        padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
                      ),
                      onPressed: () {
                        _cancelOrder(_orders[index].orderId);
                      },
                      child: Text('Cancel Order', style: TextStyle(color: Colors.white)),
                    ),
                  ),
              ],
            ),
            trailing: Icon(Icons.arrow_forward_ios, color: Colors.deepPurple),
            onTap: () {
              _fetchOrderItems(_orders[index].orderId);
              _showOrderItemsDialog(context);
            },
          ),
        );
      },
    );
  }

  Widget _buildLoginButton() {
    return Center(
      child: ElevatedButton(
        style: ElevatedButton.styleFrom(
          backgroundColor: Colors.deepPurple,
          padding: EdgeInsets.symmetric(horizontal: 30, vertical: 15),
          textStyle: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
        ),
        onPressed: () {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => LoginPage()),
          );
        },
        child: Text('Login'),
      ),
    );
  }

  Future<void> _cancelOrder(int orderId) async {
    String? userId = await UsersService().getUserId();
    Users user = await UsersService().findOne(userId!);
    OrdersService ordersService = OrdersService();
    Orders o = await ordersService.findOne(orderId);
    Orders orders = Orders(
      orderId: o.orderId, 
      orderDate: DateTime.parse(o.orderDate),
      status: "Cancel",
      totalAmount: o.totalAmount,
      createdDt: DateTime.parse(o.createdDt),
      users: user
    );
    await ordersService.saveOrder(orders);
    List<OrderItems> orderitem = await OrderItemsService().findOrder(o);
    for(var list in orderitem){
      Items i = await ItemsService().findOne(list.item.itemId);
      int stock = i.stock + list.quantity;

      Items item = Items(
        itemId: i.itemId,
        name: i.name,
        image: i.image,
        brand: i.brand,
        description: i.description,
        price: i.price,
        type: i.type,
        createdDt: DateTime.parse(i.createdDt),
        isVisible: stock <= 0 ? false : true,
        stock: stock,
      );
      await ItemsService().saveItems(item);
    }
    _fetchOrders(); // Refresh the list after updating the status
  }

  void _showOrderItemsDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) {
        return Dialog(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(20.0),
          ),
          elevation: 5,
          backgroundColor: Colors.white,
          child: Container(
            padding: EdgeInsets.all(20.0),
            width: MediaQuery.of(context).size.width * 0.8,
            child: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Text(
                  'Order Items',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 20.0,
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
                  itemBuilder: (context, index) {
                    return ListTile(
                      title: Text(
                        'Item: ${_orderItems[index].item.name}',
                        style: TextStyle(
                          fontSize: 16.0,
                        ),
                      ),
                      subtitle: Text(
                        'Quantity: ${_orderItems[index].quantity}, Price: \$${_orderItems[index].price}',
                        style: TextStyle(
                          fontSize: 14.0,
                        ),
                      ),
                    );
                  },
                ),
                SizedBox(height: 10.0),
                Align(
                  alignment: Alignment.centerRight,
                  child: TextButton(
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: Text(
                      'Close',
                      style: TextStyle(
                        color: Colors.deepPurple,
                        fontSize: 18.0,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
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
