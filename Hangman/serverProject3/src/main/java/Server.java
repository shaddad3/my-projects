import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

public class Server {

    TheServer server;
    ArrayList<ClientThread> clientList = new ArrayList<>();
    int clientCount = 0;
    GameData gameData = new GameData();
    ServerLogic serverLogic = new ServerLogic();
    int portNumber;
    private Consumer<Serializable> callback;

    Server(int port, Consumer<Serializable> call) {
        callback = call;
        portNumber = port;
        server = new TheServer();
        server.start();
    }


    public class TheServer extends Thread {

        public void run() {
            try (ServerSocket mySocket = new ServerSocket(portNumber);) {
                //print to the GUI server launched
                callback.accept("Server has began and is waiting for a Client!");

                while (true) {
                    ClientThread client = new ClientThread(mySocket.accept(), clientCount);
                    //print to GUI client # x has connected
                    callback.accept("client #" + clientCount + " has connected to the Server.");
                    clientList.add(client);
                    client.start();

                    clientCount++;
                }

            } catch (Exception exception) {
                //print out to the GUI server did not launch
                callback.accept("Server did not launch.");
                exception.printStackTrace();
                System.exit(1);
            }
        }
    }

    public class ClientThread extends Thread {

        Socket clientSocket;
        ObjectInputStream in;
        ObjectOutputStream out;
        int counter;

        ClientThread(Socket socket, int number) {
            clientSocket = socket;
            counter = number;
        }

        public void updateClient(GameData newGameData) {
            try {
                //send the data back to the Client
                out.writeObject(newGameData);
            } catch (Exception exception) {
                //error sending the data back to the Client
                callback.accept("Server failed to write data to the Client");
                exception.printStackTrace();
                System.exit(1);
            }
        }

        public void run() {
            try {
                in = new ObjectInputStream(clientSocket.getInputStream());
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                clientSocket.setTcpNoDelay(true);
            } catch (Exception exception) {
                //print that an error occurred while opening Client in/out
                callback.accept("Server failed to open input/output streams");
                exception.printStackTrace();
                System.exit(1);
            }

            updateClient(gameData); //send the Client the default Data class, which will
            //tell the Client to begin the game from the start

            //fill the category 1 words
            serverLogic.categoryFirstWords.add("apple");
            serverLogic.categoryFirstWords.add("mango");
            serverLogic.categoryFirstWords.add("banana");
            serverLogic.categoryFirstWords.add("kiwi");
            serverLogic.categoryFirstWords.add("cherry");
            serverLogic.categoryFirstWords.add("pear");
            serverLogic.categoryFirstWords.add("guava");
            serverLogic.categoryFirstWords.add("orange");
            serverLogic.categoryFirstWords.add("peach");
            serverLogic.categoryFirstWords.add("nectarine");
            serverLogic.categoryFirstWords.add("strawberry");

            //fill the category 2 words
            serverLogic.categorySecondWords.add("spider");
            serverLogic.categorySecondWords.add("ant");
            serverLogic.categorySecondWords.add("cicada");
            serverLogic.categorySecondWords.add("beetle");
            serverLogic.categorySecondWords.add("bee");
            serverLogic.categorySecondWords.add("moth");
            serverLogic.categorySecondWords.add("cricket");
            serverLogic.categorySecondWords.add("wasp");
            serverLogic.categorySecondWords.add("flea");
            serverLogic.categorySecondWords.add("butterfly");

            //fill the category 3 words
            serverLogic.categoryThirdWords.add("carrot");
            serverLogic.categoryThirdWords.add("spinach");
            serverLogic.categoryThirdWords.add("onion");
            serverLogic.categoryThirdWords.add("celery");
            serverLogic.categoryThirdWords.add("kale");
            serverLogic.categoryThirdWords.add("beets");
            serverLogic.categoryThirdWords.add("peppers");
            serverLogic.categoryThirdWords.add("tomato");
            serverLogic.categoryThirdWords.add("pumpkin");
            serverLogic.categoryThirdWords.add("broccoli");


            while (true) {
                try {
                    GameData updatedGameData = (GameData) in.readObject();
                    if (!(gameData.categoryChosen)) {
                        updatedGameData.categoryChosen = false;
                        gameData.categoryChosen = true;
                    }

                    if (!(updatedGameData.categoryChosen)) { //Client has not chosen a Category yet
                        updatedGameData.wordChosen = serverLogic.pickWord(updatedGameData.categoryPicked);

                        System.out.println(updatedGameData.categoryPicked);

                        updatedGameData.categoryChosen = true;
                        callback.accept("Client #" + counter + " chosen Category '" + updatedGameData.categoryPicked +"'. Sending back '" + updatedGameData.wordChosen + "'.");
                        for (int i = 0; i < updatedGameData.wordChosen.length(); i++) {
                            updatedGameData.wordSoFar.add(i, "_");
                        }
                        updatedGameData.narrator = "Enter a character to guess in the box above 'Send', and then hit 'Send'!";
                        updateClient(updatedGameData);
                        continue;
                    }

                    if (updatedGameData.guessMade) { //the Client submitted a char to guess

                        if (Objects.equals(updatedGameData.currentChar, "")) { //no character was sent to be guessed
                            updatedGameData.narrator = "Please guess 1 character, not no characters!";
                            callback.accept("Client #" + counter + " did not guess a character and sent nothing.");
                            updatedGameData.correctGuess = false;
                            updatedGameData.guessMade = false;
                            updateClient(updatedGameData);
                            continue;
                        }

                        if (!(Character.isLetter(updatedGameData.currentChar.charAt(0)))) { //guess was not a letter
                            updatedGameData.narrator = "Character '" + updatedGameData.currentChar + "' is not a letter! Please guess a letter.";
                            callback.accept("Client #" + counter + " guessed character '" + updatedGameData.currentChar +"'. This is not a letter.");
                            updatedGameData.correctGuess = false;
                            updatedGameData.guessMade = false;
                            updateClient(updatedGameData);
                            continue;
                        }

                        if (updatedGameData.currentChar.length() != 1) { //guess was not only 1 character
                            updatedGameData.narrator = "Please guess only 1 letter, and no less then 1 letter!";
                            callback.accept("Client #" + counter + " guessed character '" + updatedGameData.currentChar +"'. This is not 1 letter.");
                            updatedGameData.correctGuess = false;
                            updatedGameData.guessMade = false;
                            updateClient(updatedGameData);
                            continue;
                        }

                        updatedGameData.currentChar = updatedGameData.currentChar.toLowerCase();
                        char c = updatedGameData.currentChar.charAt(0);

                        if (serverLogic.duplicateGuess(updatedGameData.currentChar, updatedGameData.lettersGuessed)) { //duplicate guess made
                            updatedGameData.narrator = "Character '" + c + "' has been guessed already! Please guess a different character.";
                            callback.accept("Client #" + counter + " guessed character '" + updatedGameData.currentChar +"'. This character was guessed already.");
                            updatedGameData.correctGuess = false;
                            updatedGameData.guessMade = false;
                            updateClient(updatedGameData);
                            continue;
                        }

                        if (serverLogic.isInWord(updatedGameData.wordChosen, updatedGameData.currentChar)) { //guess was correct
                            for (int i = 0; i < updatedGameData.wordChosen.length(); i++) {
                                if (updatedGameData.wordChosen.charAt(i) == c) { //change each
                                    updatedGameData.wordSoFar.set(i, updatedGameData.currentChar);
                                }
                            }
                            updatedGameData.narrator = "Character '" + c + "' was in the word! Updating word box to include this character";
                            updatedGameData.correctGuess = true;
                            callback.accept("Client #" + counter + " guessed character '" + updatedGameData.currentChar +"'. It was in the word '" + updatedGameData.wordChosen + "'.");
                            updatedGameData.lettersGuessed.add(updatedGameData.currentChar);
                        } else { //guess was incorrect
                            updatedGameData.narrator = "Character '" + c + "' was not in the word, try and guess a different character!";
                            updatedGameData.guessesLeft--;
                            updatedGameData.correctGuess = false;
                            callback.accept("Client #" + counter + " guessed character '" + updatedGameData.currentChar +"'. It was not in the word '" + updatedGameData.wordChosen + "'.");
                            updatedGameData.lettersGuessed.add(updatedGameData.currentChar);
                        }

                        if (serverLogic.completeWord(updatedGameData.wordChosen, updatedGameData.wordSoFar)) { //Client completed the word
                            if (Objects.equals(updatedGameData.categoryPicked, updatedGameData.category1)) { //completed Category 1
                                updatedGameData.categoryOneDone = true;
                            }
                            else if (Objects.equals(updatedGameData.categoryPicked, updatedGameData.category2)) { //completed Category 2
                                updatedGameData.categoryTwoDone = true;
                            }
                            else { //completed Category 3
                                updatedGameData.categoryThreeDone = true;
                            }
                            callback.accept("Client #" + counter + " correctly guessed the word " + updatedGameData.wordChosen + " with " + updatedGameData.guessesLeft + " guesses remaining.");
                            updatedGameData.narrator = "Congratulations! You successfully guessed the word " + updatedGameData.wordChosen + " with " + updatedGameData.guessesLeft + " guesses remaining! ";
                            updatedGameData.narrator = updatedGameData.narrator + "Taking you back to the Category Select Screen ... ";
                            updatedGameData.categoriesWon++;
                            updatedGameData.roundWon = true;

                            updatedGameData.guessMade = false;

                            updatedGameData.guessesLeft = 6;
                            updatedGameData.lettersGuessed.clear();
                            updatedGameData.wordSoFar.clear();
                            updatedGameData.wordChosen = "";
                            updatedGameData.categoryPicked = "";

                            gameData.categoryChosen = false;
                        }

                        if (serverLogic.noMoreGuesses(updatedGameData.guessesLeft)) { //user ran out of guesses
                            if (Objects.equals(updatedGameData.categoryPicked, updatedGameData.category1)) { //lost in Category 1
                                updatedGameData.categoryOneAttempts--;
                            }
                            else if (Objects.equals(updatedGameData.categoryPicked, updatedGameData.category2)) { //lost in Category 2
                                updatedGameData.categoryTwoAttempts--;
                            }
                            else { //lost in Category 3
                                updatedGameData.categoryThreeAttempts--;
                            }
                            callback.accept("Client #" + counter + " did not manage to guess the word " + updatedGameData.wordChosen + ".");
                            updatedGameData.narrator = "Sorry, that was your last guess attempt! Taking you back to the Category Select Screen ... ";
                            updatedGameData.roundLost = true;
                            updatedGameData.categoryChosen = false;

                            updatedGameData.guessesLeft = 6;
                            updatedGameData.lettersGuessed.clear();
                            updatedGameData.wordSoFar.clear();
                            updatedGameData.wordChosen = "";
                            updatedGameData.categoryPicked = "";
                        }

                        if (serverLogic.gameWon(updatedGameData.categoriesWon)) { //Client won all 3 categories, so game is over
                            callback.accept("Client #" + counter + " correctly guessed all 3 categories! Displaying the win screen to them.");
                            updatedGameData.narrator = "Congratulations! You successfully guessed the word " + updatedGameData.wordChosen + " with " + updatedGameData.guessesLeft + " guesses remaining! ";
                            updatedGameData.narrator = updatedGameData.narrator + "You completed all the Categories!!! Taking you to the Win Screen ... ";
                            updatedGameData.gameWon = true;

                            updatedGameData.guessesLeft = 6;
                            updatedGameData.lettersGuessed.clear();
                            updatedGameData.wordSoFar.clear();
                        }

                        if (serverLogic.gameLost(updatedGameData.categoryOneAttempts, updatedGameData.categoryTwoAttempts, updatedGameData.categoryThreeAttempts)) { //Client ran out of attempts in a category
                            callback.accept("Client #" + counter + " used all their attempts in a category. Displaying the losing screen to them.");
                            updatedGameData.narrator = "Sorry, that was your last guess attempt! ";
                            updatedGameData.narrator = updatedGameData.narrator + "That was your final attempt in this Category :( Taking you to the Loss Screen ... ";
                            updatedGameData.gameLost = true;

                            updatedGameData.guessesLeft = 6;
                            updatedGameData.lettersGuessed.clear();
                            updatedGameData.wordSoFar.clear();
                        }

                        updateClient(updatedGameData);
                    }

                } catch (Exception exception) {
                    //print out that a Client connection ended
                    callback.accept("Something went wrong with client #" + counter + ", closing connection.");
                    exception.printStackTrace();
                    clientList.remove(this);
                    break;
                }
            }
        }
    }
}
