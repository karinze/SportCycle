import 'package:http/http.dart' as http;
import 'dart:convert';

class APIService{
  static const String url = "http://192.168.1.4:9999/api";
  static const String urlBikeproperties = "$url/bikeproperties";
  static const String urlBikerentals = "$url/bikerentals";
  static const String urlExternalTokens = "$url/externalTokens";
  static const String urlOrders = "$url/orders";
  static const String urlOrderitems = "$url/orderitems";
  static const String urlItems = "$url/items";
  static const String urlUsers = "$url/users";


}