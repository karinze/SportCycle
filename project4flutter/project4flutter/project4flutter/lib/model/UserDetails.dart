import 'Users.dart';

class UserDetails {
  UserDetails({
    int? userdetailId,
    Users? users,
    String? firstName,
    String? lastName,
    String? email,
    String? phoneNumber,
    String? address,
    String? note,
    DateTime? createdDt,
  })  : _userdetailId = userdetailId,
        _users = users,
        _firstName = firstName,
        _lastName = lastName,
        _email = email,
        _phoneNumber = phoneNumber,
        _address = address,
        _note = note,
        _createdDt = createdDt ?? DateTime.now();

  UserDetails.fromJson(dynamic json)
      : _userdetailId = json['userdetail_id'],
        _users = json['users'] != null ? Users.fromJson(json['users']) : null,
        _firstName = json['first_name'],
        _lastName = json['last_name'],
        _email = json['email'],
        _phoneNumber = json['phone_number'],
        _address = json['address'],
        _note = json['note'],
        _createdDt = _parseDate(json['created_dt']);

  final int? _userdetailId;
  final Users? _users;
  final String? _firstName;
  final String? _lastName;
  final String? _email;
  final String? _phoneNumber;
  final String? _address;
  final String? _note;
  final DateTime? _createdDt;

  int get userdetailId => _userdetailId ?? 0;
  Users? get users => _users;
  String get firstName => _firstName ?? '';
  String get lastName => _lastName ?? '';
  String get email => _email ?? '';
  String get phoneNumber => _phoneNumber ?? '';
  String get address => _address ?? '';
  String get note => _note ?? '';
  String get createdDt => _formatDate(_createdDt) ?? '';

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['userdetail_id'] = _userdetailId;
    map['users'] = _users?.toJson();
    map['first_name'] = _firstName;
    map['last_name'] = _lastName;
    map['email'] = _email;
    map['phone_number'] = _phoneNumber;
    map['address'] = _address;
    map['note'] = _note;
    map['created_dt'] = _formatDate(_createdDt);
    return map;
  }

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
