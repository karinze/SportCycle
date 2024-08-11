import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../service/CartService.dart';
import '../utils/color.dart';
import 'CheckoutPage.dart';
import 'LoginPage.dart';
import '../service/UsersService.dart';

class CartPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final cart = Provider.of<CartService>(context);
    return Scaffold(
      appBar: AppBar(
        title: Text('Cart'),
      ),
      body: cart.cartItems.isEmpty
          ? Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.card_travel_outlined,
              size: 80,
              color: Colors.grey,
            ),
            SizedBox(height: 16),
            Text(
              'No items in the cart',
              style: TextStyle(fontSize: 18, color: Colors.grey),
            ),
          ],
        ),
      )
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
      bottomNavigationBar: cart.cartItems.isEmpty
          ? SizedBox.shrink() // Hide when cart is empty
          : Padding(
        padding: const EdgeInsets.all(16.0),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text(
              'Total: \$${cart.totalPrice}',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            Row(
              children: [
                ElevatedButton(
                  onPressed: () {
                    // Handle checkout action
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
          ],
        ),
      ),
    );
  }

  void _handleCheckout(BuildContext context) async {
    final usersService = UsersService();
    final isLoggedIn = await usersService.isLoggedIn();

    if (isLoggedIn) {
      // Proceed to checkout
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => CheckOutPage(totalPrice: Provider.of<CartService>(context, listen: false).totalPrice),
        ),
      );
    } else {
      // Redirect to login page
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => const LoginPage(),
        ),
      );
    }
  }
}
