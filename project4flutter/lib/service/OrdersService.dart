import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:project4flutter/model/Users.dart';
import '../model/OrderItems.dart';
import '../model/Orders.dart';
import 'package:http_parser/http_parser.dart';

class OrdersService{
  static const String url = "http://192.168.1.7:9999/api";
  static const String urlOrders = "$url/orders/";

  Future<List<Orders>> findAll() async {
    final response = await http.get(Uri.parse(urlOrders));
    var data = json.decode(response.body);
    List<Orders> list = [];
    for (var item in data) {
      Orders orders = Orders.fromJson(item);
      list.add(orders);
    }
    return list;
  }

  Future<Orders> saveOrder(Orders orders) async {
    final response = await http.post(
      Uri.parse(urlOrders),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(orders.toJson()),
    );

    if (response.statusCode == 201) {
      return Orders.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed...');
    }
  }

  Future<List<Orders>> findUser(String userId) async {
    final response = await http.get(Uri.parse("${urlOrders}users/ $userId"));
    var data = json.decode(response.body);
    List<Orders> list = [];
    for (var item in data) {
      Orders orders = Orders.fromJson(item);
      list.add(orders);
    }
    return list;
  }

  Future<Orders> findOne(int orders_id) async {
    final response = await http.get(Uri.parse("$urlOrders $orders_id"));
    var data = json.decode(response.body);
    Orders employee = Orders.fromJson(data);
    return employee;
  }


  Future<void> sendMail(Users users, Orders orders, List<OrderItems> orderItems) async {
    final uri = Uri.parse("${urlOrders}sendbillmail");
    final request = http.MultipartRequest('POST', uri);

    // Add users part
    request.files.add(http.MultipartFile.fromString(
      'users',
      jsonEncode(users.toJson()),
      contentType: MediaType('application', 'json'),
    ));

    // Add orders part
    request.files.add(http.MultipartFile.fromString(
      'orders',
      jsonEncode(orders.toJson()),
      contentType: MediaType('application', 'json'),
    ));

    // Convert orderItems list to JSON array string
    String orderItemsJson = jsonEncode(orderItems.map((item) => item.toJson()).toList());

    // Add orderItems part
    request.files.add(http.MultipartFile.fromString(
      'orderItems',
      orderItemsJson,
      contentType: MediaType('application', 'json'),
    ));

    // Send the request
    final response = await request.send();

    // Read the response
    final responseString = await response.stream.bytesToString();

    if (response.statusCode == 200) {
      print('Mail sent successfully');
    } else {
      print('Failed to send mail: ${response.statusCode}');
      print('Response body: $responseString');
      throw Exception('Failed to send mail');
    }
  }
}