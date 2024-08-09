
import 'SpeechAndTranslateScreen.dart';
import 'package:flutter/material.dart';
import 'package:translator/translator.dart';


class TranslatorScreen extends StatefulWidget {
  const TranslatorScreen({super.key});

  @override
  Translator createState() => Translator();

}

class Translator extends State<TranslatorScreen> {

  final translator = GoogleTranslator();
  final textController = TextEditingController();
  String translatedText = "Hit the info button on the bottom right for more details!";
  String language = 'es';


  void updateTranslation() async {
    if (textController.text == "") {
      return;
    }
    Translation translated = await translator.translate(textController.text, to: language);
    setState(() {
      translatedText = translated.text;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color.fromARGB(255, 255, 230, 230),
      appBar: AppBar(
        centerTitle: true,
        title: const Text("Translator Showcase"),
        backgroundColor: Colors.red[200],
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(30),
        ),
        actions: [
          PopupMenuButton(
            tooltip: "Language menu selector",
            color: const Color.fromARGB(255, 255, 190, 190),
            icon: const Icon(Icons.menu),
            onSelected: (String selected) {
              setState(() {
                language = selected;
                updateTranslation();
              });
            },
            itemBuilder: (context) => [
              const PopupMenuItem<String>(
                value: 'es',
                child: Text("Spanish"),
              ),
              const PopupMenuItem<String>(
                value: 'ar',
                child: Text("Arabic"),
              ),
              const PopupMenuItem<String>(
                value: 'ja',
                child: Text("Japanese"),
              ),
              const PopupMenuItem<String>(
                value: 'zh-cn',
                child: Text("Chinese"),
              ),
              const PopupMenuItem<String>(
                value: 'it',
                child: Text("Italian"),
              ),
              const PopupMenuItem<String>(
                value: 'en',
                child: Text("English"),
              ),
            ],
          ),
          IconButton(
              onPressed: () => Navigator.push(
                  context, MaterialPageRoute(builder: (context) => const SpeechAndTranslateScreen())
              ),
              icon: const Icon(Icons.navigate_next)),
        ],
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Expanded(
              flex: 1,
              child: Padding(
                padding: const EdgeInsets.all(10),
                child: TextField(
                  controller: textController,
                  expands: true,
                  maxLines: null,
                  style: const TextStyle(
                    fontSize: 20, fontWeight: FontWeight.bold
                  ),
                  decoration: const InputDecoration(
                    hintText: "Enter text you want translated!"
                  ),
                  onChanged: (userText) {
                    updateTranslation();
                  },
                ),
              ),
            ),
            Expanded(
              flex: 2,
              child: Padding (
                padding: const EdgeInsets.all(10),
                child: ListView(
                  children: [
                    Text(
                      translatedText,
                      style: const TextStyle(fontSize: 30, fontWeight: FontWeight.bold),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            duration: Duration(seconds: 100),
            showCloseIcon: true,
            content: Text(
              "This screen of the app replicates a translator! Type a word, sentence, or even a"
                  " whole book where the prompt text 'Enter text you want translated!' is"
                  " to translate everything in that text field into the selected language (the default"
                  " is Spanish). To change which language to translate to, click the menu icon (hamburger) in the app bar"
                  " to choose a different language. Click the back arrow (top left) to go back to the"
                  " home screen, and to see how far I got for a different package that ended up failing,"
                  " click the arrow in the top right of the app bar. (Tip: To close the keyboard pop up after typing,"
                  " hit the downwards arrow in the bottom left of the pop up keyboard.)",
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18),
            ),
          ),
        ),
        tooltip: "Info",
        backgroundColor: Colors.red[300],
        child: const Icon(Icons.info_outline),
      ),
    );
  }
}