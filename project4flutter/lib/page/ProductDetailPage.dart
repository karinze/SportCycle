import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_paypal_checkout/flutter_paypal_checkout.dart';
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
  double _rentalPrice = 0.0;
  bool _isLoading = false;

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
                  child: Text('Add to Cart',style: TextStyle(color: Colors.white),),
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
        return StatefulBuilder(
          builder: (context, setState) {
            return AlertDialog(
              title: Text('Select Rental Date & Time'),
              content: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  _buildDateTimePicker(setState),
                  if (_rentalPrice > 0)
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 20.0),
                      child: Text(
                        'Rental Price: \$$_rentalPrice',
                        style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                      ),
                    ),
                ],
              ),
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
      },
    );
  }

  Widget _buildDateTimePicker(Function setState) {
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
                        _calculateRentalPrice();
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
                        _calculateRentalPrice();
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

  void _calculateRentalPrice() {
    if (_startDate != null && _endDate != null) {
      // Calculate the duration between the start and end date in minutes
      Duration rentalDuration = _endDate!.difference(_startDate!);
      double minutesBetween = rentalDuration.inMinutes.toDouble();

      // Calculate the rental cost
      double hourlyRate = 10 / 24.0; // Equivalent of $10 per day
      double costPerMinute = hourlyRate / 60.0; // Cost per minute
      double cost = costPerMinute * minutesBetween; // Total rental cost

      // Set the rental price and update the UI
      _rentalPrice = double.parse(cost.toStringAsFixed(2));
      setState(() {});
    }
  }
  DateTime _getVietnamCurrentTime() {
    final DateTime now = DateTime.now().toUtc().add(Duration(hours: 7));
    return now;
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



    if (_endDate!.isBefore(_startDate!)) {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('End date cannot be earlier than start date.')));
      return;
    }

    await _initiatePayPalCheckout();
  }

  Future<void> _initiatePayPalCheckout() async {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => PaypalCheckout(
          clientId: 'AZDuKIu5bbjUgctSXXXrBGPKFZ_d2nFV4XFAjzDlQMcu_6GVs_RSeESb8gcPVtuJ_8L4H5SvuE0jcx36',
          secretKey: 'EEolPMf39IbyKHpW0gejHmF5-55nbkX7mdLrlAC0g-SuXp0P0QHqooPRJtI1gv8owCbMzLy4lXgV9Ai7',
          returnURL: 'success.snippetcoder.com',
          cancelURL: 'cancel.snippetcoder.com',
          sandboxMode: true,
          transactions: [
            {
              "amount": {
                "total": _rentalPrice.toStringAsFixed(2),
                "currency": "USD",
                "details": {
                  "subtotal": _rentalPrice.toStringAsFixed(2),
                },
              },
              "description": "Bike Rental",
              "payment_options": {
                "allowed_payment_method": "INSTANT_FUNDING_SOURCE"
              },
            }
          ],
          note: "Thank you for your purchase!",
          onSuccess: (Map params) async {
            await _saveRentalDetails();
          },
          onCancel: () {
            ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Payment cancelled.')));
          },
          onError: (error) {
            ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Payment error: $error')));
            print('Payment error: $error');
          },
        ),
      ),
    );
  }

  Future<void> _saveRentalDetails() async {
    setState(() {
      _isLoading = true;  // Start loading
    });

    String? userId = await UsersService().getUserId();
    if (userId != null) {
      Users? user = await UsersService().findOne(userId);
      if (user != null) {
        BikeRentals rental = BikeRentals(
          item: widget.item,
          users: user,
          rentalStartDate: _startDate!,
          rentalEndDate: _endDate!,
          isActive: true,
        );
        await BikeRentalsService().saveBikeRentals(rental);
        ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Payment successful!')));

        // Navigate to the next page after a short delay
        Future.delayed(Duration(seconds: 1), () {
          setState(() {
            _isLoading = false;  // Stop loading
          });
          Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => MyRentPage()));
        });
      } else {
        ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('Failed to retrieve user information.')));
        setState(() {
          _isLoading = false;  // Stop loading
        });
      }
    } else {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('User is not logged in.')));
      setState(() {
        _isLoading = false;  // Stop loading
      });
    }
  }
}
