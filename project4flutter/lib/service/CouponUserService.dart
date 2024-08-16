import 'dart:convert';

import 'package:project4flutter/model/Coupons.dart';
import 'package:http/http.dart' as http;

import '../model/CouponUsers.dart';
import 'APIService.dart';

class CouponUser{
  // static const String url = "http://192.168.1.8:9999/api";
  static const String urlCouponUsers = "${APIService.url}/couponusers/";

  Future<List<CouponUsers>> findAll() async {
    final response = await http.get(Uri.parse(urlCouponUsers));
    var data = json.decode(response.body);
    List<CouponUsers> list = [];
    for (var item in data) {
      CouponUsers couponUsers = CouponUsers.fromJson(item);
      list.add(couponUsers);
    }
    return list;
  }

  Future<CouponUsers> saveCouponUsers(CouponUsers couponUsers) async {
    final response = await http.post(
      Uri.parse(urlCouponUsers),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(couponUsers.toJson()),
    );

    if (response.statusCode == 201) {
      return CouponUsers.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed...');
    }
  }

  Future<List<CouponUsers>> findUser(String userId) async {
    final response = await http.get(Uri.parse("${urlCouponUsers}user/$userId"));
    var data = json.decode(response.body);
    List<CouponUsers> list = [];
    for (var item in data) {
      CouponUsers couponUsers = CouponUsers.fromJson(item);
      list.add(couponUsers);
    }
    return list;
  }

  Future<List<CouponUsers>> findOrder(int couponId) async {
    final response = await http.get(Uri.parse("${urlCouponUsers}coupon/$couponId"));
    var data = json.decode(response.body);
    List<CouponUsers> list = [];
    for (var item in data) {
      CouponUsers couponUsers = CouponUsers.fromJson(item);
      list.add(couponUsers);
    }
    return list;
  }

  Future<CouponUsers> findOne(int couponUsers_id) async {
    final response = await http.get(Uri.parse("$urlCouponUsers $couponUsers_id"));
    var data = json.decode(response.body);
    CouponUsers employee = CouponUsers.fromJson(data);
    return employee;
  }
}