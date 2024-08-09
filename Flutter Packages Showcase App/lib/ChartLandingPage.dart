
import 'package:flutter/material.dart';
import 'ChartsScreen.dart';

class ChartLandingPage extends StatelessWidget {
  const ChartLandingPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color.fromARGB(255, 235, 225, 220),
      appBar: AppBar(
        centerTitle: true,
        title: const Text("Chart Landing Page"),
        backgroundColor: Colors.brown[200],
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(30),
        ),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Padding(
              padding: EdgeInsets.all(20),
              child: Text(
                "Click the button below to generate a pie chart showcasing the results of a poll to determine"
                    " the best class out of these 5 options: CS 342, CS 378, CS 480, CS 421, and CS 401.",
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
                textAlign: TextAlign.center,
              ),
            ),
            const SizedBox(height: 50),
            OutlinedButton(
              onPressed: () => Navigator.push(
                  context, MaterialPageRoute(builder: (context) => const ChartsScreen())
              ),
              child: const Text(
                "Generate!", style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
              ),
            ),
          ],
        ),
      ),
    );
  }
}