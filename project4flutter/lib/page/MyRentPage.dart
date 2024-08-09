import 'dart:async';

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
  Timer? _timer;

  @override
  void initState() {
    super.initState();
    _loadRentals();
    _startTimer();
  }

  void _startTimer() {
    _timer = Timer.periodic(Duration(minutes: 1), (timer) {
      setState(() {}); // Rebuilds the widget to update the countdown timers
    });
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }

  void _loadRentals() async {
    String? userId = await UsersService().getUserId();
    if (userId != null) {
      List<BikeRentals> rentals = await BikeRentalsService().findUser(userId);
      setState(() {
        _rentals = rentals;
      });
      _checkAndUpdateRentals();
    } else {
      Navigator.pushReplacementNamed(context, '/login');
    }
  }

  void _checkAndUpdateRentals() {
    for (var rental in _rentals) {
      Duration timeLeft = rental.rentalEndDate.difference(DateTime.now());

      if (timeLeft.isNegative && rental.isActive) {
        setState(() {
          rental.isActive = false;
        });
        BikeRentalsService().saveBikeRentals(rental);
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('My Rentals')),
      body: _rentals.isEmpty
          ? Center(child: Text('No rentals found.'))
          : ListView.builder(
        itemCount: _rentals.length,
        itemBuilder: (context, index) {
          BikeRentals rental = _rentals[index];
          Duration timeLeft = rental.rentalEndDate.difference(DateTime.now());

          return ListTile(
            title: Text('${rental.item.name}'),
            subtitle: rental.isActive
                ? Text('Ends in: ${timeLeft.inHours} hours and ${timeLeft.inMinutes % 60} minutes')
                : Text('Rental completed'),
            trailing: rental.isActive
                ? Icon(Icons.timer, color: Colors.green)
                : Icon(Icons.check_circle, color: Colors.red),
          );
        },
      ),
    );
  }
}

