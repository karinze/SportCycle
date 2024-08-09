import 'Items.dart';
import 'Users.dart';

class BikeRentals {
  BikeRentals({
    int? bikeRentalId,
    required Items? item,
    required Users? users,
    required DateTime? rentalStartDate,
    required DateTime? rentalEndDate,
    required bool? isActive,
    DateTime? createdDt,
  }) {
    _bikeRentalId = bikeRentalId;
    _item = item;
    _users = users;
    _rentalStartDate = rentalStartDate;
    _rentalEndDate = rentalEndDate;
    _isActive = isActive;
    _createdDt = createdDt ?? DateTime.now();
  }

  BikeRentals.fromJson(dynamic json) {
    _bikeRentalId = json['bike_rental_id'];
    _item = json['item'] != null ? Items.fromJson(json['item']) : null;
    _users = json['users'] != null ? Users.fromJson(json['users']) : null;
    _rentalStartDate = DateTime.parse(json['rental_start_date']);
    _rentalEndDate = DateTime.parse(json['rental_end_date']);
    _isActive = json['is_active'];
    _createdDt = DateTime.parse(json['created_dt']);
  }

  int? _bikeRentalId;
  Items? _item;
  Users? _users;
  DateTime? _rentalStartDate;
  DateTime? _rentalEndDate;
  bool? _isActive;
  DateTime? _createdDt;

  int get bikeRentalId => _bikeRentalId!;

  Items get item => _item!;

  Users get users => _users!;

  DateTime get rentalStartDate => _rentalStartDate!;

  DateTime get rentalEndDate => _rentalEndDate!;

  bool get isActive => _isActive!;

  set isActive(bool value) {
    _isActive = value;
  }

  String get createdDt => _formatDate(_createdDt)!;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['bike_rental_id'] = _bikeRentalId;
    if(_item != null){
      map['item'] = _item?.toJson();
    }
    if(_users != null){
      map['users'] = _users?.toJson();
    }
    map['rental_start_date'] = _rentalStartDate?.toIso8601String();
    map['rental_end_date'] = _rentalEndDate?.toIso8601String();
    map['is_active'] = _isActive;
    map['created_dt'] = _createdDt?.toIso8601String();
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
