
// import 'dart:html';

import 'package:flutter/material.dart';
import 'package:speech_to_text/speech_to_text.dart';
import 'package:speech_to_text/speech_recognition_result.dart';

class SpeechAndTranslateScreen extends StatefulWidget {
  const SpeechAndTranslateScreen({super.key});

  @override
  SpeechAndTranslate createState() => SpeechAndTranslate();

}

class SpeechAndTranslate extends State<SpeechAndTranslateScreen> {

  SpeechToText speechToText = SpeechToText();
  bool isEnabled = false;
  String _speechFromUser = "";
  bool micPressed = false;

  @override
  void initState() {
    super.initState();
    _initSpeech();
  }

  void _initSpeech() async {
    isEnabled = await speechToText.initialize();
    setState(() {});
  }

  void listenToUser() async {
    // await speechToText.listen(onResult: _getSpeech);
    await speechToText.listen(
      onResult: (SpeechRecognitionResult result) {
        setState(() {
          _speechFromUser = result.recognizedWords;
          print(_speechFromUser);
        });
      },
      listenFor: const Duration(seconds: 5)
    );
    // print(_speechFromUser);
    setState(() {});
  }

  void stopListeningToUser() async {
    await speechToText.stop();
    setState(() {});
  }

  void _getSpeech(SpeechRecognitionResult result) {
    setState(() {
      _speechFromUser = result.recognizedWords;
      print(_speechFromUser);
    });
    // _speechFromUser = result.recognizedWords;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text("Speech to Text & Translate Showcase"),
        backgroundColor: Colors.red[100],
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(30),
        ),
        actions: [
          IconButton(
              onPressed: () => Navigator.popUntil(context, ModalRoute.withName("/")),
              icon: const Icon(Icons.home)
          ),
          IconButton(
              onPressed: () => ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(
                  duration: Duration(seconds: 100),
                  showCloseIcon: true,
                  content: Text(
                    "To begin recording, click on the microphone in the lower right. This will begin"
                        " a recording session and will pick up every word you say! The recognized words"
                        " that the mic picks up will be printed on the screen next to the text 'You said:'. "
                        " To end a recording session, hit the microphone again (or if not ended for long"
                        " enough, the session will end on its own). In the end, this did not end up working because"
                        " for some reason nothing was being picked up and it seems like the recording session may "
                        "have never been actually going or something. I couldn't figure out why it was not picking"
                        " anything up or why it was not actually recording, so I gave up and switched to another idea."
                        " To navigate back to the translator screen, click the back arrow (top left), and to go to the home page, click"
                        " the home button (top right).",
                    style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18),
                  ),
                ),
              ),
              icon: const Icon(Icons.info_outline),
          ),
        ],
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Expanded(
              child: Padding(
                padding: const EdgeInsets.all(10),
                child: Text(
                  speechToText.isListening ? "You said: '$_speechFromUser'"
                  // _speechFromUser != null ? "You said: $_speechFromUser"
                      : isEnabled ? "Tap the info icon at the top right to learn "
                      "what is going on and what to do, and then hit the mic at the bottom right to begin!"
                      : "Speech is not currently available, please enable it if possible!",
                  style: const TextStyle(fontWeight: FontWeight.w600, fontSize: 20),
                  textAlign: TextAlign.center,
                ),
              ),
            ),
            Expanded(
              child: Padding(
                padding: EdgeInsets.all(10),
                child: Text(
                    ""
                ),
              ),
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => setState(() {
          speechToText.isListening ? stopListeningToUser() : listenToUser();
          // _speechFromUser = null;
          // speechToText.isNotListening ? listenToUser() : stopListeningToUser();
        }),
        tooltip: "Microphone",
        backgroundColor: Colors.red[200],
        child: speechToText.isNotListening ? const Icon(Icons.mic_off) : const Icon(Icons.mic),
      ),
    );
  }
}