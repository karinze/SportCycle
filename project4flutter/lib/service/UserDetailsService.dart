import 'package:http/http.dart' as http;
import 'package:project4flutter/model/UserDetails.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';

import '../model/Users.dart';

class UserDetailsService {
  static const String url = "http://192.168.1.7:9999/api";
  static const String urlUserDetails = "$url/userdetail/";
  static const String userIdKey = 'userId';
  Future<UserDetails> findOne(int userDetailsId) async {
    final response = await http.get(Uri.parse("$urlUserDetails$userDetailsId"));

    if (response.statusCode == 200) {
      var data = json.decode(response.body);
      print('Response data: $data'); // Print the response for debugging
      return UserDetails.fromJson(data);
    } else {
      throw Exception('Failed to load user details');
    }
  }

  Future<List<UserDetails>> findUserDetails(String userId) async {
    final response = await http.get(Uri.parse("${urlUserDetails}user/$userId"));

    if (response.statusCode == 201 || response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);

      // Convert the list of JSON maps to a list of UserDetails objects
      List<UserDetails> userDetailsList = data.map((jsonItem) {
        return UserDetails.fromJson(jsonItem);
      }).toList();

      return userDetailsList;
    } else {
      throw Exception('Failed to load user details');
    }
  }

  Future<UserDetails> saveUserDetails(UserDetails userDetails) async {
    final response = await http.post(
      Uri.parse(urlUserDetails),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(userDetails.toJson()),
    );

    if (response.statusCode == 201 || response.statusCode == 200) {
      return UserDetails.fromJson(json.decode(response.body));
    } else {
      print('Response body: ${response.body}');
      throw Exception('Failed to save user details');
    }
  }

  Future<String?> getUserId() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(userIdKey);
  }

  Future<bool> isLoggedIn() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString('username') != null;
  }
}
