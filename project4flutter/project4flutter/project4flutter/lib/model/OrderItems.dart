import 'Items.dart';
import 'Orders.dart';

class OrderItems {
  OrderItems({
    int? orderItemId,
    required Orders orders, // Không cần phải nullable
    required Items item, // Không cần phải nullable
    required int quantity,
    required double price,
    DateTime? createdDt,
  })  : _orderItemId = orderItemId,
        _orders = orders,
        _item = item,
        _quantity = quantity,
        _price = price,
        _createdDt = createdDt ?? DateTime.now();

  OrderItems.fromJson(dynamic json)
      : _orderItemId = json['order_item_id'],
        _orders = json['orders'] != null ? Orders.fromJson(json['orders']) : null, // Chuyển đổi từ JSON
        _item = json['item'] != null ? Items.fromJson(json['item']) : null, // Chuyển đổi từ JSON
        _quantity = json['quantity'],
        _price = json['price'],
        _createdDt = _parseDate(json['created_dt']);

  // Các biến private
  final int? _orderItemId;
  final Orders? _orders;
  final Items? _item;
  final int _quantity;
  final double _price;
  final DateTime? _createdDt;

  // Các getter
  int get orderItemId => _orderItemId!;
  Orders get orders => _orders!;
  Items get item => _item!;
  int get quantity => _quantity;
  double get price => _price;
  String get createdDt => _formatDate(_createdDt)!;

  // Phương thức toJson
  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['order_item_id'] = _orderItemId;
    if(_orders != null){
      map['orders'] = _orders.toJson();
    }// Chuyển đổi đối tượng Orders sang JSON
    if(_item != null){
      map['item'] = _item.toJson();
    }
     // Chuyển đổi đối tượng Items sang JSON
    map['quantity'] = _quantity;
    map['price'] = _price;
    map['created_dt'] = _formatDate(_createdDt);
    return map;
  }

  // Các phương thức hỗ trợ khác
  static DateTime? _parseDate(String? dateStr) {
    if (dateStr == null || dateStr.isEmpty) return null;
    try {
      return DateTime.parse(dateStr);
    } catch (e) {
      print('Error parsing date: $e');
      return null;
    }
  }

  static String? _formatDate(DateTime? date) {
    if (date == null) return null;
    return date.toIso8601String();
  }
}
