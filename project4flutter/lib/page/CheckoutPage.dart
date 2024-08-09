import 'package:flutter/material.dart';
import 'package:flutter_paypal_checkout/flutter_paypal_checkout.dart';
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
import 'HomePage.dart';

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

  String _selectedPaymentMethod = 'Offline';

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

        if (details.isNotEmpty) {
          setState(() {
            _userDetails = details.first;
            _firstNameController.text = _userDetails?.firstName ?? '';
            _lastNameController.text = _userDetails?.lastName ?? '';
            _emailController.text = _userDetails?.email ?? '';
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
      // Show loading dialog

        if (_selectedPaymentMethod == 'Online') {
          await _processPayPalPayment();
        } else {
          await _saveOrderToDatabase('Pending');
        }
    }
  }

  Future<void> _processPayPalPayment() async {
    try {
      await Navigator.of(context).push(
        MaterialPageRoute(
          builder: (context) => PaypalCheckout(
            sandboxMode: true, // Set this to false in production
            clientId: 'AZDuKIu5bbjUgctSXXXrBGPKFZ_d2nFV4XFAjzDlQMcu_6GVs_RSeESb8gcPVtuJ_8L4H5SvuE0jcx36',
            secretKey: 'EEolPMf39IbyKHpW0gejHmF5-55nbkX7mdLrlAC0g-SuXp0P0QHqooPRJtI1gv8owCbMzLy4lXgV9Ai7',
            returnURL: 'success.snippetcoder.com',
            cancelURL: 'cancel.snippetcoder.com',
            transactions: [
              {
                "amount": {
                  "total": widget.totalPrice.toString(),
                  "currency": "USD",
                  "details": {
                    "subtotal": widget.totalPrice.toString(),
                  }
                },
                "description": "Order from your store",
                "payment_options": {
                  "allowed_payment_method": "INSTANT_FUNDING_SOURCE"
                },
              }
            ],
            note: "Thank you for your purchase!",
            onSuccess: (Map params) async {
              print("Payment successful: $params");
              _saveOrderToDatabase('Online Payment');
            },
            onCancel: (Map params) {
              print("Payment cancelled: $params");
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text('Payment cancelled')),
              );
            },
            onError: (error) {
              print("Payment error: $error");
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text('Payment error: $error')),
              );
            },
          ),
        ),
      );
    } catch (e) {
      print("Payment process failed: $e");
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Payment process failed: $e')),
      );
    }
  }

  Future<void> _saveOrderToDatabase(String status) async {
    try {
      final UsersService usersService = UsersService();
      final OrdersService ordersService = OrdersService();
      final OrderItemsService orderItemsService = OrderItemsService();
      final UserDetailsService userDetailsService = UserDetailsService();
      final CartService cartService = Provider.of<CartService>(context, listen: false);

      String? userId = await usersService.getUserId();
      Users? user = await usersService.findOne(userId!);

      if (user != null) {
        Orders order = Orders(
          users: user,
          totalAmount: widget.totalPrice,
          orderDate: DateTime.now(),
          status: status,
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

        // Show the loading dialog
        showDialog(
          context: context,
          barrierDismissible: false,
          builder: (context) {
            return Center(
              child: CircularProgressIndicator(),
            );
          },
        );

        // Send order confirmation email
        await ordersService.sendMail(user, savedOrder, o);

        // Dismiss the loading dialog
        Navigator.of(context, rootNavigator: true).pop();

        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Order placed successfully!')),
        );

        await Future.delayed(Duration(milliseconds: 100)); // Give a brief delay
        cartService.clearCart();
        Navigator.pushAndRemoveUntil(
          context,
          MaterialPageRoute(builder: (context) => HomePage()),
              (Route<dynamic> route) => false,
        );
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('User not found. Please log in again.')),
        );
      }
    } catch (e) {
      // Dismiss the loading dialog if an error occurs
      Navigator.of(context, rootNavigator: true).pop();

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Failed to place order: $e')),
      );
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
                      if (value == null || value.isEmpty) {
                        return 'Please enter your email';
                      }
                      return null;
                    },
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
                  ),
                ],
              ),
            ),
            SizedBox(height: 20),
            Text(
              'Payment Method',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: AppColor.mainColor,
              ),
            ),
            Divider(thickness: 2),
            RadioListTile(
              title: const Text('Offline Payment'),
              value: 'Offline',
              groupValue: _selectedPaymentMethod,
              onChanged: (value) {
                setState(() {
                  _selectedPaymentMethod = value.toString();
                });
              },
            ),
            RadioListTile(
              title: const Text('Online Payment'),
              value: 'Online',
              groupValue: _selectedPaymentMethod,
              onChanged: (value) {
                setState(() {
                  _selectedPaymentMethod = value.toString();
                });
              },
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _placeOrder,
              style: ElevatedButton.styleFrom(
                backgroundColor: AppColor.mainColor,
                padding: EdgeInsets.symmetric(vertical: 16.0),
              ),
              child: Text(
                'Place Order',
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
