
import 'package:flutter/material.dart';

class SliceInfo extends StatelessWidget {
  const SliceInfo({super.key, required this.title, required this.col, required this.selected});

  final String title;
  final Color? col;
  final bool selected;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(7),
      child: Wrap(
        crossAxisAlignment: WrapCrossAlignment.center,
        children: [
          ColoredBox(color: col!, child: SizedBox(height: selected ? 25 : 15, width: selected ? 25 : 15)),
          const SizedBox(width: 5),
          Text(title, style: TextStyle(fontSize: selected ? 30 : 20, fontWeight: FontWeight.w500)),
        ],
      ),
    );
  }

}