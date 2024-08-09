import 'Users.dart';

class Orders {
  Orders({
    int? orderId,
    required Users users, // Không cần phải nullable
    required double totalAmount,
    required DateTime orderDate,
    required String status,
    DateTime? createdDt,
  })  : _orderId = orderId,
        _users = users,
        _totalAmount = totalAmount,
        _orderDate = orderDate,
        _status = status,
        _createdDt = createdDt ?? DateTime.now();

  Orders.fromJson(dynamic json)
      : _orderId = json['order_id'],
        _users = json['users'] != null ? Users.fromJson(json['users']) : null, // Chuyển đổi từ JSON
        _totalAmount = json['total_amount'],
        _orderDate = _parseDate(json['order_date']),
        _status = json['status'],
        _createdDt = _parseDate(json['created_dt']);

  // Các biến private
  final int? _orderId;
  final Users? _users;
  final double _totalAmount;
  final DateTime? _orderDate;
  final String _status;
  final DateTime? _createdDt;

  // Các getter
  int get orderId => _orderId!;
  Users get users => _users!;
  double get totalAmount => _totalAmount;
  String get orderDate => _formatDate(_orderDate)!;
  String get status => _status;
  String get createdDt => _formatDate(_createdDt)!;

  // Phương thức toJson
  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['order_id'] = _orderId;
    if(_users != null){
      map['users'] = _users.toJson();
    }
    map['total_amount'] = _totalAmount;
    map['order_date'] = _formatDate(_orderDate) ?? "";
    map['status'] = _status;
    map['created_dt'] = _formatDate(_createdDt) ?? "";
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
