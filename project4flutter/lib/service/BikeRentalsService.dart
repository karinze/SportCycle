import '../model/BikeRentals.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class BikeRentalsService{
  static const String url = "http://192.168.1.7:9999/api";
  static const String urlBikerentals = "$url/bikerentals/";

  Future<List<BikeRentals>> findAll() async {
    final response = await http.get(Uri.parse(urlBikerentals));
    var data = json.decode(response.body);
    List<BikeRentals> list = [];
    for (var item in data) {
      BikeRentals bikeRentals = BikeRentals.fromJson(item);
      list.add(bikeRentals);
    }
    return list;
  }

  Future<BikeRentals> findOne(int bikerentals_id) async {
    final response = await http.get(Uri.parse("$urlBikerentals $bikerentals_id"));
    var data = json.decode(response.body);
    BikeRentals employee = BikeRentals.fromJson(data);
    return employee;
  }

  Future<BikeRentals> saveBikeRentals(BikeRentals bikeRentals) async {
    final response = await http.post(
      Uri.parse(urlBikerentals),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(bikeRentals.toJson()),
    );

    if (response.statusCode == 201) {
      return BikeRentals.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed...');
    }
  }

  Future<List<BikeRentals>> findUser(String userId) async {
    final response = await http.get(Uri.parse(urlBikerentals+"user/"+userId));
    var data = json.decode(response.body);
    List<BikeRentals> list = [];
    for (var item in data) {
      BikeRentals bikeRentals = BikeRentals.fromJson(item);
      list.add(bikeRentals);
    }
    return list;
  }

}