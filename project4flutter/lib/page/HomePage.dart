import 'dart:async';

import 'package:flutter/material.dart';
import 'package:project4flutter/service/ItemsService.dart';
import 'package:project4flutter/service/UsersService.dart';
import 'package:project4flutter/utils/color.dart';
import 'package:provider/provider.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../model/Items.dart';
import '../model/Users.dart';
import '../service/CartService.dart';
import 'CartProductPage.dart';
import 'HistoryOrderPage.dart';
import 'MyRentPage.dart';
import 'ProductDetailPage.dart';
import 'ProductPage.dart';
import 'CartPage.dart';
import 'LoginPage.dart';
import 'UserInfoPage.dart';
import 'RentPage.dart'; // Thêm import cho RentPage

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final List<String> _carouselImages = [
    'images/img3.jpg',
    'images/img2.jpg',
    'images/img1.jpg',
    'images/img4.jpg',
    'images/img5.jpg',
  ];

  late List<Items> list = [];
  late List<Items> newestItems = [];
  final ItemsService _apiService = ItemsService();
  final UsersService _usersService = UsersService();
  int _currentIndex = 0;
  PageController _pageController = PageController(viewportFraction: 0.8);
  double _currPageValue = 0.0;
  double _scaleFactor = 0.8;
  double _height = 220.0;

  @override
  void initState() {
    super.initState();
    _fetchData();
    _pageController.addListener(() {
      setState(() {
        _currPageValue = _pageController.page!;
      });
    });
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
        Users? user = await _usersService.findOne(userId);

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
    list = await _apiService.top10();
    newestItems = list.take(10).toList(); // Limit to 10 products
    setState(() {});
  }

  Future<bool> _isLoggedIn() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString('username') != null;
  }

  @override
  Widget build(BuildContext context) {
    final List<Widget> _children = [
      _buildHomePage(),
      ProductPage(),
      OrderHistoryPage(),
      MyRentPage(),
      UserInfoPage(),
    ];

    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: Center(
          child: Text(
            "SportCycle Shop",
            style: TextStyle(fontWeight: FontWeight.bold),
          ),
        ),
      ),
      body: _children[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        onTap: (index) {
          if (index == 0 && _currentIndex == 0) {
            _fetchData();
          }
          setState(() {
            _currentIndex = index;
            _fetchData();
          });
        },
        selectedItemColor: AppColor.mainColor,
        unselectedItemColor: Colors.black,
        backgroundColor: Colors.white,
        items: [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
          BottomNavigationBarItem(icon: Icon(Icons.list), label: 'Products'),
          BottomNavigationBarItem(icon: Icon(Icons.shopping_cart), label: 'History'),
          BottomNavigationBarItem(icon: Icon(Icons.directions_bike), label: 'Rent'),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: 'Me'),
        ],
      ),
      floatingActionButton: _buildFloatingActionButton(context),
    );
  }

  Widget _buildHomePage() {
    return SingleChildScrollView(
      child: Column(
        children: [
          _buildCarousel(),
          _buildNewProductsGrid(),
        ],
      ),
    );
  }

  Widget _buildCarousel() {
    return Container(
      height: 250,
      child: PageView.builder(
        controller: _pageController,
        itemCount: _carouselImages.length, // Use the fixed list of images
        itemBuilder: (context, index) {
          return _buildPageItem(index);
        },
      ),
    );
  }

  Widget _buildNewProductsGrid() {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            'New Products',
            style: TextStyle(fontSize: 24.0, fontWeight: FontWeight.bold),
          ),
          SizedBox(height: 10),
          GridView.builder(
            shrinkWrap: true,
            physics: NeverScrollableScrollPhysics(),
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 2,
              crossAxisSpacing: 10.0,
              mainAxisSpacing: 10.0,
              childAspectRatio: 2 / 3,
            ),
            itemCount: newestItems.length,
            itemBuilder: (context, index) {
              final item = newestItems[index];
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
                            child: ClipRRect(
                              borderRadius: BorderRadius.vertical(
                                top: Radius.circular(10.0),
                              ),
                              child: Image(
                                image: AssetImage('images/' + item.image),
                                fit: BoxFit.cover,
                              ),
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
                                          : null, // Disable the button if stock is 0
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
        ],
      ),
    );
  }

  Widget _buildFloatingActionButton(BuildContext context) {
    return Consumer<CartService>(
      builder: (context, cartService, child) {
        int totalItems =
        cartService.cartItems.fold(0, (sum, item) => sum + item.quantity);

        return Stack(
          alignment: Alignment.topRight,
          children: [
            FloatingActionButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => CartPage()),
                );
              },
              child: Icon(Icons.shopping_cart),
              backgroundColor: AppColor.mainColor,
            ),
            if (totalItems > 0)
              Positioned(
                right: 0,
                child: Container(
                  padding: EdgeInsets.all(2),
                  decoration: BoxDecoration(
                    color: Colors.red,
                    borderRadius: BorderRadius.circular(10),
                  ),
                  constraints: BoxConstraints(
                    minWidth: 16,
                    minHeight: 16,
                  ),
                  child: Text(
                    '$totalItems',
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 10,
                    ),
                    textAlign: TextAlign.center,
                  ),
                ),
              ),
          ],
        );
      },
    );
  }

  Widget _buildPageItem(int index) {
    Matrix4 matrix = new Matrix4.identity();
    if (index == _currPageValue.floor()) {
      var currScale = 1 - (_currPageValue - index) * (1 - _scaleFactor);
      var currTrans = _height * (1 - currScale) / 2;
      matrix = Matrix4.diagonal3Values(1, currScale, 1)..setTranslationRaw(0, currTrans, 0);
    } else if (index == _currPageValue.floor() + 1) {
      var currScale = _scaleFactor + (_currPageValue - index + 1) * (1 - _scaleFactor);
      var currTrans = _height * (1 - currScale) / 2;
      matrix = Matrix4.diagonal3Values(1, currScale, 1)..setTranslationRaw(0, currTrans, 0);
    } else if (index == _currPageValue.floor() - 1) {
      var currScale = 1 - (_currPageValue - index) * (1 - _scaleFactor);
      var currTrans = _height * (1 - currScale) / 2;
      matrix = Matrix4.diagonal3Values(1, currScale, 1)..setTranslationRaw(0, currTrans, 0);
    } else {
      var currScale = 0.8;
      matrix = Matrix4.diagonal3Values(1, currScale, 1)..setTranslationRaw(0, _height * (1 - _scaleFactor) / 2, 1);
    }

    return Transform(
      transform: matrix,
      child: GestureDetector(
        onTap: () {
          // Handle image tap if needed
        },
        child: Container(
          height: _height,
          margin: EdgeInsets.all(10),
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(30),
            color: index.isEven ? Colors.blue : Colors.green,
            image: DecorationImage(
              fit: BoxFit.cover,
              image: AssetImage(_carouselImages[index]), // Use the fixed image
            ),
          ),
        ),
      ),
    );
  }
}
