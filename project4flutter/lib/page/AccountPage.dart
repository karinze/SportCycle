import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:project4flutter/model/UserDetails.dart';
import 'package:project4flutter/model/Users.dart';
import 'package:project4flutter/service/UserDetailsService.dart';

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
                    prefixIcon: Icon(Icons.email, color: Color(0xFF7971EA)),
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
                  enabled: false, // Ensure this is true or omit if not necessary
                ),

            ),
                _buildTextFormField(
                  controller: _phoneController,
                  labelText: 'Phone Number',
                  icon: Icons.phone,
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
                    backgroundColor: Color(0xFF5148E5),
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
          prefixIcon: Icon(icon, color: Color(0xFF7971EA)),
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
