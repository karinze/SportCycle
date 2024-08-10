import 'package:project4flutter/model/Coupons.dart';
import 'package:project4flutter/model/Users.dart';

/// coupon_user_id : 1
/// created_dt : "12"

class CouponUsers {
  CouponUsers({
      int? couponUserId,
      required Coupons coupons,
      required Users users,
      DateTime? createdDt,}){
    _couponUserId = couponUserId;
    _users = users;
    _coupons = coupons;
    _createdDt = createdDt;
}

  CouponUsers.fromJson(dynamic json) {
    _couponUserId = json['coupon_user_id'];
    _coupons = json['coupons'] != null ? Coupons.fromJson(json['coupons']) : null;
    _users = json['users'] != null ? Users.fromJson(json['users']) : null;
    _createdDt = _parseDate(json['created_dt']);
  }
  int? _couponUserId;
  Coupons? _coupons;
  Users? _users;
  DateTime? _createdDt;

  int get couponUserId => _couponUserId!;
  Coupons get coupons => _coupons!;
  Users get users => _users!;
  String get createdDt => _formatDate(_createdDt)!;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['coupon_user_id'] = _couponUserId;
    if(_coupons != null){
      map['coupons'] = _coupons?.toJson();
    }
    if(_users != null){
      map['users'] = _users?.toJson();
    }
    map['created_dt'] = _formatDate(_createdDt) ?? "";
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