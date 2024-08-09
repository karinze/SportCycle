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
  DateTime? _startDate;
  DateTime? _endDate;
  final TextEditingController _startDateController = TextEditingController();
  final TextEditingController _endDateController = TextEditingController();

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
            Image.asset('images/' + widget.item.image, fit: BoxFit.cover),
            SizedBox(height: 20),
            Text(
              widget.item.name,
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
              textAlign: TextAlign.center,
            ),
            SizedBox(height: 10),
            Text(
              widget.item.brand,
              style: TextStyle(fontSize: 18, color: Colors.grey[600]),
              textAlign: TextAlign.center,
            ),
            SizedBox(height: 15),
            Text(
              "Stock: ${widget.item.stock}",
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
              textAlign: TextAlign.center,
            ),
            SizedBox(height: 20),
            Text(
              '\$${widget.item.price}',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold, color: Colors.green[700]),
              textAlign: TextAlign.center,
            ),
            SizedBox(height: 20),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text('Quantity', style: TextStyle(fontSize: 18)),
                _buildQuantitySelector(),
              ],
            ),
            SizedBox(height: 30),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                ElevatedButton(
                  onPressed: _addToCart,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.blue,
                    padding: EdgeInsets.symmetric(horizontal: 20, vertical: 12),
                  ),
                  child: Text('Add to Cart'),
                ),
                ElevatedButton(
                  onPressed: () {
                    _showRentDialog(context);
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.orange,
                    padding: EdgeInsets.symmetric(horizontal: 20, vertical: 12),
                  ),
                  child: Text('Rent a Bicycle'),
                ),
              ],
            ),
          ],
        ),
      ),
    );
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
              if (_quantity < widget.item.stock) {
                _quantity++;
              } else {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text('Cannot add more items than are in stock')),
                );
              }
            });
          },
        ),
      ],
    );
  }

  void _addToCart() {
    Provider.of<CartService>(context, listen: false)
        .addToCart(widget.item, quantity: _quantity);
    Navigator.pop(context);
  }

  void _showRentDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: Text('Select Rental Date & Time'),
          content: _buildDateTimePicker(),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: Text('Cancel'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.pop(context);
                _rentBicycleProcess();
              },
              child: Text('Confirm'),
            ),
          ],
        );
      },
    );
  }

  Widget _buildDateTimePicker() {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        TextField(
          controller: _startDateController,
          decoration: InputDecoration(
            labelText: 'Start Date & Time',
            suffixIcon: IconButton(
              icon: Icon(Icons.calendar_today),
              onPressed: () async {
                DateTime? selectedDate = await showDatePicker(
                  context: context,
                  initialDate: DateTime.now(),
                  firstDate: DateTime.now(),
                  lastDate: DateTime(2100),
                );
                if (selectedDate != null) {
                  TimeOfDay? selectedTime = await showTimePicker(
                    context: context,
                    initialTime: TimeOfDay.now(),
                  );
                  if (selectedTime != null) {
                    DateTime startDate = DateTime(
                      selectedDate.year,
                      selectedDate.month,
                      selectedDate.day,
                      selectedTime.hour,
                      selectedTime.minute,
                    );

                    if (startDate.isBefore(DateTime.now())) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(content: Text('Start date and time cannot be earlier than the current date and time.')),
                      );
                    } else {
                      setState(() {
                        _startDate = startDate;
                        _startDateController.text = _startDate.toString();
                      });
                    }
                  }
                }
              },
            ),
          ),
        ),
        SizedBox(height: 10),
        TextField(
          controller: _endDateController,
          decoration: InputDecoration(
            labelText: 'End Date & Time',
            suffixIcon: IconButton(
              icon: Icon(Icons.calendar_today),
              onPressed: () async {
                DateTime? selectedDate = await showDatePicker(
                  context: context,
                  initialDate: DateTime.now().add(Duration(days: 1)),
                  firstDate: DateTime.now(),
                  lastDate: DateTime(2100),
                );
                if (selectedDate != null) {
                  TimeOfDay? selectedTime = await showTimePicker(
                    context: context,
                    initialTime: TimeOfDay.now(),
                  );
                  if (selectedTime != null) {
                    DateTime endDate = DateTime(
                      selectedDate.year,
                      selectedDate.month,
                      selectedDate.day,
                      selectedTime.hour,
                      selectedTime.minute,
                    );

                    if (_startDate != null && endDate.isBefore(_startDate!)) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(content: Text('End date cannot be earlier than start date.')),
                      );
                    } else {
                      setState(() {
                        _endDate = endDate;
                        _endDateController.text = _endDate.toString();
                      });
                    }
                  }
                }
              },
            ),
          ),
        ),
      ],
    );
  }


  Future<void> _rentBicycleProcess() async {
    bool isLoggedIn = await UsersService().isLoggedIn();
    if (!isLoggedIn) {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('You must be logged in to rent a bicycle')));
      return;
    }

    if (_startDate == null || _endDate == null) {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Please select both start and end dates')));
      return;
    }

    if (_startDate!.isBefore(DateTime.now())) {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Start date cannot be in the past.')));
      return;
    }

    if (_endDate!.isBefore(_startDate!)) {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('End date cannot be earlier than start date.')));
      return;
    }

    _rentBicycle(context, _startDate!, _endDate!);
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
        Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => MyRentPage()));
      } else {
        ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Failed to retrieve user information.')));
      }
    } else {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('User is not logged in.')));
    }
  }
}
