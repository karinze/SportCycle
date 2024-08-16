import 'package:http/http.dart' as http;
import 'dart:convert';

import '../model/Items.dart';
import 'APIService.dart';

class ItemsService {
  // static const String url = "http://192.168.1.8:9999/api";
  static const String urlItems = "${APIService.url}/items/";

  Future<List<Items>> findAll() async {
    final response = await http.get(Uri.parse(urlItems));
    var data = json.decode(response.body);
    List<Items> list = [];
    for (var item in data) {
      Items items = Items.fromJson(item);
      list.add(items);
    }
    return list;
  }

  Future<List<Items>> top10() async {
    final response = await http.get(Uri.parse("${urlItems}top10"));
    var data = json.decode(response.body);
    List<Items> list = [];
    for (var item in data) {
      Items items = Items.fromJson(item);
      list.add(items);
    }
    return list;
  }

  Future<Items> findOne(int items_id) async {
    final response = await http.get(Uri.parse("$urlItems $items_id"));
    var data = json.decode(response.body);
    Items employee = Items.fromJson(data);
    return employee;
  }

  Future<Items> saveItems(Items itemss) async {
    final response = await http.post(
      Uri.parse(urlItems),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(itemss.toJson()),
    );

    if (response.statusCode == 201) {
      return Items.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed...');
    }
  }

  Future<List<Items>> search(String name) async {
    final response = await http.get(Uri.parse("$urlItems search/ $name"));
    var data = json.decode(response.body);
    List<Items> list = [];
    for (var item in data) {
      Items items = Items.fromJson(item);
      list.add(items);
    }
    return list;
  }

  Future<List<Items>> filter({
    required String name,
    required double minPrice,
    required double maxPrice,
    required String brand,
    required String bikeSize,
    required String bikeWheelSize,
    required String bikeBrakeType,
    required String bikeMaterial,
    required String bikeColor,
  }) async {
    final uri = Uri.parse('$urlItems/showfilter').replace(
      queryParameters: {
        if (name.isNotEmpty) '?name=': name,
        if (brand.isNotEmpty) '&brand': brand,
        if (minPrice > 0) 'minPrice': minPrice.toString(),
        if (maxPrice > 0) 'maxPrice': maxPrice.toString(),
        if (bikeSize.isNotEmpty) 'bikeSize': bikeSize,
        if (bikeWheelSize.isNotEmpty) 'bikeWheelSize': bikeWheelSize,
        if (bikeBrakeType.isNotEmpty) 'bikeBrakeType': bikeBrakeType,
        if (bikeMaterial.isNotEmpty) 'bikeMaterial': bikeMaterial,
        if (bikeColor.isNotEmpty) 'bikeColor': bikeColor,
      },
    );

    final response = await http.get(uri);
    final Map<String, dynamic> data = json.decode(response.body);

    // Check if the 'items' key exists and is a list
    if (data.containsKey('items') && data['items'] is List) {
      final List<dynamic> itemsData = data['items'];
      return itemsData.map((item) => Items.fromJson(item)).toList();
    } else {
      throw Exception('Invalid JSON structure');
    }
  }
}
