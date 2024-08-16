import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:project4flutter/service/APIService.dart';

import '../model/BikeProperties.dart';

class BikePropertiesService {
  // static const String url = "http://192.168.1.8:9999/api";
  static const String urlBikeproperties = "${APIService.url}/bikeproperties/";

  Future<List<BikeProperties>> findAll() async {
    final response = await http.get(Uri.parse(urlBikeproperties));
    if (response.statusCode == 200) {
      var data = json.decode(response.body) as List;
      List<BikeProperties> list = data.map((item) => BikeProperties.fromJson(item)).toList();
      return list;
    } else {
      throw Exception('Failed to load bike properties');
    }
  }

  Future<BikeProperties> findOne(int bikepropertiesId) async {
    final response = await http.get(Uri.parse("$urlBikeproperties$bikepropertiesId"));
    if (response.statusCode == 200) {
      var data = json.decode(response.body);
      BikeProperties bikeProperties = BikeProperties.fromJson(data);
      return bikeProperties;
    } else {
      throw Exception('Failed to load bike properties');
    }
  }

  Future<BikeProperties> findByItemId(int itemId) async {
    final response = await http.get(Uri.parse("$urlBikeproperties/item/$itemId"));
    if (response.statusCode == 200) {
      var data = json.decode(response.body);
      BikeProperties bikeProperties = BikeProperties.fromJson(data);
      return bikeProperties;
    } else {
      throw Exception('Failed to load bike properties');
    }
  }
}
