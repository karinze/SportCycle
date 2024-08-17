import 'dart:async';

import 'package:flutter/material.dart';
import 'package:project4flutter/service/ItemsService.dart';
import 'package:project4flutter/utils/color.dart';
import 'package:provider/provider.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../model/Items.dart';
import '../model/Users.dart';
import '../service/CartService.dart';
import '../service/UsersService.dart';
import 'CartPage.dart';
import 'HomePage.dart';
import 'ProductDetailPage.dart';

class ProductPage extends StatefulWidget {
  @override
  _ProductPageState createState() => _ProductPageState();
}

class _ProductPageState extends State<ProductPage> {
  late List<Items> list = [];
  late List<Items> filteredList = [];
  final ItemsService _apiService = ItemsService();
  final int _itemsPerPage = 10;
  Map<String, int> _currentPages = {"Bike": 0, "Accessories": 0};
  String _searchQuery = "";
  final FocusNode _searchFocusNode = FocusNode();
  String _selectedCategory = "Bike"; // Giá trị mặc định của phân loại
  List<String> _categories = ["Bike", "Accessories"]; // Danh sách phân loại

  @override
  void initState() {
    super.initState();
    _checkAccountStatus();
    _fetchData();

    // Unfocus the search field when the page is initialized
    WidgetsBinding.instance.addPostFrameCallback((_) {
      FocusScope.of(context).unfocus();
    });
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

  void _fetchData() async {
    list = await _apiService.findAll();
    print('Fetched items: ${list.map((item) => item.type).toList()}'); // In ra loại phân loại của các sản phẩm
    _updateFilteredList();
    setState(() {});
  }

  void _updateFilteredList() {
    List<Items> currentList = list.where((item) {
      bool matchesQuery = item.name.toLowerCase().contains(_searchQuery.toLowerCase());
      bool matchesCategory = item.type.toLowerCase() == _selectedCategory.toLowerCase();
      return matchesQuery && matchesCategory;
    }).toList();

    int currentPage = _currentPages[_selectedCategory] ?? 0;
    filteredList = currentList
        .skip(currentPage * _itemsPerPage)
        .take(_itemsPerPage)
        .toList();
  }

  void _onSearchChanged(String query) {
    setState(() {
      _searchQuery = query;
      _currentPages[_selectedCategory] = 0;
      _updateFilteredList();
    });
  }

  void _onPageChanged(int page) {
    setState(() {
      _currentPages[_selectedCategory] = page;
      _updateFilteredList();
    });
  }

  void _onCategoryChanged(String category) {
    setState(() {
      _selectedCategory = category;
      _updateFilteredList();
    });
  }

  @override
  void dispose() {
    _searchFocusNode.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Expanded(
              child: TextField(
                focusNode: _searchFocusNode,
                decoration: InputDecoration(
                  hintText: 'Search',
                  prefixIcon: Icon(Icons.search, color: Colors.black54),
                  hintStyle: TextStyle(color: Colors.black45),
                  border: InputBorder.none,
                ),
                style: TextStyle(color: Colors.black),
                onChanged: _onSearchChanged,
              ),
            ),
            SizedBox(width: 10),
            DropdownButton<String>(
              value: _selectedCategory,
              onChanged: (String? newValue) {
                if (newValue != null) {
                  _onCategoryChanged(newValue);
                }
              },
              items: _categories.map((String category) {
                return DropdownMenuItem<String>(
                  value: category,
                  child: Text(category),
                );
              }).toList(),
              underline: SizedBox(),
            ),
          ],
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          children: [
            SizedBox(height: 10),
            Expanded(
              child: GridView.builder(
                gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 2,
                  crossAxisSpacing: 10.0,
                  mainAxisSpacing: 10.0,
                  childAspectRatio: 0.75,
                ),
                itemCount: filteredList.length,
                itemBuilder: (context, index) {
                  final item = filteredList[index];
                  return GestureDetector(
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => ProductDetailPage(item: item, item_id: item.itemId),
                        ),
                      );
                    },
                    child: Stack(
                      children: [
                        Card(
                          elevation: 2.0,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(10.0),
                          ),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.stretch,
                            children: [
                              Expanded(
                                child: Image(
                                  image: AssetImage('images/' + item.image),
                                  fit: BoxFit.cover,
                                ),
                              ),
                              Padding(
                                padding: const EdgeInsets.all(8.0),
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text(
                                      item.name,
                                      style: TextStyle(
                                        fontSize: 16.0,
                                        fontWeight: FontWeight.bold,
                                      ),
                                    ),
                                    Text(
                                      item.brand,
                                      style: TextStyle(
                                        fontSize: 14.0,
                                        color: Colors.grey,
                                      ),
                                    ),
                                    Row(
                                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                      children: [
                                        Text(
                                          '\$${item.price}',
                                          style: TextStyle(
                                            fontSize: 14.0,
                                            fontWeight: FontWeight.bold,
                                          ),
                                        ),
                                        IconButton(
                                          icon: Icon(Icons.add_shopping_cart),
                                          onPressed: item.stock > 0
                                              ? () {
                                            Provider.of<CartService>(context, listen: false)
                                                .addToCart(item);
                                          }
                                              : null, // Disable the button if the stock is 0
                                        ),
                                      ],
                                    ),
                                  ],
                                ),
                              ),
                            ],
                          ),
                        ),
                        if (item.stock == 0)
                          Positioned(
                            top: 0,
                            left: 0,
                            child: Container(
                              padding: EdgeInsets.symmetric(vertical: 4.0, horizontal: 8.0),
                              decoration: BoxDecoration(
                                color: Colors.red,
                                borderRadius: BorderRadius.only(
                                  topLeft: Radius.circular(10.0),
                                  bottomRight: Radius.circular(10.0),
                                ),
                              ),
                              child: Text(
                                'Out of Stock',
                                style: TextStyle(
                                  color: Colors.white,
                                  fontWeight: FontWeight.bold,
                                  fontSize: 12.0,
                                ),
                              ),
                            ),
                          ),
                      ],
                    ),
                  );
                },
              ),
            ),
            SizedBox(height: 10),
            _buildPagination(),
          ],
        ),
      ),
      floatingActionButton: Consumer<CartService>(
        builder: (context, cart, child) {
          return FloatingActionButton(
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => CartPage(),
                ),
              );
            },
            child: Stack(
              alignment: Alignment.center,
              children: [
                Icon(Icons.shopping_cart),
                if (cart.cartItems.isNotEmpty)
                  Positioned(
                    right: 8,
                    top: 8,
                    child: CircleAvatar(
                      radius: 10,
                      backgroundColor: Colors.red,
                      child: Text(
                        '${cart.cartItems.length}',
                        style: TextStyle(
                          color: Colors.white,
                          fontSize: 12,
                        ),
                      ),
                    ),
                  ),
              ],
            ),
          );
        },
      ),
    );
  }

  Widget _buildPagination() {
    int totalPages = ((_searchQuery.isEmpty ? list.where((item) => item.type.toLowerCase() == _selectedCategory.toLowerCase()).length : filteredList.length) / _itemsPerPage).ceil();

    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: List.generate(totalPages, (index) {
        return GestureDetector(
          onTap: () => _onPageChanged(index),
          child: Container(
            margin: EdgeInsets.symmetric(horizontal: 4.0),
            padding: EdgeInsets.all(8.0),
            decoration: BoxDecoration(
              color: _currentPages[_selectedCategory] == index ? AppColor.mainColor : Colors.grey,
              shape: BoxShape.circle,
            ),
            child: Text(
              '${index + 1}',
              style: TextStyle(
                color: Colors.white,
              ),
            ),
          ),
        );
      }),
    );
  }
}
