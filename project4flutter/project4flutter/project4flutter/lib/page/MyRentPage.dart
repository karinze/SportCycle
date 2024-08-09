

import 'package:flutter/material.dart';

import '../model/BikeRentals.dart';
import '../service/BikeRentalsService.dart';
import '../service/UsersService.dart';

class MyRentPage extends StatefulWidget {
  @override
  _MyRentPageState createState() => _MyRentPageState();
}

class _MyRentPageState extends State<MyRentPage> {
  List<BikeRentals> _rentals = [];

  @override
  void initState() {
    super.initState();
    _loadRentals();
  }

  void _loadRentals() async {
    String? userId = await UsersService().getUserId();
    if (userId != null) {
      List<BikeRentals> rentals = await BikeRentalsService().findUser(userId);
      setState(() {
        _rentals = rentals;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('My Rentals')),
      body: ListView.builder(
        itemCount: _rentals.length,
        itemBuilder: (context, index) {
          BikeRentals rental = _rentals[index];
          Duration timeLeft = rental.rentalEndDate.difference(DateTime.now());

          if (timeLeft.isNegative && rental.isActive) {
            // Update rental status to inactive
            setState(() {
              rental.isActive = false;
              BikeRentalsService().saveBikeRentals(rental);
            });
          }

          return ListTile(
            title: Text('${rental.item.name}'),
            subtitle: Text('Ends in: ${timeLeft.inHours} hours and ${timeLeft.inMinutes % 60} minutes'),
            trailing: rental.isActive
                ? Icon(Icons.timer, color: Colors.green)
                : Icon(Icons.check_circle, color: Colors.red),
          );
        },
      ),
    );
  }
}

