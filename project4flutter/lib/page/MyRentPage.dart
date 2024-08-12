import 'dart:async';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:project4flutter/model/Items.dart';
import 'package:provider/provider.dart';
import '../model/BikeRentals.dart';
import '../service/BikeRentalsService.dart';
import '../service/ItemsService.dart';
import '../service/UsersService.dart';
import '../utils/color.dart';
import 'LoginPage.dart';

class MyRentPage extends StatefulWidget {
  @override
  _MyRentPageState createState() => _MyRentPageState();
}

class _MyRentPageState extends State<MyRentPage> {
  bool _isLoggedIn = false;
  bool _isLoading = true;


  @override
  void initState() {
    super.initState();
    _checkLoginStatus();
  }

  Future<void> _checkLoginStatus() async {
    bool isLoggedIn = await UsersService().isLoggedIn();
    setState(() {
      _isLoggedIn = isLoggedIn;
      _isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(

        title: Center(
          child: Text('My Rentals',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.w600,
                color: Colors.deepPurple,
              )),
        ),
        backgroundColor: Colors.white,
        elevation: 0,
      ),
      body: _isLoading
          ? Center(child: CircularProgressIndicator())
          : _isLoggedIn
          ? MyRentalsContent() // Show rentals if logged in
          : _buildLoginPrompt(), // Show login prompt if not logged in
    );
  }

  Widget _buildLoginPrompt() {
    return Center(
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
            child: Text("Login",
                style: TextStyle(fontSize: 18, color: Colors.white)),
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
    );
  }
}

class MyRentalsContent extends StatefulWidget {
  @override
  _MyRentalsContentState createState() => _MyRentalsContentState();
}

class _MyRentalsContentState extends State<MyRentalsContent> {
  List<BikeRentals> _bikeRentals = [];
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _fetchRentals();
  }

  Future<void> _fetchRentals() async {
    String? userId = await UsersService().getUserId();
    if (userId != null) {
      List<BikeRentals> rentals = await BikeRentalsService().findUser(userId);
      setState(() {
        _bikeRentals = rentals.map((rental) {
          rental.rentalStartDate = rental.rentalStartDate.toUtc().add(Duration(hours: 7));
          rental.rentalEndDate = rental.rentalEndDate.toUtc().add(Duration(hours: 7));
          return rental;
        }).toList();
        _isLoading = false;
      });
    }
  }

  void _handleRentalEnd() {
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return _isLoading
        ? Center(child: CircularProgressIndicator())
        : _buildRentalList();
  }

  Widget _buildRentalList() {
    if (_bikeRentals.isEmpty) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.directions_bike,
              size: 100,
              color: Colors.grey[350],
            ),
            SizedBox(height: 20),
            Text('No Rentals Found.',
                style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.w500,
                    color: Colors.grey[500]))
          ],
        ),
      );
    }

    return ListView.builder(
      itemCount: _bikeRentals.length,
      itemBuilder: (context, index) {
        BikeRentals rental = _bikeRentals[index];
        bool isActive = rental.isActive;

        // Sử dụng múi giờ Việt Nam
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
                  Text('Start: ${startDate}',
                      style: TextStyle(fontSize: 16)),
                  Text('End: ${endDate}', style: TextStyle(fontSize: 16)),
                  SizedBox(height: 5),
                  isActive
                      ? CountdownTimer(
                    startDate: startDate,
                    endDate: endDate,
                    rental: rental,
                    onRentalEnd: _handleRentalEnd,
                  )
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
  final VoidCallback onRentalEnd;

  const CountdownTimer({
    required this.startDate,
    required this.endDate,
    required this.rental,
    required this.onRentalEnd,
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
    _currentTime = _getVietnamCurrentTime(); // Lấy giờ hiện tại theo múi giờ Việt Nam
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
        _currentTime = _getVietnamCurrentTime(); // Cập nhật với giờ Việt Nam
        print("Current time: $_currentTime");

        if (_currentTime.isAfter(widget.endDate)) {
          timer.cancel();
          _updateRentalStatus();
          widget.onRentalEnd(); // Thông báo tới widget cha
        }
      });
    });
  }

  void _updateRentalStatus() async {
    widget.rental.isActive = false;
    await BikeRentalsService().saveBikeRentals(widget.rental);
    int rentalquantity = widget.rental.item.rentalquantity + 1;
    Items item = Items(
        itemId: widget.rental.item.itemId,
        name: widget.rental.item.name,
        brand: widget.rental.item.brand,
        description: widget.rental.item.description,
        price: widget.rental.item.price,
        stock: widget.rental.item.stock,
        rentalquantity: rentalquantity,
        type: widget.rental.item.type,
        image: widget.rental.item.image,
        isVisible: widget.rental.item.isVisible,
        createdDt: DateTime.parse(widget.rental.item.createdDt)
    );
    await ItemsService().saveItems(item);
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
