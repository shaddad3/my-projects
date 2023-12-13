import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MyController implements Initializable {

    /* Elements from Welcome Screen */

    @FXML
    private VBox root;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField portNumber;

    @FXML
    private Button connect;

    @FXML
    private TextArea textArea;
    @FXML
    private ImageView welcomePic;

    /* Elements from Category Screen */

    @FXML
    private VBox root2;

    @FXML
    private TextField textField;

    @FXML
    private Button category1;

    @FXML
    private Button category2;

    @FXML
    private Button category3;

    @FXML
    private Button confirm;
    @FXML
    private ImageView categoryPic;

    /* Elements from Play Screen */

    @FXML
    private BorderPane root3;

    @FXML
    private Menu menu;
    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem freshStart;

    @FXML
    private MenuItem exit;

    @FXML
    private Text title;

    @FXML
    private TextField titleWelcome;

    @FXML
    private TextArea wordSizeBox;

    @FXML
    private TextArea guessFeedback;

    @FXML
    private TextArea guessesRemaining;

    @FXML
    private TextArea wordSoFar;

    @FXML
    private TextField charBox;

    @FXML
    private Button send;

    @FXML
    private TextArea wordBank;

    @FXML
    private TextArea narrator;
    //   ** * ** * ** * ** ** ** ** *** * * * * ** * ** * ** * * * *
    // elements for win screen
    @FXML
    private VBox root4;
    @FXML
    private TextArea winText;
    @FXML
    private ImageView winnerPic;

    //   ** * ** * ** * ** ** ** ** *** * * * * ** * ** * ** * * * *
    // elements for lose screen
    @FXML
    private VBox root5;
    @FXML
    private TextArea loseText;
    @FXML
    private ImageView lossPic;

    GameData gameData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //exitMethod() closes the Client connection and exits the program.
    public void exitMethod(ActionEvent e) throws IOException {
        Platform.exit();
    }

    //freshStartMethod() brings the Client back to the welcome screen, which is the
    //beginning of the game. It can be invoked in any screen that has the menu bar.
    public void freshStartMethod(ActionEvent e) throws IOException {
        try {
            // Read file fxml and draw interface.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("welcomeScreen.fxml"));
            Parent newRoot = loader.load();
            newRoot.getStylesheets().add(getClass().getResource("welcomeStyle.css").toExternalForm());

            //figure out which root is currently being used, and set it to be the welcome Screen
            if (root != null) {
                root.getScene().setRoot(newRoot);
            } else if (root2 != null) {
                root2.getScene().setRoot(newRoot);
            } else if (root3 != null) {
                root3.getScene().setRoot(newRoot);
            } else if (root4 != null) {
                root4.getScene().setRoot(newRoot);
            } else {
                root5.getScene().setRoot(newRoot);
            }


        } catch(Exception exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    //connectToServer() creates a new Client, and starts this Client's thread using start().
    public void connectToServer(int port) {
        ClientSession.client = new Client(port, data -> {
            Platform.runLater(() -> {
                gameData = (GameData) data;
                category1.setText(category1.getText() + gameData.category1);
                category2.setText(category2.getText() + gameData.category2);
                category3.setText(category3.getText() + gameData.category3);
            });
        });

        ClientSession.client.start();
    }

    //connectMethod() takes the port number entered, and tries to connect to the Server. If successful,
    //it changes the screen to the next one, and if not, tells the Client to enter a correct port number.
    public void connectMethod(ActionEvent e) throws IOException {
        try {
            String s = portNumber.getText();
            int port = Integer.parseInt(s);

            connect.setDisable(true);
            portNumber.setDisable(true);

            //set the screen to the next screen, the category selection screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("categoryScreen.fxml"));
            Parent newRoot = loader.load();
            newRoot.getStylesheets().add(getClass().getResource("categoryStyle.css").toExternalForm());
            MyController controller = loader.getController();
            controller.connectToServer(port);

            root.getScene().setRoot(newRoot);
        } catch (Exception exception) {
            errorLabel.setText("Please enter a valid port number");
            portNumber.clear();
        }

    }

    //category1Method() logs that the Client selected Category 1 as their option to play in,
    //disables it, and enables all other available categories in case the user wants to switch.
    public void category1Method(ActionEvent e) throws IOException {
        ClientSession.client.gameData.categoryPicked = ClientSession.client.gameData.category1;

        if (gameData.categoryThreeDone) {
            category3.setStyle("-fx-background-color: rgba(197, 107, 255, 0.68)");
        } else {
            category3.setStyle("-fx-background-color: rgba(197, 107, 255, 0.68)");
            category3.setDisable(false);
        }

        if (gameData.categoryTwoDone) {
            category2.setStyle("-fx-background-color: rgba(255, 111, 56, 0.91)");
        } else {
            category2.setStyle("-fx-background-color: rgba(255, 111, 56, 0.91)");
            category2.setDisable(false);
        }

        if (gameData.categoryOneDone) {
            category1.setStyle("-fx-background-color: rgba(60, 98, 248, 0.65)");
        } else {
            category1.setStyle("-fx-background-color: green");
            category1.setDisable(true);
        }

        confirm.setDisable(false);
    }

    //category2Method() logs that the Client selected Category 2 as their option to play in,
    //disables it, and enables all other available categories in case the user wants to switch.
    public void category2Method(ActionEvent e) throws IOException {
        ClientSession.client.gameData.categoryPicked = ClientSession.client.gameData.category2;

        if (gameData.categoryThreeDone) {
            category3.setStyle("-fx-background-color: rgba(197, 107, 255, 0.68)");
        } else {
            category3.setStyle("-fx-background-color: rgba(197, 107, 255, 0.68)");
            category3.setDisable(false);
        }

        if (gameData.categoryTwoDone) {
            category2.setStyle("-fx-background-color: rgba(255, 111, 56, 0.91)");
        } else {
            category2.setStyle("-fx-background-color: green");
            category2.setDisable(true);
        }

        if (gameData.categoryOneDone) {
            category1.setStyle("-fx-background-color: rgba(60, 98, 248, 0.65)");
        } else {
            category1.setStyle("-fx-background-color: rgba(60, 98, 248, 0.65)");
            category1.setDisable(false);
        }

        confirm.setDisable(false);
    }

    //category3Method() logs that the Client selected Category 3 as their option to play in,
    //disables it, and enables all other available categories in case the user wants to switch.
    public void category3Method(ActionEvent e) throws IOException {
        ClientSession.client.gameData.categoryPicked = ClientSession.client.gameData.category3;

        if (gameData.categoryThreeDone) {
            category3.setStyle("-fx-background-color: rgba(197, 107, 255, 0.68)");
        } else {
            category3.setStyle("-fx-background-color: green");
            category3.setDisable(true);
        }

        if (gameData.categoryTwoDone) {
            category2.setStyle("-fx-background-color: rgba(255, 111, 56, 0.91)");
        } else {
            category2.setStyle("-fx-background-color: rgba(255, 111, 56, 0.91)");
            category2.setDisable(false);
        }

        if (gameData.categoryOneDone) {
            category1.setStyle("-fx-background-color: rgba(60, 98, 248, 0.65)");
        } else {
            category1.setStyle("-fx-background-color: rgba(60, 98, 248, 0.65)");
            category1.setDisable(false);
        }

        confirm.setDisable(false);
    }

    //categoryPlayback() is used to switch the Client's callback Consumer object to a new
    //Consumer object that is used for the category select screen.
    public void categoryPlayback() {
        ClientSession.client.setCallback(data -> {
            Platform.runLater(() -> {
                gameData = (GameData) data;

                if (gameData.categoryOneDone) {
                    category1.setDisable(true);
                }
                if (gameData.categoryTwoDone) {
                    category2.setDisable(true);
                }
                if (gameData.categoryThreeDone) {
                    category3.setDisable(true);
                }
                category1.setText(category1.getText() + gameData.category1 + "\n" + "Attempts Left: " + gameData.categoryOneAttempts);
                category2.setText(category2.getText() + gameData.category2 + "\n" + "Attempts Left: " + gameData.categoryTwoAttempts);
                category3.setText(category3.getText() + gameData.category3 + "\n" + "Attempts Left: " + gameData.categoryThreeAttempts);

            });
        });
    }

    //playCallback() is used to switch the Client's callback Consumer object to a new
    //Consumer object that is used for the play screen.
    public void playCallback() {
        ClientSession.client.setCallback(data -> {
            Platform.runLater(() -> {
                gameData = (GameData) data;

                int wordSize = gameData.wordChosen.length();

                title.setText("Category: " + gameData.categoryPicked);
                wordSizeBox.setText("The word has " + wordSize + " letters");
                if (gameData.guessMade) {
                    if (gameData.correctGuess) {
                        guessFeedback.setText("Character " + gameData.currentChar + " was in the word :)");
                    } else {
                        guessFeedback.setText("Character " + gameData.currentChar + " was not in the word :(");
                    }
                } else {
                    guessFeedback.setText("No guess made");
                }
                guessesRemaining.setText(gameData.guessesLeft + " of 6 guesses remaining");
                wordSoFar.setText("Word: " + gameData.wordSoFar);
                wordBank.setText("Characters guessed so far: " + gameData.lettersGuessed);
                narrator.setText(gameData.narrator);

                if ((gameData.roundWon) || (gameData.roundLost)) {
                    send.setDisable(true);
                    if (gameData.gameWon) {
                        PauseTransition win = new PauseTransition(Duration.seconds(3));
                        win.setOnFinished(e -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("winScreen.fxml"));
                                Parent newRoot = loader.load();
                                newRoot.getStylesheets().add(getClass().getResource("winStyle.css").toExternalForm());

                                root3.getScene().setRoot(newRoot);

                            } catch (Exception exception) {

                            }
                            send.setDisable(false);
                        });
                        win.play();
                    } else if (gameData.gameLost) {
                        PauseTransition loss = new PauseTransition(Duration.seconds(3));
                        loss.setOnFinished(e -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("lossScreen.fxml"));
                                Parent newRoot = loader.load();
                                newRoot.getStylesheets().add(getClass().getResource("lossStyle.css").toExternalForm());

                                root3.getScene().setRoot(newRoot);

                            } catch (Exception exception) {

                            }
                            send.setDisable(false);
                        });
                        loss.play();
                    } else {
                        PauseTransition pause = new PauseTransition(Duration.seconds(3));
                        pause.setOnFinished(e -> {
                            try {
                                gameData.roundWon = false;
                                gameData.roundLost = false;
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("categoryScreen.fxml"));
                                Parent newRoot = loader.load();
                                newRoot.getStylesheets().add(getClass().getResource("categoryStyle.css").toExternalForm());
                                MyController controller = loader.getController();

                                controller.categoryPlayback();
                                ClientSession.client.useCallback(gameData);

                                root3.getScene().setRoot(newRoot);

                            } catch (Exception exception) {

                            }
                            send.setDisable(false);
                        });
                        pause.play();
                    }
                }
            });
        });
    }

    //confirmMethod() sends the Server the updated category that has been selected, and
    //changes the screen to the play screen.
    public void confirmMethod(ActionEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("playScreen.fxml"));
        Parent newRoot = loader.load();
        newRoot.getStylesheets().add(getClass().getResource("playStyle.css").toExternalForm());

        MyController controller = loader.getController();
        controller.playCallback();
        gameData.categoryChosen = false;
        ClientSession.client.useCallback(gameData);

        ClientSession.client.send(ClientSession.client.gameData);

        root2.getScene().setRoot(newRoot);

    }

    //sendMethod() sends the Server the new guess made by the Client
    public void sendMethod(ActionEvent e) throws IOException {
        if (charBox.getText().isEmpty()) {
            gameData.currentChar = "";
        } else {
            gameData.currentChar = charBox.getText();
        }
        gameData.guessMade = true;
        charBox.clear();
        charBox.setPromptText("Character: ");

        ClientSession.client.send(ClientSession.client.gameData);
        gameData.guessMade = false;

    }

}

