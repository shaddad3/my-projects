Description of Packages used for Project 4

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
