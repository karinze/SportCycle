import 'package:intl/intl.dart';

class Users {
  Users({
    String? user_id,
    required String username,
    required String password,
    required String email,
    required bool role,
    required bool block,
    DateTime? created_dt,
  }) {
    _user_id = user_id ?? _generateUniqueId();
    _username = username;
    _password = password;
    _email = email;
    _role = role;
    _block = block;
    _created_dt = created_dt ?? DateTime.now();
  }

  Users.fromJson(dynamic json) {
    _user_id = json['user_id'];
    _username = json['username'];
    _password = json['password'];
    _email = json['email'];
    _role = json['role'];
    _block = json['block'];
    _created_dt = _parseDate(json['created_dt']);
  }

  String? _user_id;
  String? _username;
  String? _password;
  String? _email;
  bool? _role;
  bool? _block;
  DateTime? _created_dt;

  String get user_id => _user_id!;
  String get username => _username!;
  String get password => _password!;
  String get email => _email!;
  bool get role => _role!;
  bool get block => _block!;
  String get created_dt => _formatDate(_created_dt)!;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['user_id'] = _user_id;
    map['username'] = _username;
    map['password'] = _password;
    map['email'] = _email;
    map['role'] = _role;
    map['block'] = _block;
    map['created_dt'] = _formatDate(_created_dt) ?? "";
    return map;
  }

  static DateTime? _parseDate(String? dateStr) {
    if (dateStr == null || dateStr.isEmpty) return null;
    try {
      return DateTime.parse(dateStr); // ISO 8601 format
    } catch (e) {
      print('Error parsing date: $e');
      return null;
    }
  }

  static String? _formatDate(DateTime? date) {
    if (date == null) return null;
    return date.toIso8601String(); // ISO 8601 format
  }

  static String _generateUniqueId() {
    return DateTime.now().millisecondsSinceEpoch.toString();
  }
}
