import 'package:flutter/material.dart';

import 'package:qr_flutter/qr_flutter.dart';

class QRScreen extends StatelessWidget {
  const QRScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // backgroundColor: Colors.yellow[100],
      backgroundColor: const Color.fromARGB(255, 255, 253, 208),
      appBar: AppBar(
        centerTitle: true,
        title: const Text("QR Code Showcase"),
        backgroundColor: Colors.amber[100],
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(30),
        ),
      ),
      body: Center(
        child: Container(
          // decoration: const BoxDecoration(
          //   image: DecorationImage(
          //     image: AssetImage("assets/linkedin.png")
          //   )
          // ),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Padding(
                padding: EdgeInsets.all(10),
                child: Text(
                  "Scan the QR code below to connect with me on LinkedIn!",
                  textAlign: TextAlign.center,
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                ),
              ),
              QrImageView(
                data: "https://www.linkedin.com/in/sammy-haddad-28207b27b/",
                size: 200,
              ),
              const SizedBox(height: 40),
              const Padding(
                padding: EdgeInsets.all(10),
                child: Text(
                  "Alternatively, check out my GitHub repo to see the projects "
                      "I've done so far! (these are all UIC school projects so you've likely done them too)",
                  textAlign: TextAlign.center,
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                ),
              ),
              QrImageView(
                data: "https://github.com/shaddad3/my-projects",
                size: 200,
              ),
            ],
          ),
        ),
      ),
    );
  }
}