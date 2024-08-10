/// coupon_id : 1
/// code : "DSFSDFSDF"
/// discount_amount : 1.1
/// expiration_date : "12"
/// usage_limit : 1
/// is_active : true
/// created_dt : "121"

class Coupons {
  Coupons({
      int? couponId,
      String? code,
      double? discountAmount,
      String? expirationDate,
      int? usageLimit,
      bool? isActive,
      DateTime? createdDt,}){
    _couponId = couponId;
    _code = code;
    _discountAmount = discountAmount;
    _expirationDate = expirationDate;
    _usageLimit = usageLimit;
    _isActive = isActive;
    _createdDt = createdDt;
}

  Coupons.fromJson(dynamic json) {
    _couponId = json['coupon_id'];
    _code = json['code'];
    _discountAmount = json['discount_amount'];
    _expirationDate = json['expiration_date'];
    _usageLimit = json['usage_limit'];
    _isActive = json['is_active'];
    _createdDt = _parseDate(json['created_dt']);
  }
  int? _couponId;
  String? _code;
  double? _discountAmount;
  String? _expirationDate;
  int? _usageLimit;
  bool? _isActive;
  DateTime? _createdDt;

  int get couponId => _couponId!;
  String get code => _code!;
  double get discountAmount => _discountAmount!;
  String get expirationDate => _expirationDate!;
  int get usageLimit => _usageLimit!;
  bool get isActive => _isActive!;
  String get createdDt => _formatDate(_createdDt)!;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['coupon_id'] = _couponId;
    map['code'] = _code;
    map['discount_amount'] = _discountAmount;
    map['expiration_date'] = _expirationDate;
    map['usage_limit'] = _usageLimit;
    map['is_active'] = _isActive;
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