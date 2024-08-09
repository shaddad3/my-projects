
/* Description of Packages used for Project 4

Student Author:
  Samuel Haddad
  University of Illinois Chicago
  CS 378, Spring 2024

1) qr_flutter
  - This package allows you to easily get a qr code into your flutter
  app, which you then can use to link / direct your user to something
  important, offer bonuses such as coupon codes, or anything else! In
  my app, I used this package to link my users to my LinkedIn profile
  and one of my GitHub repositories.

  - To use the package you must add it to pubspec file and then import it:
  import 'package:qr_flutter/qr_flutter.dart';

2) fl_chart
  - This package allows you to add various different kinds of charts such as
  pie charts, line charts, bar charts, etc. to your flutter app by using its built
  in widgets! The package adds widgets to your disposal such as PieChart(), BarChart(),
  LineChart(), etc. which will create these kinds of charts for you, and have various
  different properties to allow for interaction and styling. In my app, I used this package
  to display the results of a 'poll' in a pie chart (the poll is actually just random data,
  but in a real app actual results and data could be put into a chart to make analysis and
  interpretation easier), and made the pie chart interactive.
  - To use the package you must add it to pubspec file and then import it:
  import 'package:fl_chart/fl_chart.dart';

3) translator
  - This package gives you a translator to allow you to translate any and all of the text
  on the screen to a different language! The translator package gives you access to the
  Google Translate API for free, allowing you to easily translate your text between languages.
  In my app I use the package to create a replica of a translator, in which the user enters
  the text they want translated and I display the translation below it. In the app the default
  language to translate to is Spanish, but there is a menu that allows the user to change the
  translate language to a couple different options (there are limited choices in my app,
  but in a more polished translator there could be hundreds of options!).
  - To use the package you must add it to pubspec file and then import it:
  import 'package:translator/translator.dart';

4) speech_to_text
  - This package gives you the ability to record the user's voice, and with this you can do
  things such as store their words to display to them later, translate their spoken words into
  another language, and a lot more! In my app I did not get this to work, as for some reason it
  never recorded anything and would seemingly start recording and immediately stop. The original
  idea was to combine this package with the translator package to make a translator that would
  translate spoken words into a selected language, such as Spanish, English, etc. but in the end
  I had to give up on the speech to text part.
  - To use the package you must add it to pubspec file and then import it:
  import 'package:speech_to_text/speech_to_text.dart';

*/


import 'package:flutter/material.dart';
import 'TranslatorScreen.dart';
import 'ChartLandingPage.dart';
import 'QRScreen.dart';


void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // backgroundColor: const Color.fromARGB(255, 195, 177, 225),
      backgroundColor: const Color.fromARGB(255, 250, 230, 250),
      appBar: AppBar(
        centerTitle: true,
        // backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        backgroundColor: Colors.purple[100],
        title: const Text(" Flutter Packages' Showcase!"),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(30),
        ),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'Pick any of the following to see that package in use!',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 10,),
            OutlinedButton(
              onPressed: () => Navigator.push(
                  context, MaterialPageRoute(builder: (context) => const QRScreen())
              ),
              child: Container(
                // color: Colors.white,
                height: 220,
                width: 200,
                decoration: const BoxDecoration(
                  shape: BoxShape.circle,
                  image: DecorationImage(
                    scale: 1.5,
                    image: AssetImage("assets/qrCode.png"),
                  ),
                ),
                child: const Padding(
                  padding: EdgeInsets.fromLTRB(0, 10, 0, 0),
                  child: Text(
                    "QR Codes",
                    style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold, fontSize: 18), textAlign: TextAlign.center,
                  ),
                ),
              ),
            ),
            const SizedBox(height: 10),
            OutlinedButton(
              onPressed: () => Navigator.push(
                  context, MaterialPageRoute(builder: (context) => const ChartLandingPage())
              ),
              child: Container(
                height: 220,
                width: 200,
                decoration: const BoxDecoration(
                  shape: BoxShape.circle,
                  image: DecorationImage(
                    scale: 1.5,
                    image: AssetImage("assets/charts.jpeg"),
                  ),
                ),
                child: const Padding(
                  padding: EdgeInsets.fromLTRB(0, 10, 0, 0),
                  child: Text(
                    "Charts",
                    style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold, fontSize: 18), textAlign: TextAlign.center,
                  ),
                ),
              ),
            ),
            const SizedBox(height: 10,),
            OutlinedButton(
              onPressed: () => Navigator.push(
                  context, MaterialPageRoute(builder: (context) => const TranslatorScreen())
              ),
              child: Container(
                height: 220,
                width: 200,
                decoration: const BoxDecoration(
                  shape: BoxShape.circle,
                  image: DecorationImage(
                    scale: 1.5,
                    fit: BoxFit.cover,
                    image: AssetImage("assets/translate.png"),
                  ),
                ),
                child: const Padding(
                  padding: EdgeInsets.fromLTRB(0, 10, 0, 0),
                  child: Text(
                    "Translator",
                    style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold, fontSize: 18), textAlign: TextAlign.center,
                  ),
                ),
              ),
            ),
          ],
        ),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
