import 'dart:async';

import 'package:flutter/material.dart';
import 'package:project4flutter/page/HomePage.dart';
import 'package:project4flutter/service/CartService.dart';
import 'package:project4flutter/service/UserDetailsService.dart';
import 'package:project4flutter/service/UsersService.dart';
import 'package:project4flutter/utils/color.dart';
import 'package:provider/provider.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../model/UserDetails.dart';
import '../model/Users.dart';
import 'AccountPage.dart';
import 'LoginPage.dart';

class UserInfoPage extends StatefulWidget {
  @override
  _UserInfoPageState createState() => _UserInfoPageState();
}

class _UserInfoPageState extends State<UserInfoPage> {
  String? _username;
  UserDetails? _userDetails;
  Users? _users;
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadUserInfo();
    _checkAccountStatus();
  }

  void _checkAccountStatus() async {
    final interval = Duration(seconds: 2); // Check every 5 minutes
    Timer.periodic(interval, (timer) async {
      final prefs = await SharedPreferences.getInstance();
      final username = prefs.getString('username');
      final userId = prefs.getString('userId');

      if (username != null && userId != null) {
        // Call an API to get the latest user info
        Users? user = await UsersService().findOne(userId);

        if (user != null && user.block) {
          // If account is blocked, log the user out
          await _logoutt();
          timer.cancel(); // Stop the periodic check after logging out
        }
      }
    });
  }

  Future<void> _logoutt() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.clear(); // Clear the session

    Navigator.of(context).pushAndRemoveUntil(
      MaterialPageRoute(builder: (context) => HomePage()),
          (Route<dynamic> route) => false,
    );
  }

  Future<void> _loadUserInfo() async {
    final prefs = await SharedPreferences.getInstance();
    final userId = prefs.getString('userId');
    _username = prefs.getString('username');

    if (userId != null) {
      final userDetailsService = UserDetailsService();
      final userDetails = await userDetailsService.findUserDetails(userId);
      final user = await UsersService().findOne(userId!);
      _users = user;
      if (userDetails.isNotEmpty) {
        setState(() {
          _userDetails = userDetails.first;
        });
      }
    }
    setState(() {
      _isLoading = false;
    });
  }

  Future<void> _logout(BuildContext context) async {
    final CartService cartService = Provider.of<CartService>(context, listen: false);
    final prefs = await SharedPreferences.getInstance();
    await prefs.clear(); // Clear all preferences
    cartService.clearCart();
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (context) => HomePage()),
    );
  }

  Future<void> _navigateToAccountPage() async {
    final updatedUserDetails = await Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => AccountPage(userDetails: _userDetails,users: _users,),
      ),
    );

    if (updatedUserDetails != null && updatedUserDetails is UserDetails) {
      setState(() {
        _userDetails = updatedUserDetails;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _isLoading
          ? Center(child: CircularProgressIndicator())
          : _username != null
          ? SingleChildScrollView(
        child: Column(
          children: [
            Container(
              width: double.infinity,
              padding: EdgeInsets.symmetric(vertical: 30, horizontal: 20),
              decoration: BoxDecoration(
                gradient: LinearGradient(
                  colors: [Color(0xFF89dad0), Color(0xFF5148E5)],
                  begin: Alignment.topLeft,
                  end: Alignment.bottomRight,
                ),
              ),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  CircleAvatar(
                    radius: 50,
                    backgroundColor: Colors.white,
                    child: Text(
                      _users?.username.isNotEmpty == true
                          ? _users!.username[0]
                          : "?",
                      style: TextStyle(
                        fontSize: 40,
                        color: Color(0xFF7971EA),
                      ),
                    ),
                  ),
                  SizedBox(height: 15),
                  Text(
                    '${_users?.username ?? "Unknown"}',
                    style: TextStyle(
                      fontSize: 26,
                      color: Colors.white,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 10),
                  Text(
                    _users?.email ?? "Email not available",
                    style: TextStyle(
                      fontSize: 20,
                      color: Colors.white70,
                    ),
                  ),
                ],
              ),
            ),
            SizedBox(height: 20),
            ListTile(
              leading: Icon(Icons.phone, color: Color(0xFF89dad0)),
              title: Text(
                'Phone: ${_userDetails?.phoneNumber.isNotEmpty == true ? _userDetails!.phoneNumber : "Not available"}',
                style: TextStyle(fontSize: 18),
              ),
            ),
            Divider(),
            ListTile(
              leading: Icon(Icons.location_on, color: Color(0xFF89dad0)),
              title: Text(
                'Address: ${_userDetails?.address.isNotEmpty == true ? _userDetails!.address : "Not available"}',
                style: TextStyle(fontSize: 18),
              ),
            ),
            Divider(),
            ListTile(
              leading: Icon(Icons.manage_accounts_outlined, color: Color(0xFF89dad0)),
              title: Text(
                'Profile',
                style: TextStyle(fontSize: 18),
              ),
              trailing: Icon(Icons.arrow_forward_ios, color: Colors.grey[400]),
              onTap: _navigateToAccountPage,
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () => _logout(context),
              child: Text("Logout", style: TextStyle(fontSize: 18, color: Colors.white)),
              style: ElevatedButton.styleFrom(
                backgroundColor: AppColor.login,
                padding: EdgeInsets.symmetric(horizontal: 50, vertical: 15),
                textStyle: TextStyle(fontSize: 20),
              ),
            ),
            SizedBox(height: 20),
          ],
        ),
      )
          : Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.person_outline,
              size: 100,
              color: Colors.grey[400],
            ),
            SizedBox(height: 20),
            Text(
              'No user found. Please login.',
              style: TextStyle(fontSize: 18, color: Colors.grey[600]),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () => Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => LoginPage()),
              ),
              child: Text("Login",style: TextStyle(fontSize: 18, color: Colors.white)),
              style: ElevatedButton.styleFrom(
                backgroundColor: AppColor.login,
                padding: EdgeInsets.symmetric(horizontal: 40, vertical: 12),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(30),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
