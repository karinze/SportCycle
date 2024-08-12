import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'HomePage.dart';

class SplashPage extends StatefulWidget {
  const SplashPage({super.key});

  @override
  State<SplashPage> createState() => _SplashPageState();
}

class _SplashPageState extends State<SplashPage> with TickerProviderStateMixin {
  late Animation<double> animation;
  late AnimationController controller;

  @override
  void initState() {
    super.initState();

    // Initialize the AnimationController and start the animation
    controller = AnimationController(
        vsync: this,
        duration: const Duration(seconds: 2)
    )..forward();

    // Define the animation curve
    animation = CurvedAnimation(
        parent: controller,
        curve: Curves.easeInOut
    );

    // Delay the transition to the next page
    Timer(const Duration(seconds: 3), () {
      // Transition to HomePage after 3 seconds
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => HomePage()), // Navigate to HomePage
      );
    });
  }

  @override
  void dispose() {
    // Dispose of the controller when the widget is disposed
    controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          // Scale Transition for the logo
          ScaleTransition(
            scale: animation,
            child: Center(
              child:Image(
                image: AssetImage("images/bikelogo1.png"),
                width: 250,
              ),
              // Image.asset("images/temp/bikelogo1.png", width: 250),
            ),
          ),
          // Static second logo
          Center(
            child:Image(
              image: AssetImage("images/bikelogo2.png"),
              width: 250,
            ),
          ),
        ],
      ),
    );
  }
}
