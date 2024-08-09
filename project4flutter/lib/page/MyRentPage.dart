import 'dart:async';

import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';
import '../model/BikeRentals.dart';
import '../service/BikeRentalsService.dart';
import '../service/UsersService.dart';
import 'LoginPage.dart';

class MyRentPage extends StatefulWidget {
  @override
  _MyRentPageState createState() => _MyRentPageState();
}

class _MyRentPageState extends State<MyRentPage> {
  List<BikeRentals> _bikeRentals = [];
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _fetchRentals();
  }

  Future<void> _fetchRentals() async {
    bool isLoggedIn = await UsersService().isLoggedIn();
    if (isLoggedIn) {
      String? userId = await UsersService().getUserId();
      if (userId != null) {
        List<BikeRentals> rentals = await BikeRentalsService().findUser(userId);
        setState(() {
          _bikeRentals = rentals;
          _isLoading = false;
        });
      }
    } else {
      Navigator.pushReplacement(
          context, MaterialPageRoute(builder: (context) => LoginPage()));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('My Rentals'),
      ),
      body: _isLoading
          ? Center(child: CircularProgressIndicator())
          : _buildRentalList(),
    );
  }

  Widget _buildRentalList() {
    if (_bikeRentals.isEmpty) {
      return Center(
          child: Text('No rentals found.',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.w500)));
    }

    return ListView.builder(
      itemCount: _bikeRentals.length,
      itemBuilder: (context, index) {
        BikeRentals rental = _bikeRentals[index];
        bool isActive = rental.isActive;
        DateTime endDate = rental.rentalEndDate;
        DateTime startDate = rental.rentalStartDate;

        return Padding(
          padding: const EdgeInsets.symmetric(horizontal: 8.0, vertical: 4.0),
          child: Card(
            elevation: 5,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10),
            ),
            child: ListTile(
              contentPadding:
              EdgeInsets.symmetric(horizontal: 16.0, vertical: 10.0),
              leading: Icon(
                isActive ? Icons.pedal_bike : Icons.check_circle,
                color: isActive ? Colors.green : Colors.grey,
                size: 40,
              ),
              title: Text(rental.item.name,
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
              subtitle: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text('Start: ${rental.rentalStartDate}',
                      style: TextStyle(fontSize: 16)),
                  Text('End: ${endDate}', style: TextStyle(fontSize: 16)),
                  SizedBox(height: 5),
                  isActive
                      ? CountdownTimer(startDate: startDate, endDate: endDate, rental: rental)
                      : Text('Status: Completed',
                      style: TextStyle(
                          fontSize: 16, color: Colors.redAccent)),
                ],
              ),
            ),
          ),
        );
      },
    );
  }
}


class CountdownTimer extends StatefulWidget {
  final DateTime startDate;
  final DateTime endDate;
  final BikeRentals rental;

  const CountdownTimer({
    required this.startDate,
    required this.endDate,
    required this.rental,
  });

  @override
  _CountdownTimerState createState() => _CountdownTimerState();
}

class _CountdownTimerState extends State<CountdownTimer>
    with SingleTickerProviderStateMixin {
  late DateTime _currentTime;
  late Timer _timer;
  late AnimationController _animationController;

  @override
  void initState() {
    super.initState();
    _currentTime = _getVietnamCurrentTime(); // Get the current time in Vietnam's time zone
    _animationController =
    AnimationController(vsync: this, duration: Duration(seconds: 1))
      ..repeat();
    _startTimer();
  }

  DateTime _getVietnamCurrentTime() {
    final DateTime now = DateTime.now().toUtc().add(Duration(hours: 7));
    return now;
  }

  void _startTimer() {
    _timer = Timer.periodic(Duration(seconds: 1), (timer) {
      setState(() {
        _currentTime = _getVietnamCurrentTime(); // Update with Vietnam time
        print("Current time: $_currentTime");

        if (_currentTime.isAfter(widget.endDate)) {
          timer.cancel();
          _updateRentalStatus();
        }
      });
    });
  }

  void _updateRentalStatus() async {
    widget.rental.isActive = false;
    await BikeRentalsService().saveBikeRentals(widget.rental);
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        content: Text('Rental for ${widget.rental.item.name} has ended.')));
  }

  @override
  void dispose() {
    _animationController.dispose();
    _timer.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    if (_currentTime.isBefore(widget.startDate)) {
      return Text(
        "Rental has not started yet.",
        style: TextStyle(fontSize: 16, color: Colors.orangeAccent),
      );
    }

    Duration remainingTime = widget.endDate.difference(_currentTime);

    if (remainingTime.isNegative) {
      return Text('Status: Completed',
          style: TextStyle(fontSize: 16, color: Colors.redAccent));
    } else {
      return AnimatedBuilder(
        animation: _animationController,
        builder: (context, child) {
          return Text(
            'Remaining: ${remainingTime.inHours}h ${remainingTime.inMinutes.remainder(60)}m ${remainingTime.inSeconds.remainder(60)}s',
            style: TextStyle(
                fontSize: 16,
                color: remainingTime.inMinutes <= 5
                    ? Colors.red
                    : Colors.blueAccent,
                fontWeight: FontWeight.bold),
          );
        },
      );
    }
  }
}





