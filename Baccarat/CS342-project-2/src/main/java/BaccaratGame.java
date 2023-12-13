/*
* CS 342: Project 2
* Authors: Samuel Haddad, Lorena Castillejo
* Project Description: In this project we create
* an app to simulate the Casino game Baccarat. In
* the project a GUI is created to display to the user
* the game, as well as allow them to play it. In the
* project we created 4 classes, BaccaratGame, which runs
* the whole project and brings all the other classes together
* to interact with each other to create the game and game flow.
* We also create Card, which is used to represent Cards in the game,
* as well as BaccaratDealer, which represents the Dealer that would
* be present in the Casino. Finally, we create BaccaratGameLogic, which
* is used to implement some of the logic used in the game.
*/

import javafx.animation.*;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class BaccaratGame extends Application {

	//Objects needed for the game
	ArrayList<Card> playerHand = new ArrayList<>();
	ArrayList<Card> bankerHand = new ArrayList<>();
	BaccaratDealer theDealer = new BaccaratDealer();
	BaccaratGameLogic gameLogic = new BaccaratGameLogic();
	double currentBet = 0;
	double displayBet = 0; //used for the GUI
	String betOn = "";
	double roundWinnings = 0;
	double totalWinnings = 0;
	double displayWinnings = 0; //used for the GUI

	//Objects needed for the GUI
	HashMap<String, Scene> sceneMap = new HashMap<>();
	Button play = new Button("PLAY");
	HBox hands = new HBox();
	Label playerLabel = new Label("Player Hand: ");
	HBox playerCards = new HBox();
	Label bankerLabel = new Label("Banker Hand: ");
	HBox bankerCards = new HBox();
	VBox deckBox = new VBox();
	VBox center = new VBox();
	Text playerScore = new Text("Player Score: ");
	Text bankerScore = new Text("Banker Score: ");
	HBox topBox = new HBox();
	TextArea winnings = new TextArea();
	TextField bet = new TextField();
	Button playerBet = new Button("Player");
	Button dealerBet = new Button("Banker");
	Button drawBet = new Button("Draw");
	Button confirmBet = new Button("Confirm Bet");
	TextArea narrator = new TextArea("Game Log: ");
	HBox betArea = new HBox();;
	VBox betting = new VBox();;
	HBox bottom = new HBox();;
	Menu optionsMenu = new Menu("Options");
	MenuItem freshStart = new MenuItem("Fresh Start");
	MenuItem exit = new MenuItem("Exit");
	PauseTransition naturalWin = new PauseTransition();
	PauseTransition naturalLoss = new PauseTransition();
	PauseTransition win1 = new PauseTransition();
	PauseTransition win2 = new PauseTransition();
	PauseTransition loss1 = new PauseTransition();
	PauseTransition loss2 = new PauseTransition();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to Baccarat!");

		//set the size for the two HBox's that display the Cards
		playerCards.setPrefSize(300, 300);
		bankerCards.setPrefSize(300, 300);

		//put each hand in a VBox with its respective Label
		VBox allPlayer = new VBox(playerLabel, playerCards);
		VBox allBanker = new VBox(bankerLabel, bankerCards);

		//put both hands in an HBox to have them displayed side by side
		hands.getChildren().addAll(allPlayer, allBanker);

		//create an Image to represent the Dealer and set its size
		Image dealerPic = new Image("dealer.png");
		ImageView dealer = new ImageView(dealerPic);
		dealer.setFitHeight(200);
		dealer.setFitWidth(200);

		//create an Image to represent the Deck and set its size
		Image deckPic = new Image("deck.png");
		ImageView deckOfCards = new ImageView(deckPic);
		deckOfCards.setFitHeight(120);
		deckOfCards.setFitWidth(120);

		//put the Dealer and Deck images in a VBox to display them together
		deckBox.getChildren().addAll(dealer, deckOfCards);

		//put the VBox with the Dealer and Deck and HBox of the two hands in a VBox to display them together
		center = new VBox(deckBox, hands);

		//create the menu bar that is used throughout the program
		optionsMenu.getItems().addAll(freshStart, exit);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(optionsMenu);

		//when Exit is clicked in the Options menu, exit the program entirely
		exit.setOnAction(e -> {
			Platform.exit();
		});

		//when Fresh Start is clicked in the Options menu, reset everything in the
		//game and display the welcome scene again
		freshStart.setOnAction(e -> {
			this.clearGUI();
			totalWinnings = 0;
			displayWinnings = 0;
			winnings.setPromptText("Winnings: " + displayWinnings);
			play.setDisable(false);
			play.setStyle("-fx-background-color: white;");
			sceneMap.put("welcomeScene", createWelcomeScene());
			primaryStage.setScene(sceneMap.get("welcomeScene"));
		});

		//put the Options menu and the Play button in an HBox together and align them properly
		topBox = new HBox(menuBar, play);
		topBox.setAlignment(Pos.TOP_LEFT);
		topBox.setSpacing(267);

		//when the Play button is clicked, clear everything from the previous round and display the bet scene
		play.setOnAction(e -> {
			play.setStyle("-fx-background-color: gold;");
			this.clearGUI();
			play.setDisable(true);
			winnings.setPromptText("Winnings: " + displayWinnings);
			sceneMap.put("betScene", createBetScene());
			primaryStage.setScene(sceneMap.get("betScene"));
		});

		//set the winnings Area to display properly
		winnings.setPromptText("Winnings: " + displayWinnings);
		winnings.setWrapText(true);
		winnings.setPrefSize(100, 100);

		//set the bet Area to display properly
		bet.setPromptText("Bet Amount: ");
		bet.setPrefSize(50, 90);

		//set the playerBet button to the right size
		playerBet.setPrefSize(70, 78);

		//when the playerBet button is clicked, disable it and enable the other buttons to allow the user
		//to change their bet if they would like. Store that they bet on 'Player'
		playerBet.setOnAction(e -> {
			playerBet.setDisable(true);
			playerBet.setStyle("-fx-background-color: #0ddcff"); //change color to signal this is what they bet on
			dealerBet.setDisable(false);
			dealerBet.setStyle("-fx-background-color: white");
			drawBet.setDisable(false);
			drawBet.setStyle("-fx-background-color: white");
			confirmBet.setDisable(false);
			betOn = "Player";
		});

		//set the dealerBet button to the right size
		dealerBet.setPrefSize(70, 78);

		//when the dealerBet button is clicked, disable it and enable the other buttons to allow the user
		//to change their bet if they would like. Store that they bet on 'Banker'
		dealerBet.setOnAction(e -> {
			dealerBet.setDisable(true);
			dealerBet.setStyle("-fx-background-color: #0ddcff");
			playerBet.setDisable(false);
			playerBet.setStyle("-fx-background-color: white");
			drawBet.setDisable(false);
			drawBet.setStyle("-fx-background-color: white");
			confirmBet.setDisable(false);
			betOn = "Banker";
		});

		//set the drawBet button to the right size
		drawBet.setPrefSize(70, 78);

		//when the drawBet button is clicked, disable it and enable the other buttons to allow the user
		//to change their bet if they would like. Store that they bet on 'Draw'
		drawBet.setOnAction(e -> {
			drawBet.setDisable(true);
			drawBet.setStyle("-fx-background-color: #0ddcff");
			playerBet.setDisable(false);
			playerBet.setStyle("-fx-background-color: white");
			dealerBet.setDisable(false);
			dealerBet.setStyle("-fx-background-color: white");
			confirmBet.setDisable(false);
			betOn = "Draw";
		});

		//set the confirmBet button to the right size
		confirmBet.setPrefSize(90, 78);

		//when the confirmBet button is clicked, check to make sure a valid bet was made. If so, store
		//the bet amount in currentBet, round the bet amount in displayBet to make the GUI display it nicely,
		//and display the play scene. If not, print out a message in the narrator to tell them what to do
		confirmBet.setOnAction(e -> {
			String amount = bet.getText();
			boolean isDigit = true;
			try { //do a try catch expression to make sure the inputted bet is a number
				currentBet = Double.parseDouble(amount);
			} catch (NumberFormatException exception) {
				//if it is not a number, ask the user to input a number and set isDigit flag to false
				narrator.setText("Game Log: " + "\nPlease enter a number.");
				isDigit = false;
			}
			if (Objects.equals(amount, "")) {
				narrator.setText("Game Log: " + "\nPlease enter an amount greater than or equal to 0.");
			}
			else if (amount.contains("-")) {
				narrator.setText("Game Log: " + "\nPlease enter an amount greater than or equal to 0.");
			}
			else {
				if (isDigit) {
					currentBet = Double.parseDouble(amount);
					displayBet = BigDecimal.valueOf(currentBet).setScale(2, RoundingMode.DOWN).doubleValue();
					sceneMap.put("playScene", createPlayScene());
					primaryStage.setScene(sceneMap.get("playScene"));
				}
			}
		});

		//set the naturalWin timer to work in relation to the play scene, and if this is called, display the win scene
		naturalWin.setDuration(Duration.seconds(21));
		naturalWin.setOnFinished(e -> {
			freshStart.setDisable(false);
			sceneMap.put("winScene", createWinScene());
			primaryStage.setScene(sceneMap.get("winScene"));
		});

		//set the naturalLoss timer to work in relation to the play scene, and if this is called, display the loss scene
		naturalLoss.setDuration(Duration.seconds(21));
		naturalLoss.setOnFinished(e -> {
			freshStart.setDisable(false);
			sceneMap.put("lossScene", createLossScene());
			primaryStage.setScene(sceneMap.get("lossScene"));
		});

		//set the win1 timer to work in relation to the play scene, and if this is called, display the win scene
		win1.setDuration(Duration.seconds(37));
		win1.setOnFinished(e -> {
			freshStart.setDisable(false);
			sceneMap.put("winScene", createWinScene());
			primaryStage.setScene(sceneMap.get("winScene"));
		});

		//set the loss1 timer to work in relation to the play scene, and if this is called, display the loss scene
		loss1.setDuration(Duration.seconds(37));
		loss1.setOnFinished(e -> {
			freshStart.setDisable(false);
			sceneMap.put("lossScene", createLossScene());
			primaryStage.setScene(sceneMap.get("lossScene"));
		});

		//set the win2 timer to work in relation to the play scene, and if this is called, display the win scene
		win2.setDuration(Duration.seconds(32));
		win2.setOnFinished(e -> {
			freshStart.setDisable(false);
			sceneMap.put("winScene", createWinScene());
			primaryStage.setScene(sceneMap.get("winScene"));
		});

		//set the loss2 timer to work in relation to the play scene, and if this is called, display the loss scene
		loss2.setDuration(Duration.seconds(32));
		loss2.setOnFinished(e -> {
			freshStart.setDisable(false);
			sceneMap.put("lossScene", createLossScene());
			primaryStage.setScene(sceneMap.get("lossScene"));
		});

		//make the narrator wrap its text, and set its size
		narrator.setWrapText(true);
		narrator.setPrefSize(350, 170);

		//put all the bet buttons in an HBox to display them together, and put this HBox and the bet text area
		//in a VBox to wrap them up together. Finally, wrap the winnings, all bet stuff, and the narrator together
		betArea = new HBox(playerBet, dealerBet, drawBet, confirmBet);
		betting = new VBox(bet, betArea);
		bottom = new HBox(winnings, betting, narrator);

		//setting font of play button
		play.setFont(Font.font(17));
		play.setTextFill(Color.BLACK);

		//setting font of playerBet, dealerBet, and drawBet buttons
		playerBet.setFont(new Font(15));
		dealerBet.setFont(new Font(15));
		drawBet.setFont(new Font(15));

		//set font and fix display of the two labels
		playerLabel.setBlendMode(BlendMode.MULTIPLY);
		playerLabel.setFont(Font.font(20));
		bankerLabel.setBlendMode(BlendMode.MULTIPLY);
		bankerLabel.setFont(Font.font(20));

		//set font and fix display of the two scores
		playerScore.setBlendMode(BlendMode.MULTIPLY);
		playerScore.setFont(Font.font(15));
		bankerScore.setBlendMode(BlendMode.MULTIPLY);
		bankerScore.setFont(Font.font(15));

		//set the style for the narrator
		narrator.setStyle("-fx-text-fill: black;");

		//add a glow effect to the play button
		DropShadow glow = new DropShadow();
		glow.setRadius(20);
		glow.setColor(Color.rgb(232,232,80,.72));
		play.setEffect(glow);

		//set the alignment for various different elements in the GUI
		BorderPane.setAlignment(playerScore, Pos.CENTER);
		BorderPane.setAlignment(bankerScore, Pos.CENTER);
		BorderPane.setAlignment(bottom, Pos.CENTER);
		BorderPane.setMargin(bottom, new Insets(20));
		center.setAlignment(Pos.BOTTOM_CENTER);
		deckBox.setAlignment(Pos.TOP_CENTER);
		playerCards.setAlignment(Pos.CENTER_LEFT);
		bankerCards.setAlignment(Pos.CENTER_RIGHT);
		hands.setSpacing(100);
		hands.setAlignment(Pos.BOTTOM_CENTER);

		//put the welcome scene in our map, set the scene to the Stage, and show it
		sceneMap.put("welcomeScene", createWelcomeScene());

		primaryStage.setScene(sceneMap.get("welcomeScene"));
		primaryStage.show();
	}

	//evaluateWinnings() takes no parameters, and has a return type of double. This method figures
	//out who won in the game by using the betOn string and the whoWon method from gameLogic, and
	//then calculates what the proper payout is supposed to be based on the currentBet
	public double evaluateWinnings() {

		double winnings = 0;
		if (Objects.equals(betOn, gameLogic.whoWon(playerHand, bankerHand))) { //user bet on the correct outcome
			if (Objects.equals(betOn, "Player")) { //1:1 payout for Player bet
				winnings = currentBet * 2;
			}
			else if (Objects.equals(betOn, "Banker")) { //1:1 payout with a 5% penalty
				winnings = currentBet + (currentBet * 0.95);
			}
			else { //8:1 payout for a Draw bet
				winnings = currentBet + (currentBet * 8);
			}
		}
		else { //user bet on an incorrect outcome
			winnings = currentBet * (-1);
		}

		return winnings;
	}

	//createWelcomeScene() is the Scene method that creates our welcome screen to be displayed
	public Scene createWelcomeScene() {
		//create a BorderPane as the root of the scene
		BorderPane welcome = new BorderPane();

		//set all the elements of the game other than the play button and Options menu to be
		//disabled, and set the narrator to tell the user what to do, aka hit the play button.
		winnings.setDisable(true);

		bet.setDisable(true);

		playerBet.setDisable(true);

		dealerBet.setDisable(true);

		drawBet.setDisable(true);

		confirmBet.setDisable(true);

		narrator.setDisable(true);
		narrator.setText("Game Log: " + "\nHit the Play Button to begin!");


		//set the BorderPane to contain all the elements of the GUI needed in the correct spots
		welcome.setTop(topBox);
		welcome.setBottom(bottom);
		welcome.setLeft(playerScore);
		welcome.setRight(bankerScore);
		welcome.setCenter(center);
		welcome.setStyle("-fx-background-color: #46794c;");

		//add padding to the BorderPane, and finally return this Scene to display
		welcome.setPadding(new Insets(20));
		return new Scene(welcome, 800, 800);
	}

	//createBetScene() is the Scene method that creates our bet scene to be displayed
	public Scene createBetScene() {
		//create a BorderPane as the root of the scene
		BorderPane betScene = new BorderPane();

		//set winnings to be disabled
		winnings.setDisable(true);

		//enable the bet text to allow the user to enter their bet
		bet.setPromptText("Bet Amount: ");
		bet.setDisable(false);

		//enable all bet buttons other than the confirm button
		playerBet.setDisable(false);
		dealerBet.setDisable(false);
		drawBet.setDisable(false);

		confirmBet.setDisable(true);

		//disable the narrator and have it tell the user what to do, enter a bet and click who to bet on
		narrator.setDisable(true);
		narrator.setText("Game Log: " + "\nPlease enter an amount to bet, then choose to bet on either 'Player'" +
				" 'Banker', or 'Draw'. Once finished, hit 'Confirm Bet' to begin the round!");


		//set the BorderPane to contain all the elements of the GUI needed in the correct spots
		betScene.setTop(topBox);
		betScene.setBottom(bottom);
		betScene.setLeft(playerScore);
		betScene.setRight(bankerScore);
		betScene.setCenter(center);
		betScene.setStyle("-fx-background-color: #46794c;");

		//set padding in the BorderPane and return the Scene to display
		betScene.setPadding(new Insets(20));
		return new Scene(betScene, 800, 800);
	}

	//createPlayScene() is the Scene method that creates our play scene to be displayed
	public Scene createPlayScene() {
		//create a BorderPane as the root of the scene
		BorderPane playScene = new BorderPane();

		winnings.setDisable(true);

		//set the bet text field to display the amount bet, and disable the bet text
		bet.setText("Bet Amount: " + displayBet);
		bet.setDisable(true);

		//disable all the bet related buttons
		playerBet.setDisable(true);
		dealerBet.setDisable(true);
		drawBet.setDisable(true);
		confirmBet.setDisable(true);

		narrator.setDisable(true);
		freshStart.setDisable(true);

		//a PauseTransition to display that the Player's hand is dealt and now we are dealing the Banker's hand
		PauseTransition narratorPause1 = new PauseTransition(Duration.seconds(7));
		narratorPause1.setOnFinished(e -> {

			narrator.setText(narrator.getText() + "Done.\nDealing the Banker's hand ... ");
			ArrayList<Card> tempHand = new ArrayList<>();
			tempHand.add(playerHand.get(0));
			tempHand.add(playerHand.get(1));
			playerScore.setText(playerScore.getText() + gameLogic.handTotal(tempHand)); //update the score
		});

		narratorPause1.play();

		//display that we are dealing the Player's hand
		narrator.setText("Game Log: " + "\nDealing the Player's hand ... ");

		PauseTransition playerPause = new PauseTransition(Duration.seconds(3));

		playerHand = theDealer.dealHand(); //deal 2 cards to the player's hand

		//a PauseTransition to display that the Player's cards as images
		playerPause.setOnFinished(e -> {

			Image c1 = new Image("PNG/" + playerHand.get(0).faceName + ".png");
			ImageView card1 = new ImageView(c1);
			card1.setFitHeight(160);
			card1.setFitWidth(80);

			Image c2 = new Image("PNG/" + playerHand.get(1).faceName + ".png");
			ImageView card2 = new ImageView(c2);
			card2.setFitHeight(160);
			card2.setFitWidth(80);

			playerCards.getChildren().addAll(card1, card2);

			card1.setTranslateX(-100); // starting position
			card2.setTranslateX(100);

			TranslateTransition transition1 = new TranslateTransition(Duration.seconds(2), card1);
			TranslateTransition transition2 = new TranslateTransition(Duration.seconds(2), card2);

			transition1.setToX(0); // target position is player's hand
			transition2.setToX(0);

			transition1.play();
			transition2.play();
		});

		playerPause.play();

		//a PauseTransition to display that the Banker's hand is dealt and now we are evaluating for a natural win
		PauseTransition narratorPause2 = new PauseTransition(Duration.seconds(14));
		narratorPause2.setOnFinished(e -> {

			narrator.setText(narrator.getText() + "Done.\nWas there a Natural Win?  ... ");
			ArrayList<Card> tempHand = new ArrayList<>();
			tempHand.add(bankerHand.get(0));
			tempHand.add(bankerHand.get(1));
			bankerScore.setText(bankerScore.getText() + gameLogic.handTotal(tempHand));
		});

		narratorPause2.play();

		PauseTransition bankerPause = new PauseTransition(Duration.seconds(10));
		bankerHand = theDealer.dealHand(); //deal 2 cards to the Banker's hand

		//a PauseTransition to display the Banker's hand as images
		bankerPause.setOnFinished(e -> {

			Image c1 = new Image("PNG/" + bankerHand.get(0).faceName + ".png");
			ImageView card1 = new ImageView(c1);
			card1.setFitHeight(160);
			card1.setFitWidth(80);

			Image c2 = new Image("PNG/" + bankerHand.get(1).faceName + ".png");
			ImageView card2 = new ImageView(c2);
			card2.setFitHeight(160);
			card2.setFitWidth(80);

			bankerCards.getChildren().addAll(card1, card2);

			card1.setTranslateX(-100); // starting position
			card2.setTranslateX(100);

			TranslateTransition transition1 = new TranslateTransition(Duration.seconds(2), card1);
			TranslateTransition transition2 = new TranslateTransition(Duration.seconds(2), card2);

			transition1.setToX(0); // target position is player's hand
			transition2.setToX(0);

			transition1.play();
			transition2.play();
		});

		bankerPause.play();

		int playerTotal = gameLogic.handTotal(playerHand);
		int bankerTotal = gameLogic.handTotal(bankerHand);

		while (true) {

			if (playerTotal == 8 || playerTotal == 9 || bankerTotal == 8 || bankerTotal == 9) {

				//a PauseTransition to display that there was a natural win, and then we evaluate who won
				PauseTransition narratorPause3 = new PauseTransition(Duration.seconds(17));
				narratorPause3.setOnFinished(e ->
								narrator.setText(narrator.getText() + "Yes.\nDetermining the Winner ... ")
				);

				narratorPause3.play();

				if (Objects.equals(betOn, gameLogic.whoWon(playerHand, bankerHand))) { //the user bet on the winner of the round
					naturalWin.play();
					break;
				} else { //the user did not bet on the winner of the round
					naturalLoss.play();
					break;
				}
			} else {
				//a PauseTransition to display that there was not a natural win, and then we see if the Player needs a 3rd card
				PauseTransition narratorPause4 = new PauseTransition(Duration.seconds(17));
				narratorPause4.setOnFinished(e ->
								narrator.setText(narrator.getText() + "No, continuing ... " + "\nDoes the Player need a 3rd card? ...")
				);

				narratorPause4.play();
			}

			Card playerCard = null;

			if (gameLogic.evaluatePlayerDraw(playerHand)) {
				//a PauseTransition to display that the player does need a 3rd card, and that we are dealing it
				PauseTransition narratorPause5 = new PauseTransition(Duration.seconds(20));
				narratorPause5.setOnFinished(e ->
								narrator.setText(narrator.getText() + "Yes.\nDealing 3rd card to Player ... ")
				);

				narratorPause5.play();

				playerCard = theDealer.drawOne();
				playerHand.add(playerCard);

				//a PauseTransition to display the player's 3rd card
				PauseTransition playerPause2 = new PauseTransition(Duration.seconds(22));
				playerPause2.setOnFinished(e -> {

					Image c3 = new Image("PNG/" + playerHand.get(2).faceName + ".png");
					ImageView card3 = new ImageView(c3);
					card3.setFitHeight(160);
					card3.setFitWidth(80);

					playerCards.getChildren().add(card3);

					card3.setTranslateX(60); // starting position

					TranslateTransition transition1 = new TranslateTransition(Duration.seconds(2), card3);
					transition1.setToX(0); // target position is player's hand
					transition1.play();
				});

				playerPause2.play();

				//a PauseTransition to display that the player's 3rd card is dealt, and now asking does the banker need a 3rd card
				PauseTransition narratorPause6 = new PauseTransition(Duration.seconds(25));
				narratorPause6.setOnFinished(e -> {

					narrator.setText(narrator.getText() + "Done.\nDoes the Banker need a 3rd card? ... ");
					playerScore.setText("Player Score: " + gameLogic.handTotal(playerHand));
				});

				narratorPause6.play();
			} else {
				//a PauseTransition to display that the player does not need a third card, and now we ask does the banker need a 3rd card
				PauseTransition narratorPause7 = new PauseTransition(Duration.seconds(20));
				narratorPause7.setOnFinished(e ->
								narrator.setText(narrator.getText() + "No.\nDoes the Banker need a 3rd card? ... ")
				);

				narratorPause7.play();
			}

			if (gameLogic.evaluateBankerDraw(bankerHand, playerCard)) {
				//a PauseTransition to display that the banker does need a third card, and we are dealing it
				PauseTransition narratorPause8 = new PauseTransition(Duration.seconds(28));
				narratorPause8.setOnFinished(e ->
								narrator.setText(narrator.getText() + "Yes.\nDealing 3rd card to Banker ... ")
				);

				narratorPause8.play();

				bankerHand.add(theDealer.drawOne());

				//a PauseTransition to display the banker's 3rd card in the hand
				PauseTransition dealerPause2 = new PauseTransition(Duration.seconds(30));
				dealerPause2.setOnFinished(e -> {

					Image c3 = new Image("PNG/" + bankerHand.get(2).faceName + ".png");
					ImageView card3 = new ImageView(c3);
					card3.setFitHeight(160);
					card3.setFitWidth(80);

					bankerCards.getChildren().add(card3);

					card3.setTranslateX(100); // starting position

					TranslateTransition transition1 = new TranslateTransition(Duration.seconds(2), card3);
					transition1.setToX(0); // target position is player's hand
					transition1.play();
				});

				dealerPause2.play();

				PauseTransition narratorPause9 = new PauseTransition(Duration.seconds(33));
				//a PauseTransition to display that the banker's 3rd card has been dealt, and now we are determining the winner
				narratorPause9.setOnFinished(e -> {
					narrator.setText(narrator.getText() + "Done.\nDetermining the Winner ... ");
					bankerScore.setText("Banker Score: " + gameLogic.handTotal(bankerHand));
				});

				narratorPause9.play();

				if (Objects.equals(betOn, gameLogic.whoWon(playerHand, bankerHand))) { //the user bet on the winner of the round
					win1.play();
					break;
				} else { //the user did not bet on the winner of the round
					loss1.play();
					break;
				}
			}
			else {
				//a PauseTransition to display that the banker did not need a 3rd card, and now we are determining the winner
				PauseTransition narratorPause10 = new PauseTransition(Duration.seconds(28));
				narratorPause10.setOnFinished(e ->
								narrator.setText(narrator.getText() + "No.\nDetermining the Winner ... ")
				);

				narratorPause10.play();

				if (Objects.equals(betOn, gameLogic.whoWon(playerHand, bankerHand))) { //the user bet on the winner of the round
					win2.play();
					break;
				} else { //the user did not bet on the winner of the round
					loss2.play();
					break;
				}
			}

		}

		//set the BorderPane to contain all the elements of the GUI needed in the correct spots
		playScene.setTop(topBox);
		playScene.setBottom(bottom);
		playScene.setLeft(playerScore);
		playScene.setRight(bankerScore);
		playScene.setCenter(center);
		playScene.setStyle("-fx-background-color: #46794c;");

		//set padding and return the Scene to be displayed
		playScene.setPadding(new Insets(20));
		return new Scene(playScene, 800, 800);
	}

	//createWinScene() is the Scene method that creates our win scene to be displayed
	public Scene createWinScene() {
		//create a BorderPane to be the root of the scene
		BorderPane winScene = new BorderPane();

		//create an image and imageView of money to symbolize winning the round, do this for all the money in the BorderPane
		Image moneyPic4 = new Image("money.png");
		ImageView money4 = new ImageView(moneyPic4);
		money4.setFitHeight(200);
		money4.setFitWidth(200);

		//make the imageView rotate to add cool effects, do this for all the money in the BorderPane
		RotateTransition rotate4 = new RotateTransition(Duration.seconds(5),money4);
		rotate4.setCycleCount(RotateTransition.INDEFINITE);
		rotate4.setByAngle(360);
		rotate4.play();

		Image moneyPic5 = new Image("money.png");
		ImageView money5 = new ImageView(moneyPic5);
		money5.setFitHeight(200);
		money5.setFitWidth(200);
		RotateTransition rotate5 = new RotateTransition(Duration.seconds(5),money5);
		rotate5.setCycleCount(RotateTransition.INDEFINITE);
		rotate5.setByAngle(360);
		rotate5.play();

		Image moneyPic6 = new Image("money.png");
		ImageView money6 = new ImageView(moneyPic6);
		money6.setFitHeight(200);
		money6.setFitWidth(200);
		RotateTransition rotate6 = new RotateTransition(Duration.seconds(5),money6);
		rotate6.setCycleCount(RotateTransition.INDEFINITE);
		rotate6.setByAngle(360);
		rotate6.play();

		//add the money pictures to an HBox to group them together
		HBox moneyBottom = new HBox(money4, money5, money6);

		Image moneyPic7 = new Image("money.png");
		ImageView money7 = new ImageView(moneyPic7);
		money7.setFitHeight(200);
		money7.setFitWidth(200);
		RotateTransition rotate7 = new RotateTransition(Duration.seconds(5),money7);
		rotate7.setCycleCount(RotateTransition.INDEFINITE);
		rotate7.setByAngle(360);
		rotate7.play();

		//add the money picture to an VBox to group it
		VBox moneyLeft = new VBox(money7);

		Image moneyPic9 = new Image("money.png");
		ImageView money9 = new ImageView(moneyPic9);
		money9.setFitHeight(200);
		money9.setFitWidth(200);
		RotateTransition rotate9 = new RotateTransition(Duration.seconds(5),money9);
		rotate9.setCycleCount(RotateTransition.INDEFINITE);
		rotate9.setByAngle(360);
		rotate9.play();

		//add the money picture to an VBox to group it
		VBox moneyRight = new VBox(money9);


		//put the Dealer in the scene like the other scenes
		Image dealerPic = new Image("dealer.png");
		ImageView dealer = new ImageView(dealerPic);
		dealer.setFitHeight(200);
		dealer.setFitWidth(200);

		//put the Deck in the scene like the other scenes
		Image deckPic = new Image("deck.png");
		ImageView deckOfCards = new ImageView(deckPic);
		deckOfCards.setFitHeight(100);
		deckOfCards.setFitWidth(100);

		//create a text area to display the results of the round
		TextArea centerText = new TextArea("Results of the Round: \n");
		centerText.setWrapText(true);
		centerText.setDisable(true);
		centerText.setPrefSize(300, 200);
		DropShadow glow = new DropShadow();
		glow.setRadius(100);
		glow.setColor(Color.rgb(232,232,80,.85));
		centerText.setEffect(glow); //add a glow effect to the results text

		centerText.setBackground(Background.fill(Color.GOLD));
		centerText.setStyle("-fx-text-fill: black");

		roundWinnings = evaluateWinnings(); //get the amount won/lost from evaluateWinnings()

		totalWinnings += roundWinnings; //sum the totalWinnings with the new roundWinnings

		roundWinnings = BigDecimal.valueOf(roundWinnings).setScale(2, RoundingMode.DOWN).doubleValue();
		displayWinnings = BigDecimal.valueOf(totalWinnings).setScale(2, RoundingMode.DOWN).doubleValue();

		centerText.setText(centerText.getText() + playerScore.getText() + ", " + bankerScore.getText());
		centerText.setText(centerText.getText() + "\nWinner: " + gameLogic.whoWon(playerHand, bankerHand));

		if (roundWinnings < 0) { //lost if roundWinnings is negative
			centerText.setText(centerText.getText() + "\nYou bet on " + betOn + ". Sorry, you lost!");
		} else { //won as they made a correct bet
			centerText.setText(centerText.getText() + "\nYou bet on " + betOn +". Congratulations, you won!");
		}

		//display the results of the round, as well as text about how to proceed through the game
		centerText.setText(centerText.getText() + "\nBet Amount: " + displayBet);
		centerText.setText(centerText.getText() + "\nRound Winnings: " + roundWinnings);
		centerText.setText(centerText.getText() + "\nTotal Winnings: " + displayWinnings);
		centerText.setText(centerText.getText() + "\nTo continue with these winnings and play another round, hit the Play button!");
		centerText.setText(centerText.getText() + "\nTo reset your winnings, go to Options and then hit Fresh Start!");

		VBox centerBox = new VBox(dealer, deckOfCards, centerText);

		play.setDisable(false);
		play.setStyle("-fx-background-color: white");

		//set the BorderPane to contain all the elements of the GUI needed in the correct spots
		winScene.setTop(topBox);
		winScene.setBottom(moneyBottom);
		moneyBottom.setAlignment(Pos.CENTER);
		winScene.setLeft(moneyLeft);
		moneyLeft.setAlignment(Pos.CENTER);
		winScene.setRight(moneyRight);
		moneyRight.setAlignment(Pos.CENTER);
		winScene.setCenter(centerBox);
		centerBox.setAlignment(Pos.CENTER);

		winScene.setPadding(new Insets(20));
		winScene.setStyle("-fx-background-color: #46794c;");

		return new Scene(winScene, 800, 800);
	}

	public Scene createLossScene() {
		BorderPane lossScene = new BorderPane();

		//create an image and imageView of losing money to symbolize losing the round, do this for all the loss pictures in the BorderPane
		Image lossPic4 = new Image("losingMoney.png");
		ImageView loss4 = new ImageView(lossPic4);
		loss4.setFitHeight(200);
		loss4.setFitWidth(200);

		//make the imageView rotate to add cool effects, do this for all the loss pictures in the BorderPane
		RotateTransition rotateLoss4 = new RotateTransition(Duration.seconds(5),loss4);
		rotateLoss4.setCycleCount(RotateTransition.INDEFINITE);
		rotateLoss4.setByAngle(360);
		rotateLoss4.play();

		Image lossPic5 = new Image("losingMoney.png");
		ImageView loss5 = new ImageView(lossPic5);
		loss5.setFitHeight(200);
		loss5.setFitWidth(200);
		RotateTransition rotateLoss5 = new RotateTransition(Duration.seconds(5),loss5);
		rotateLoss5.setCycleCount(RotateTransition.INDEFINITE);
		rotateLoss5.setByAngle(360);
		rotateLoss5.play();

		Image lossPic6 = new Image("losingMoney.png");
		ImageView loss6 = new ImageView(lossPic6);
		loss6.setFitHeight(200);
		loss6.setFitWidth(200);
		RotateTransition rotateLoss6 = new RotateTransition(Duration.seconds(5),loss6);
		rotateLoss6.setCycleCount(RotateTransition.INDEFINITE);
		rotateLoss6.setByAngle(360);
		rotateLoss6.play();

		//add the loss pictures to an HBox to group them together
		HBox lossBottom = new HBox(loss4, loss5, loss6);

		Image lossPic7 = new Image("losingMoney.png");
		ImageView loss7 = new ImageView(lossPic7);
		loss7.setFitHeight(200);
		loss7.setFitWidth(200);
		RotateTransition rotateLoss7 = new RotateTransition(Duration.seconds(5),loss7);
		rotateLoss7.setCycleCount(RotateTransition.INDEFINITE);
		rotateLoss7.setByAngle(360);
		rotateLoss7.play();

		//add the loss picture to an VBox to group it
		VBox lossLeft = new VBox(loss7);

		Image lossPic9 = new Image("losingMoney.png");
		ImageView loss9 = new ImageView(lossPic9);
		loss9.setFitHeight(200);
		loss9.setFitWidth(200);
		RotateTransition rotateLoss9 = new RotateTransition(Duration.seconds(5),loss9);
		rotateLoss9.setCycleCount(RotateTransition.INDEFINITE);
		rotateLoss9.setByAngle(360);
		rotateLoss9.play();

		//add the loss picture to an VBox to group it
		VBox lossRight = new VBox(loss9);

		//put the Dealer in the scene like the other scenes
		Image dealerPic = new Image("dealer.png");
		ImageView dealer = new ImageView(dealerPic);
		dealer.setFitHeight(200);
		dealer.setFitWidth(200);

		//put the Deck in the scene like the other scenes
		Image deckPic = new Image("deck.png");
		ImageView deckOfCards = new ImageView(deckPic);
		deckOfCards.setFitHeight(100);
		deckOfCards.setFitWidth(100);

		//create a text area to display the results of the round
		TextArea centerText = new TextArea("Results of the Round: \n");
		centerText.setWrapText(true);
		centerText.setDisable(true);
		centerText.setPrefSize(300, 200);
		DropShadow glow = new DropShadow();
		glow.setRadius(100);
		glow.setColor(Color.rgb(232,80,80,.85));
		centerText.setEffect(glow);

		roundWinnings = evaluateWinnings(); //get the amount won/lost from evaluateWinnings()

		totalWinnings += roundWinnings; //sum the totalWinnings with the new roundWinnings

		roundWinnings = BigDecimal.valueOf(roundWinnings).setScale(2, RoundingMode.DOWN).doubleValue();
		displayWinnings = BigDecimal.valueOf(totalWinnings).setScale(2, RoundingMode.DOWN).doubleValue();

		centerText.setText(centerText.getText() + playerScore.getText() + ", " + bankerScore.getText());
		centerText.setText(centerText.getText() + "\nWinner: " + gameLogic.whoWon(playerHand, bankerHand));

		if (roundWinnings < 0) { //lost if roundWinnings is negative
			centerText.setText(centerText.getText() + "\nYou bet on " + betOn + ". Sorry, you lost!");
		} else { //won as they made a correct bet
			centerText.setText(centerText.getText() + "\nYou bet on " + betOn +". Congratulations, you won!");
		}

		//display the results of the round, as well as text about how to proceed through the game
		centerText.setText(centerText.getText() + "\nBet Amount: " + displayBet);
		centerText.setText(centerText.getText() + "\nRound Winnings: " + roundWinnings);
		centerText.setText(centerText.getText() + "\nTotal Winnings: " + displayWinnings);
		centerText.setText(centerText.getText() + "\nTo continue with these winnings and play another round, hit the Play button!");
		centerText.setText(centerText.getText() + "\nTo reset your winnings, go to Options and then hit Fresh Start!");

		VBox centerBox = new VBox(dealer, deckOfCards, centerText);

		play.setDisable(false);
		play.setStyle("-fx-background-color: white");

		centerText.setBackground(Background.fill(Color.RED));
		centerText.setStyle("-fx-text-fill: black");

		//set the BorderPane to contain all the elements of the GUI needed in the correct spots
		lossScene.setTop(topBox);
		lossScene.setBottom(lossBottom);
		lossBottom.setAlignment(Pos.CENTER);
		lossScene.setLeft(lossLeft);
		lossLeft.setAlignment(Pos.CENTER);
		lossScene.setRight(lossRight);
		lossRight.setAlignment(Pos.CENTER);
		lossScene.setCenter(centerBox);
		centerBox.setAlignment(Pos.CENTER);

		lossScene.setPadding(new Insets(20));
		lossScene.setStyle("-fx-background-color: #46794c;");

		return new Scene(lossScene, 800, 800);
	}

	//clearGUI() is the function used to clear / reset all the GUI elements created/updated
	//while playing through the game. This is used by the Play button and Fresh Start menu
	//option to reset the GUI back to its default settings.
	public void clearGUI() {
		//reset the two score texts back to their default
		playerScore.setText("Player Score: ");
		bankerScore.setText("Banker Score: ");

		//reset the two bet's, as well as the bet area and betOn string
		currentBet = 0;
		displayBet = 0;
		betOn = "";
		bet.clear();

		//reset all the buttons to their original style's
		playerBet.setStyle("-fx-background-color: white");
		dealerBet.setStyle("-fx-background-color: white");
		drawBet.setStyle("-fx-background-color: white");

		confirmBet.setDisable(false);

		//reset the the hand ArrayList's to be empty
		playerHand.clear();
		bankerHand.clear();

		//remove all the Card images
		playerCards.getChildren().removeAll(playerCards.getChildren());
		bankerCards.getChildren().removeAll(bankerCards.getChildren());
	}
}
