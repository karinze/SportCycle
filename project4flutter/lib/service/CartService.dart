import 'package:flutter/material.dart';
import '../model/CartItem.dart';
import '../model/Items.dart';

class CartService extends ChangeNotifier {
  final List<CartItem> _cartItems = [];

  List<CartItem> get cartItems => _cartItems;

  void addToCart(Items item, {int quantity = 1}) {
    final existingCartItem = _cartItems.firstWhere(
          (cartItem) => cartItem.item.itemId == item.itemId,
      orElse: () => CartItem(item: item, quantity: 0),
    );

    final int totalQuantityInCart = existingCartItem.quantity + quantity;

    if (totalQuantityInCart <= item.stock) {
      if (_cartItems.contains(existingCartItem)) {
        existingCartItem.quantity += quantity;
      } else {
        _cartItems.add(CartItem(item: item, quantity: quantity));
      }
      notifyListeners();
    } else {
      // Notify the user that they cannot add more items than are in stock
      print('Cannot add more items than are in stock');
      // You can show a SnackBar or a dialog here if you want to notify the user in the UI.
    }
  }

  void removeFromCart(Items item) {
    final existingCartItem = _cartItems.firstWhere(
          (cartItem) => cartItem.item.itemId == item.itemId,
      orElse: () => CartItem(item: item),
    );

    if (existingCartItem.quantity > 1) {
      existingCartItem.quantity -= 1;
    } else {
      _cartItems.remove(existingCartItem);
    }
    notifyListeners();
  }

  double get totalPrice => _cartItems.fold(
      0, (total, cartItem) => total + (cartItem.item.price! * cartItem.quantity));

  void clearCart() {
    _cartItems.clear();
    notifyListeners();
  }
}

