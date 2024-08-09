/// item_id : 1
/// name : "hello"
/// brand : "hello"
/// description : "hello"
/// price : 2.1
/// stock : 1
/// type : "hello"
/// image : "hello"
/// is_visible : true
/// created_dt : "12/12/2004"

class Items {
  Items({
      int? itemId,
    required String? name,
    required String? brand,
    required String? description,
    required double? price,
    required int? stock,
    required String? type,
    required String? image,
    required bool? isVisible,
    DateTime? createdDt,}){

    _itemId = itemId;
    _name = name;
    _brand = brand;
    _description = description;
    _price = price;
    _stock = stock;
    _type = type;
    _image = image;
    _isVisible = isVisible;
    _createdDt = createdDt ?? DateTime.now();

}

  Items.fromJson(dynamic json) {
    _itemId = json['item_id'];
    _name = json['name'];
    _brand = json['brand'];
    _description = json['description'];
    _price = json['price'];
    _stock = json['stock'];
    _type = json['type'];
    _image = json['image'];
    _isVisible = json['is_visible'];
    _createdDt = _parseDate(json['created_dt']);
  }
  int? _itemId;
  String? _name;
  String? _brand;
  String? _description;
  double? _price;
  int? _stock;
  String? _type;
  String? _image;
  bool? _isVisible;
  DateTime? _createdDt;

  int get itemId => _itemId!;
  String get name => _name!;
  String get brand => _brand!;
  String get description => _description!;
  double get price => _price!;
  int get stock => _stock!;
  String get type => _type!;
  String get image => _image!;
  bool get isVisible => _isVisible!;
  String get createdDt => _formatDate(_createdDt)!;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['item_id'] = _itemId;
    map['name'] = _name;
    map['brand'] = _brand;
    map['description'] = _description;
    map['price'] = _price;
    map['stock'] = _stock;
    map['type'] = _type;
    map['image'] = _image;
    map['is_visible'] = _isVisible;
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