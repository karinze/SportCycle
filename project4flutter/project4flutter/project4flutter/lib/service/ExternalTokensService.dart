import 'package:project4flutter/model/Users.dart';

import '../model/ExternalTokens.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class ExternalTokensService{
  static const String url = "http://192.168.1.4:9999/api";
  static const String urlExternalTokens = "$url/externalTokens/";

  Future<ExternalTokens> saveBikeRentals(ExternalTokens externalTokens) async {
    final response = await http.post(
      Uri.parse(urlExternalTokens),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(externalTokens.toJson()),
    );

    if (response.statusCode == 201) {
      return ExternalTokens.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed...');
    }
  }

  Future<ExternalTokens> findOne(Users user) async {
    final response = await http.get(Uri.parse("$urlExternalTokens findbyuser/ $user"));
    var data = json.decode(response.body);
    ExternalTokens employee = ExternalTokens.fromJson(data);
    return employee;
  }
}