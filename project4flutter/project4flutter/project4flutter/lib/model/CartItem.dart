import 'Items.dart';

class CartItem {
  final Items item;
  int quantity;

  CartItem({required this.item, this.quantity = 1});
}