import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../service/CartService.dart';
import '../utils/color.dart';
import 'CheckoutPage.dart';
import 'LoginPage.dart';
import '../service/UsersService.dart';

class CartProductPage extends StatelessWidget {
  final UsersService _usersService = UsersService();

  void _handleCheckout(BuildContext context) async {
    final isLoggedIn = await _usersService.isLoggedIn();

    if (!isLoggedIn) {
      // User is not logged in, redirect to login page
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => const LoginPage(),
        ),
      );
    } else {
      // Proceed to checkout
      // Navigator.of(context).push(
      //   // MaterialPageRoute(
      //   //   builder: (context) => CheckoutPage(
      //   //     totalPrice: Provider.of<CartService>(context, listen: false).totalPrice,
      //   //   ),
      //   // ),
      // );
    }
  }

  @override
  Widget build(BuildContext context) {
    final cart = Provider.of<CartService>(context);
    return Scaffold(
      appBar: AppBar(
        title: Text('Cart'),
      ),
      body: cart.cartItems.isEmpty
          ? Center(child: Text('No items in the cart'))
          : ListView.builder(
        itemCount: cart.cartItems.length,
        itemBuilder: (context, index) {
          final cartItem = cart.cartItems[index];
          return ListTile(
            leading: Image(image: AssetImage('images/' + cartItem.item.image!)),
            title: Text(cartItem.item.name!),
            subtitle: Text('Quantity: ${cartItem.quantity}'),
            trailing: IconButton(
              icon: Icon(Icons.remove_circle_outline),
              onPressed: () {
                cart.removeFromCart(cartItem.item);
              },
            ),
          );
        },
      ),
      bottomNavigationBar: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text(
              'Total: \$${cart.totalPrice}',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            ElevatedButton(
              onPressed: () {
                _handleCheckout(context);
              },
              style: ElevatedButton.styleFrom(
                foregroundColor: Colors.black,
                backgroundColor: AppColor.mainColor, // White text color
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10), // Rounded corners
                ),
              ),
              child: Text(
                'Check Out',
                style: TextStyle(
                  fontSize: 16,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
