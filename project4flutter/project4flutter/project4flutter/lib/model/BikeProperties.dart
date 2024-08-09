import 'Items.dart';

/// bike_property_id : 1
/// bike_size : "hello"
/// bike_wheel_size : "hello"
/// bike_color : "hello"
/// bike_material : "hello"
/// bike_brake_type : "hello"
/// created_dt : "12/12/2004"

class BikeProperties {
  BikeProperties({
      int? bikePropertyId,
    required Items? item,
    required String? bikeSize,
    required String? bikeWheelSize,
    required String? bikeColor,
    required String? bikeMaterial,
    required String? bikeBrakeType,
    DateTime? createdDt,}){
    _bikePropertyId = bikePropertyId;
    _item = item;
    _bikeSize = bikeSize;
    _bikeWheelSize = bikeWheelSize;
    _bikeColor = bikeColor;
    _bikeMaterial = bikeMaterial;
    _bikeBrakeType = bikeBrakeType;
    _createdDt = createdDt ?? DateTime.now();
}

  BikeProperties.fromJson(dynamic json) {
    _bikePropertyId = json['bike_property_id'];
    _item = json['item'];
    _bikeSize = json['bike_size'];
    _bikeWheelSize = json['bike_wheel_size'];
    _bikeColor = json['bike_color'];
    _bikeMaterial = json['bike_material'];
    _bikeBrakeType = json['bike_brake_type'];
    _createdDt = _parseDate(json['created_dt']);
  }
  int? _bikePropertyId;
  Items? _item;
  String? _bikeSize;
  String? _bikeWheelSize;
  String? _bikeColor;
  String? _bikeMaterial;
  String? _bikeBrakeType;
  DateTime? _createdDt;

  int get bikePropertyId => _bikePropertyId!;
  Items get item => _item!;
  String get bikeSize => _bikeSize!;
  String get bikeWheelSize => _bikeWheelSize!;
  String get bikeColor => _bikeColor!;
  String get bikeMaterial => _bikeMaterial!;
  String get bikeBrakeType => _bikeBrakeType!;
  String get createdDt => _formatDate(_createdDt)!;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['bike_property_id'] = _bikePropertyId;
    map['item'] = _item;
    map['bike_size'] = _bikeSize;
    map['bike_wheel_size'] = _bikeWheelSize;
    map['bike_color'] = _bikeColor;
    map['bike_material'] = _bikeMaterial;
    map['bike_brake_type'] = _bikeBrakeType;
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