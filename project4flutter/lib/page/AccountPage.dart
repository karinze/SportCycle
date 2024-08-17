import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:project4flutter/model/UserDetails.dart';
import 'package:project4flutter/model/Users.dart';
import 'package:project4flutter/service/UserDetailsService.dart';
import 'package:project4flutter/service/UsersService.dart';
import 'package:project4flutter/utils/color.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'HomePage.dart';

class AccountPage extends StatefulWidget {
  final UserDetails? userDetails;
  final Users? users;

  AccountPage({this.userDetails,this.users});

  @override
  _AccountPageState createState() => _AccountPageState();
}

class _AccountPageState extends State<AccountPage> {
  final _formKey = GlobalKey<FormState>();

  late TextEditingController _firstNameController;
  late TextEditingController _lastNameController;
  late TextEditingController _emailController;
  late TextEditingController _phoneController;
  late TextEditingController _addressController;
  late TextEditingController _noteController;

  @override
  void initState() {
    super.initState();

    // Initialize controllers with existing details or empty strings
    _firstNameController =
        TextEditingController(text: widget.userDetails?.firstName ?? '');
    _lastNameController =
        TextEditingController(text: widget.userDetails?.lastName ?? '');
    _emailController =
        TextEditingController(text: widget.users?.email ?? '');
    _phoneController =
        TextEditingController(text: widget.userDetails?.phoneNumber ?? '');
    _addressController =
        TextEditingController(text: widget.userDetails?.address ?? '');
    _noteController =
        TextEditingController(text: widget.userDetails?.note ?? '');
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
          await _logout();
          timer.cancel(); // Stop the periodic check after logging out
        }
      }
    });
  }

  Future<void> _logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.clear(); // Clear the session

    Navigator.of(context).pushAndRemoveUntil(
      MaterialPageRoute(builder: (context) => HomePage()),
          (Route<dynamic> route) => false,
    );
  }

  Future<void> _saveUserDetails() async {
    if (_formKey.currentState!.validate()) {
      UserDetails userDetails = UserDetails(
        userdetailId: widget.userDetails?.userdetailId,
        firstName: _firstNameController.text,
        lastName: _lastNameController.text,
        email: _emailController.text,
        phoneNumber: _phoneController.text,
        address: _addressController.text,
        note: _noteController.text,
        users: widget.userDetails?.users,
      );

      UserDetailsService userDetailsService = UserDetailsService();
      await userDetailsService.saveUserDetails(userDetails);

      // Show success message or navigate back
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('User details saved successfully')),
      );
      Navigator.pop(context, userDetails);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Account Settings'),
        elevation: 0,
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Form(
            key: _formKey,
            child: Column(
              children: [
                _buildTextFormField(
                  controller: _firstNameController,
                  labelText: 'First Name',
                  icon: Icons.person,
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter your first name';
                    } else if (value.length > 255) {
                      return 'Last name cannot be more than 255 characters';
                    }
                    return null;
                  },
                ),
                _buildTextFormField(
                  controller: _lastNameController,
                  labelText: 'Last Name',
                  icon: Icons.person_outline,
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter your last name';
                    } else if (value.length > 255) {
                      return 'Last name cannot be more than 255 characters';
                    }
                    return null;
                  },
                ),
                Padding(
                  padding: const EdgeInsets.only(bottom: 20.0),
                  child: TextFormField(
                    controller: _emailController,
                    decoration: InputDecoration(
                      labelText: 'Email',
                      prefixIcon: Icon(Icons.email, color: AppColor.mainColor), // Changed to red
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(15.0),
                        borderSide: BorderSide(color: Color(0xFF7971EA)),
                      ),
                      filled: true,
                      fillColor: Colors.grey[100],
                      contentPadding:
                      EdgeInsets.symmetric(vertical: 15.0, horizontal: 20.0),
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter your email';
                      }
                      return null;
                    },
                    enabled: false,
                  ),
                ),
                _buildTextFormField(
                  controller: _phoneController,
                  labelText: 'Phone Number',
                  icon: Icons.phone,
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter your phone number';
                    }
                    if (value.length < 10) {
                      return 'Phone number must be at least 10 digits';
                    }
                    if (value.length > 11) {
                      return 'Phone number must be no more than 11 digits';
                    }
                    // Check if the first digit is '0'
                    // if (!value.startsWith('0')) {
                    //   return 'Phone number must start with 0';
                    // }
                    // Check if all characters are digits
                    if (!RegExp(r'^\d+$').hasMatch(value)) {
                      return 'Phone number must contain only digits';
                    }
                    // Check for repetitive digits
                    RegExp repetitivePattern = RegExp(r'(\d)\1{9,10}');
                    if (repetitivePattern.hasMatch(value)) {
                      return 'Please do not spam a number more than 11 times';
                    }
                    return null;
                  },

                ),
                _buildTextFormField(
                  controller: _addressController,
                  labelText: 'Address',
                  icon: Icons.location_on,
                ),
                _buildTextFormField(
                  controller: _noteController,
                  labelText: 'Note',
                  icon: Icons.note,
                ),
                SizedBox(height: 30),
                ElevatedButton(
                  onPressed: _saveUserDetails,
                  child: Padding(
                    padding: const EdgeInsets.symmetric(
                        horizontal: 50, vertical: 15),
                    child: Text(
                      'Save',
                      style: TextStyle(fontSize: 18, color: Colors.white),
                    ),
                  ),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: AppColor.mainColor,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(30.0),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildTextFormField({
    required TextEditingController controller,
    required String labelText,
    required IconData icon,
    String? Function(String?)? validator,
  }) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 20.0),
      child: TextFormField(
        controller: controller,
        decoration: InputDecoration(
          labelText: labelText,
          prefixIcon: Icon(icon, color: AppColor.mainColor), // Changed to red
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(15.0),
            borderSide: BorderSide(color: Color(0xFF7971EA)),
          ),
          filled: true,
          fillColor: Colors.grey[100],
          contentPadding:
          EdgeInsets.symmetric(vertical: 15.0, horizontal: 20.0),
        ),
        validator: validator,
      ),
    );
  }
}
