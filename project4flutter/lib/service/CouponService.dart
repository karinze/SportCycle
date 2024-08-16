import 'dart:convert';

import 'package:project4flutter/model/Coupons.dart';
import 'package:http/http.dart' as http;

import 'APIService.dart';

class CouponService{
  // static const String url = "http://192.168.1.8:9999/api";
  static const String urlCoupons = "${APIService.url}/coupons/";

  Future<List<Coupons>> findAll() async {
    final response = await http.get(Uri.parse(urlCoupons));
    var data = json.decode(response.body);
    List<Coupons> list = [];
    for (var item in data) {
      Coupons coupons = Coupons.fromJson(item);
      list.add(coupons);
    }
    return list;
  }

  Future<Coupons> saveCoupon(Coupons coupons) async {
    final response = await http.post(
      Uri.parse(urlCoupons),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(coupons.toJson()),
    );

    if (response.statusCode == 201) {
      return Coupons.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed...');
    }
  }

  Future<Coupons> findCode(String code ) async {
    final response = await http.get(Uri.parse("${urlCoupons}findcode/$code"));
    var data = json.decode(response.body);
    Coupons employee = Coupons.fromJson(data);
    return employee;
  }

  Future<Coupons> findOne(int coupons_id) async {
    final response = await http.get(Uri.parse("$urlCoupons $coupons_id"));
    var data = json.decode(response.body);
    Coupons employee = Coupons.fromJson(data);
    return employee;
  }
}