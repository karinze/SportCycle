import 'package:http/http.dart' as http;
import 'package:project4flutter/model/Items.dart';
import 'package:project4flutter/model/Orders.dart';
import 'dart:convert';

import '../model/OrderItems.dart';

class OrderItemsService{
  static const String url = "http://192.168.1.7:9999/api";
  static const String urlOrderitems = "$url/orderitems/";

  Future<List<OrderItems>> findAll() async {
    final response = await http.get(Uri.parse(urlOrderitems));
    var data = json.decode(response.body);
    List<OrderItems> list = [];
    for (var item in data) {
      OrderItems orderItems = OrderItems.fromJson(item);
      list.add(orderItems);
    }
    return list;
  }

  Future<OrderItems> saveOrderItems(OrderItems orderItems) async {
    final response = await http.post(
      Uri.parse(urlOrderitems),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(orderItems.toJson()),
    );

    if (response.statusCode == 201) {
      return OrderItems.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed...');
    }
  }

  Future<List<OrderItems>> findOrder(Orders order) async {
    int o = order.orderId;
    final response = await http.get(Uri.parse("${urlOrderitems}order/$o"));
    if (response.statusCode == 201 || response.statusCode == 200) {
      var data = json.decode(response.body);

      // Check if the response contains a list
      if (data is List) {
        List<OrderItems> list = [];
        for (var item in data) {
          OrderItems orderItems = OrderItems.fromJson(item);
          list.add(orderItems);
        }
        return list;
      } else {
        // If the response is an object containing a list, adjust accordingly
        if (data['orderItems'] is List) {
          List<OrderItems> list = [];
          for (var item in data['orderItems']) {
            OrderItems orderItems = OrderItems.fromJson(item);
            list.add(orderItems);
          }
          return list;
        } else {
          throw Exception('Unexpected JSON structure');
        }
      }
    } else {
      throw Exception('Failed to load order items');
    }
  }

  Future<List<OrderItems>> findItems(int itemid) async {
    final response = await http.get(Uri.parse("$urlOrderitems items/ $itemid"));
    var data = json.decode(response.body);
    List<OrderItems> list = [];
    for (var item in data) {
      OrderItems orderItems = OrderItems.fromJson(item);
      list.add(orderItems);
    }
    return list;
  }
}