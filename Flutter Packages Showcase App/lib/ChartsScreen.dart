
import 'dart:math';
import 'package:flutter/material.dart';
import 'SliceInfo.dart';

import 'package:fl_chart/fl_chart.dart';

class ChartsScreen extends StatefulWidget {
  const ChartsScreen({super.key});

  @override
  Charts createState() => Charts();

}

class Charts extends State<ChartsScreen> {

  int? selectedIndex;
  bool isSelected = false;
  List<double> values = List.generate(5, (index) => Random().nextInt(10).toDouble() + 2);
  List<Color?> colors = [Colors.amber[100], Colors.pink[100], Colors.green[100], Colors.blue[100], Colors.purple[100]];


  List<PieChartSectionData> chartSections() {
    double sum = 0;
    values.forEach((element) => sum += element);

    return List.generate(5, (index) {
      return PieChartSectionData(
        color: colors[index],
        value: values[index],
        radius: selectedIndex == index ? 200 : 180,
        titleStyle: const TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
        title: "${((values[index] / sum) * 100).toStringAsFixed(2)}%",
      );
    });
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // backgroundColor: Color.fromARGB(255, 235, 230, 220),
      backgroundColor: const Color.fromARGB(255, 235, 225, 220),
      appBar: AppBar(
        centerTitle: true,
        title: const Text("Charts Showcase"),
        backgroundColor: Colors.brown[200],
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(30),
        ),
        actions: [
          IconButton(
            onPressed: () => Navigator.popUntil(context, ModalRoute.withName("/")),
            icon: const Icon(Icons.home)),
        ],
      ),
      body: Center(
        child: Column(
          children: [
            const Padding(
              padding: EdgeInsets.all(10),
              child: Text(
                "The pie chart below is interactive. If you click on one of the slices"
                    " it will get bigger and 'pop out'. The slice you select also"
                    " enlarges the slice info above it to make it obvious which class you are"
                    " viewing (ex. if you click on the blue slice, the indicator for CS 401"
                    " enlarges). In the app bar, press the back button to go to the previous"
                    " screen or the home button to go to the home screen.",
                style: TextStyle(fontWeight: FontWeight.w600, fontSize: 18),
                textAlign: TextAlign.center,
              ),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                SliceInfo(title: "CS 342", col: colors[0], selected: selectedIndex == 0),
                SliceInfo(title: "CS 378", col: colors[1], selected: selectedIndex == 1),
                SliceInfo(title: "CS 480", col: colors[2], selected: selectedIndex == 2),
              ],
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                SliceInfo(title: "CS 401", col: colors[3], selected: selectedIndex == 3),
                SliceInfo(title: "CS 421", col: colors[4], selected: selectedIndex == 4)
              ],
            ),
            Expanded(
              child: PieChart(
                PieChartData(
                  pieTouchData: PieTouchData(
                    touchCallback: (FlTouchEvent touch, pieTouchResponse) {
                      setState(() {
                        selectedIndex = pieTouchResponse?.touchedSection?.touchedSectionIndex;
                      });
                    }
                  ),
                  sectionsSpace: 5,
                  centerSpaceRadius: 0,
                  // centerSpaceColor: Colors.white,
                  sections: chartSections(),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

}