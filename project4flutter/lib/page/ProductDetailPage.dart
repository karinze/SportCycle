import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../model/BikeRentals.dart';
import '../model/Items.dart';
import '../model/Users.dart';
import '../service/BikeRentalsService.dart';
import '../service/CartService.dart';
import '../service/UsersService.dart';
import 'MyRentPage.dart';

class ProductDetailPage extends StatefulWidget {
  final Items item;

  const ProductDetailPage({super.key, required this.item});

  @override
  _ProductDetailPageState createState() => _ProductDetailPageState();
}

class _ProductDetailPageState extends State<ProductDetailPage> {
  int _quantity = 1;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.item.name),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Image(image: AssetImage('images/' + widget.item.image)),
            SizedBox(height: 20),
            Text(
              widget.item.name,
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 10),
            Text(widget.item.brand),
            SizedBox(height: 20),
            Text(
              '\$${widget.item.price}',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 20),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text('Quantity', style: TextStyle(fontSize: 18)),
                _buildQuantitySelector(),
              ],
            ),
            SizedBox(height: 20),
            Row(
              children: [
                ElevatedButton(
                  onPressed: () {
                    Provider.of<CartService>(context, listen: false)
                        .addToCart(widget.item, quantity: _quantity);
                    Navigator.pop(context);
                  },
                  child: Text('Add to Cart'),

                ),
                ElevatedButton(
                  onPressed: () async {
                    // Check if the user is logged in
                    bool isLoggedIn = await UsersService().isLoggedIn();
                    if (!isLoggedIn) {
                      // Show a message and navigate to the login page
                      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('You must be logged in to rent a bicycle')));
                      // Navigate to login page here, if necessary
                      return;
                    }

                    // Show the date selection dialog
                    _selectDateTime(context, (DateTime start, DateTime end) {
                      // Rent the bicycle if dates are selected
                      _rentBicycle(context, start, end);
                    });
                  },
                  child: Text('Rent a Bicycle'),
                ),

              ],
            ),

          ],
        ),
      ),
    );
  }

  Future<void> _selectDateTime(BuildContext context, Function(DateTime, DateTime) onDateTimeSelected) async {
    DateTime? startDate;
    DateTime? endDate;

    // Select start date
    startDate = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime.now(),
      lastDate: DateTime(2100),
    );

    if (startDate != null) {
      final TimeOfDay? startTime = await showTimePicker(
        context: context,
        initialTime: TimeOfDay.now(),
      );

      if (startTime != null) {
        // Combine the selected date and time
        startDate = DateTime(startDate.year, startDate.month, startDate.day, startTime.hour, startTime.minute);
      } else {
        return; // Time selection canceled
      }
    } else {
      return; // Date selection canceled
    }

    // Select end date
    endDate = await showDatePicker(
      context: context,
      initialDate: startDate.add(Duration(hours: 1)),
      firstDate: startDate,
      lastDate: DateTime(2100),
    );

    if (endDate != null) {
      final TimeOfDay? endTime = await showTimePicker(
        context: context,
        initialTime: TimeOfDay.fromDateTime(startDate.add(Duration(hours: 1))),
      );

      if (endTime != null) {
        // Combine the selected date and time
        endDate = DateTime(endDate.year, endDate.month, endDate.day, endTime.hour, endTime.minute);
      } else {
        return; // Time selection canceled
      }
    } else {
      return; // Date selection canceled
    }

    // Validation check: End date must be after start date
    if (endDate.isAfter(startDate)) {
      onDateTimeSelected(startDate, endDate);
    } else {
      // Show an error message
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('End date must be after start date')));
    }
  }

  void _rentBicycle(BuildContext context, DateTime startDate, DateTime endDate) async {
    String? userId = await UsersService().getUserId();
    if (userId != null) {
      Users? user = await UsersService().findOne(userId);
      if (user != null) {
        BikeRentals rental = BikeRentals(
          item: widget.item,
          users: user,
          rentalStartDate: startDate,
          rentalEndDate: endDate,
          isActive: true,
        );
        await BikeRentalsService().saveBikeRentals(rental);
        Navigator.push(context, MaterialPageRoute(builder: (context) => MyRentPage()));
      } else {
        ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Failed to retrieve user information.')));
      }
    } else {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('User is not logged in.')));
    }
  }


  Widget _buildQuantitySelector() {
    return Row(
      children: [
        IconButton(
          icon: Icon(Icons.remove),
          onPressed: () {
            setState(() {
              if (_quantity > 1) _quantity--;
            });
          },
        ),
        Text(
          '$_quantity',
          style: TextStyle(fontSize: 18),
        ),
        IconButton(
          icon: Icon(Icons.add),
          onPressed: () {
            setState(() {
              _quantity++;
            });
          },
        ),
      ],
    );
  }
}
