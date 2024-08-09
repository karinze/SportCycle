import 'package:flutter/material.dart';
import 'package:project4flutter/page/HomePage.dart';
import 'package:provider/provider.dart';
import '../model/OrderItems.dart';
import '../model/Orders.dart';
import '../model/Users.dart';
import '../model/UserDetails.dart';
import '../service/CartService.dart';
import '../service/OrderItemsService.dart';
import '../service/OrdersService.dart';
import '../service/UserDetailsService.dart';
import '../service/UsersService.dart';
import '../utils/color.dart';

class CheckOutPage extends StatefulWidget {
  final double totalPrice;

  CheckOutPage({required this.totalPrice});

  @override
  _CheckOutPageState createState() => _CheckOutPageState();
}

class _CheckOutPageState extends State<CheckOutPage> {
  final _formKey = GlobalKey<FormState>();
  UserDetails? _userDetails;

  // Controllers for form fields
  TextEditingController _firstNameController = TextEditingController();
  TextEditingController _lastNameController = TextEditingController();
  TextEditingController _emailController = TextEditingController();
  TextEditingController _phoneController = TextEditingController();
  TextEditingController _addressController = TextEditingController();
  TextEditingController _noteController = TextEditingController();

  @override
  void initState() {
    super.initState();
    _loadUserDetails();
  }

  Future<void> _loadUserDetails() async {
    try {
      final UserDetailsService userDetailsService = UserDetailsService();
      String? userId = await userDetailsService.getUserId();

      if (userId != null) {
        List<UserDetails> details =
        await userDetailsService.findUserDetails(userId);

        Users us= await UsersService().findOne(userId);
        if (details.isNotEmpty) {
          setState(() {
            _userDetails = details.first;
            _firstNameController.text = _userDetails?.firstName ?? '';
            _lastNameController.text = _userDetails?.lastName ?? '';
            _emailController.text = us.email ?? '';
            _phoneController.text = _userDetails?.phoneNumber ?? '';
            _addressController.text = _userDetails?.address ?? '';
            _noteController.text = _userDetails?.note ?? '';
          });
        }
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Failed to load user details: $e')),
      );
    }
  }

  Future<void> _placeOrder() async {
    if (_formKey.currentState!.validate()) {
      try {
        final UsersService usersService = UsersService();
        final OrdersService ordersService = OrdersService();
        final OrderItemsService orderItemsService = OrderItemsService();
        final UserDetailsService userDetailsService = UserDetailsService();
        final CartService cartService =
        Provider.of<CartService>(context, listen: false);

        String? userId = await usersService.getUserId();
        Users user = await usersService.findOne(userId!);

        if (user != null) {
          List<UserDetails> userDetailsList =
          await userDetailsService.findUserDetails(userId);
          UserDetails userDetails;

          if (userDetailsList.isNotEmpty) {
            userDetails = userDetailsList.first;
            userDetails = UserDetails(
              userdetailId: userDetails.userdetailId,
              users: user,
              firstName: _firstNameController.text,
              lastName: _lastNameController.text,
              email: _emailController.text,
              phoneNumber: _phoneController.text,
              address: _addressController.text,
              note: _noteController.text,
              createdDt: DateTime.parse(userDetails.createdDt),
            );
          } else {
            userDetails = UserDetails(
              users: user,
              firstName: _firstNameController.text,
              lastName: _lastNameController.text,
              email: _emailController.text,
              phoneNumber: _phoneController.text,
              address: _addressController.text,
              note: _noteController.text,
            );
          }

          await userDetailsService.saveUserDetails(userDetails);

          Orders order = Orders(
            users: user,
            totalAmount: widget.totalPrice,
            orderDate: DateTime.now(),
            status: "Pending",
          );

          Orders savedOrder = await OrdersService().saveOrder(order);

          List<OrderItems> o = [];
          for (var cartItem in cartService.cartItems) {
            OrderItems orderItem = OrderItems(
              orders: savedOrder,
              item: cartItem.item,
              quantity: cartItem.quantity,
              price: cartItem.item.price * cartItem.quantity,
            );

            o.add(orderItem);
            await orderItemsService.saveOrderItems(orderItem);
          }

          await ordersService.sendMail(user, savedOrder, o);

          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Order placed successfully!')),
          );
          Navigator.pushAndRemoveUntil(
            context,
            MaterialPageRoute(builder: (context) => HomePage()),
                (Route<dynamic> route) => false,
          );

          cartService.clearCart();


        } else {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('User not found. Please log in again.')),
          );
        }
      } catch (e) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Failed to place order: $e')),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final cartService = Provider.of<CartService>(context);

    return Scaffold(
      appBar: AppBar(
        title: Text('Checkout'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: ListView(
          children: [
            Text(
              'Order Summary',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: AppColor.mainColor,
              ),
            ),
            Divider(thickness: 2),
            ListView.builder(
              shrinkWrap: true,
              physics: NeverScrollableScrollPhysics(),
              itemCount: cartService.cartItems.length,
              itemBuilder: (context, index) {
                final cartItem = cartService.cartItems[index];
                return ListTile(
                  contentPadding: EdgeInsets.symmetric(vertical: 8.0),
                  leading: Image.asset('images/${cartItem.item.image!}', width: 50, height: 50),
                  title: Text(cartItem.item.name!),
                  subtitle: Text('Quantity: ${cartItem.quantity}'),
                  trailing: Text(
                    '\$${cartItem.item.price * cartItem.quantity}',
                    style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                  ),
                );
              },
            ),
            Divider(thickness: 2),
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 16.0),
              child: Text(
                'Total: \$${widget.totalPrice}',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
                textAlign: TextAlign.end,
              ),
            ),
            SizedBox(height: 20),
            Text(
              'Shipping Information',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: AppColor.mainColor,
              ),
            ),
            Divider(thickness: 2),
            Form(
              key: _formKey,
              child: Column(
                children: [
                  TextFormField(
                    controller: _firstNameController,
                    decoration: InputDecoration(
                      labelText: 'First Name',
                      border: OutlineInputBorder(),
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter your first name';
                      }
                      return null;
                    },
                  ),
                  SizedBox(height: 16),
                  TextFormField(
                    controller: _lastNameController,
                    decoration: InputDecoration(
                      labelText: 'Last Name',
                      border: OutlineInputBorder(),
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter your last name';
                      }
                      return null;
                    },
                  ),
                  SizedBox(height: 16),
                  TextFormField(
                    controller: _emailController,
                    decoration: InputDecoration(
                      labelText: 'Email',
                      border: OutlineInputBorder(),
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty || !value.contains('@')) {
                        return 'Please enter a valid email address';
                      }
                      return null;
                    },
                    enabled: false,
                  ),
                  SizedBox(height: 16),
                  TextFormField(
                    controller: _phoneController,
                    decoration: InputDecoration(
                      labelText: 'Phone Number',
                      border: OutlineInputBorder(),
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter your phone number';
                      }
                      return null;
                    },
                  ),
                  SizedBox(height: 16),
                  TextFormField(
                    controller: _addressController,
                    decoration: InputDecoration(
                      labelText: 'Address',
                      border: OutlineInputBorder(),
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter your address';
                      }
                      return null;
                    },
                  ),
                  SizedBox(height: 16),
                  TextFormField(
                    controller: _noteController,
                    decoration: InputDecoration(
                      labelText: 'Note',
                      border: OutlineInputBorder(),
                    ),
                    maxLines: 3,
                  ),
                  SizedBox(height: 32),
                  ElevatedButton(
                    onPressed: _placeOrder,
                    style: ElevatedButton.styleFrom(
                      foregroundColor: Colors.white,
                      backgroundColor: AppColor.mainColor,
                      padding: EdgeInsets.symmetric(vertical: 16.0),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(8.0),
                      ),
                    ),
                    child: Center(
                      child: Text(
                        'Place Order',
                        style: TextStyle(fontSize: 18),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
