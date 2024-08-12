import 'package:http/http.dart' as http;
import 'package:project4flutter/service/CartService.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';

import '../model/Users.dart';

class UsersService {
  static const String url = "http://192.168.1.7:9999/api";
  static const String urlUsers = "$url/users/";
  static const String urlAuth = "$url/auth/";
  static const String userIdKey = 'userId';

  Future<Users?> checkLogin(String username, String password) async {
    final response = await http.get(
        Uri.parse("${urlAuth}login/$username/$username/$password"));

    print("API Response Code: ${response.statusCode}");
    print("API Response Body: ${response.body}");

    if (response.statusCode == 200) {
      if (response.body.isEmpty || response.body == 'null') {
        print("Login failed: empty or null response.");
        return null;
      }

      try {
        var data = json.decode(response.body);

        if (data == null || data.isEmpty) {
          print("Login failed: no user data returned.");
          return null;
        }

        Users user = Users.fromJson(data);

        // Save session
        final prefs = await SharedPreferences.getInstance();
        if (prefs.getString('username') != null ||
            prefs.getString('userId') != null ||
            prefs.getString('sessionToken') != null) {
          await prefs.remove('username');
          await prefs.remove('sessionToken');
          await prefs.remove('userId');
        }
        await prefs.setString('username', username);
        await prefs.setString('userId', user.user_id!);
        await prefs.setString(
            'sessionToken', user.password!); // Use an actual session token

        return user;
      } catch (e) {
        print("Error parsing JSON: $e");
        return null;
      }
    } else {
      // Nếu mã trạng thái không phải là 200, trả về null
      print("Login failed with status: ${response.statusCode}");
      return null;
    }
  }


  Future<bool> isLoggedIn() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.containsKey('username');
  }

  Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('username');
    await prefs.remove('sessionToken');
    await prefs.remove('userId');
    CartService().clearCart();
  }

  Future<List<Users>> findAll() async {
    final response = await http.get(Uri.parse(urlUsers));
    var data = json.decode(response.body);
    List<Users> list = [];
    for (var item in data) {
      Users users = Users.fromJson(item);
      list.add(users);
    }
    return list;
  }

  Future<bool> isEmailExist(String email) async {
    List<Users> usersList = await findAll();
    for (var user in usersList) {
      if (user.email == email) {
        return true;
      }
    }
    return false;
  }

  Future<bool> isUsernameExist(String username) async {
    List<Users> usersList = await findAll();
    for (var user in usersList) {
      if (user.username == username) {
        return true;
      }
    }
    return false;
  }

  Future<Users?> isEmailExistSendMail(String email) async {
    List<Users> usersList = await findAll();
    for (var user in usersList) {
      if (user.email == email) {
        return user;
      }
    }
    return null;
  }

  Future<Users?> finduserid(String users_id) async {
    List<Users> usersList = await findAll();
    for (var user in usersList) {
      if (user.user_id == users_id) {
        return user;
      }
    }
    return null;
  }

  Future<Users> findOne(String users_id) async {
    final response = await http.get(Uri.parse("$urlUsers $users_id"));
    var data = json.decode(response.body);
    print('Response data: $data');
    Users employee = Users.fromJson(data);
    return employee;
  }

  Future<void> saveUsers(Users user) async {
    try {
      final response = await http.post(
        Uri.parse(urlUsers),
        headers: {
          'Content-Type': 'application/json',
        },
        body: jsonEncode(user.toJson()),
      );

      if (response.statusCode != 200 && response.statusCode != 201) {
        print('Error: ${response.statusCode} - ${response.body}');
        throw Exception('Failed to save user');
      }
    } catch (e) {
      print('Exception: $e');
      throw Exception('Failed to save user: $e');
    }
  }

  Future<String?> getUserId() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(userIdKey);
  }

  Future<Users?> findEmail(String email) async {
    final response = await http.get(Uri.parse("$urlUsers'findemail/'$email"));
    var data = json.decode(response.body);
    if(data!=null){
      Users employee = Users.fromJson(data);
      return employee;
    }else{
      return null;
    }
  }

  Future<void> sendMail(Users users) async {
    final response = await http.post(
      Uri.parse('http://192.168.1.7:9999/api/users/sendmail'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(users.toJson()),
    );

    if (response.statusCode == 200) {
      print('Mail sent successfully.');
    } else {
      throw Exception('Failed to send mail.');
    }
  }
}
