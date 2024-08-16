import 'package:flutter/material.dart';
import 'package:project4flutter/service/ItemsService.dart';
import 'package:project4flutter/utils/color.dart';
import 'package:provider/provider.dart';
import '../model/Items.dart';
import '../service/CartService.dart';
import 'CartPage.dart';
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
    _fetchData();

    // Unfocus the search field when the page is initialized
    WidgetsBinding.instance.addPostFrameCallback((_) {
      FocusScope.of(context).unfocus();
    });
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
                    child: Card(
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
                                      onPressed: () {
                                        Provider.of<CartService>(context, listen: false)
                                            .addToCart(item);
                                      },
                                    ),
                                  ],
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
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