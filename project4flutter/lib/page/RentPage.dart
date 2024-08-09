import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../service/CartService.dart';
import '../utils/color.dart';

class RentPage extends StatelessWidget {
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
            Row(
              children: [
                ElevatedButton(
                  onPressed: () {
                    // Handle checkout action
                    _handleCheckout(context);
                  },
                  style: ElevatedButton.styleFrom(
                    foregroundColor: Colors.black, backgroundColor: AppColor.mainColor, // White text color
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


  void _handleCheckout(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        backgroundColor: AppColor.paraColor, // Custom background color
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.all(Radius.circular(20.0)), // Rounded corners
        ),
        title: Text(
          'Checkout',
          style: TextStyle(

            color: Colors.black, // Custom text color
            fontWeight: FontWeight.bold,
          ),
        ),
        content: Text(
          'Proceed to checkout?',
          style: TextStyle(
            color: Colors.black, // Custom text color
          ),
        ),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.of(context).pop();
              // Implement actual checkout logic here
            },
            style: TextButton.styleFrom(
              backgroundColor: AppColor.mainColor, // Custom button background color
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10.0), // Rounded corners for button
              ),
            ),
            child: Text(
              'Yes',
              style: TextStyle(
                color: Colors.black, // Custom text color for button
              ),
            ),
          ),
          TextButton(
            onPressed: () {
              Navigator.of(context).pop();
            },
            style: TextButton.styleFrom(
              backgroundColor: Colors.white, // Custom button background color
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10.0), // Rounded corners for button
              ),
            ),
            child: Text(
              'No',
              style: TextStyle(
                color: Colors.black, // Custom text color for button
              ),
            ),
          ),
        ],
      ),
    );
  }
}
