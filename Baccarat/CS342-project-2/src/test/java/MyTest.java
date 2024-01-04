import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class MyTest {

	@BeforeAll
	static void setup(){ Platform.startup(()->{});}

	@AfterAll
	static void clean(){ Platform.exit();}
	//     																		  //
	// * * * * * * * * 	TESTING BACCARAT GAME LOGIC CLASS METHODS  * * * * * * * //
	// 																			//
	// WhoWon() method testing   ======>
	// Testing whoWon() method with a natural win for the Banker
	@Test
	void whoWonTest1(){
		BaccaratGameLogic logic = new BaccaratGameLogic();
		ArrayList<Card> hand1 = new ArrayList<>();
		Card playerCard1 = new Card("Clubs", 5);
		Card playerCard2 = new Card("Spades", 2);
		hand1.add(playerCard1);
		hand1.add(playerCard2);

		ArrayList<Card> hand2 = new ArrayList<>();
		Card bankerCard1 = new Card("Hearts", 7);
		Card bankerCard2 = new Card("Spades", 1);
		hand2.add(bankerCard1);
		hand2.add(bankerCard2);

		assertEquals("Banker", logic.whoWon(hand1, hand2), "Wrong! Banker should have won.");
	}
	// Testing whoWon() method with a draw
	@Test
	void whoWonTest2(){
		BaccaratGameLogic logic = new BaccaratGameLogic();
		ArrayList<Card> hand1 = new ArrayList<>();
		Card playerCard1 = new Card("Clubs", 3);
		Card playerCard2 = new Card("Spades", 3);
		hand1.add(playerCard1);
		hand1.add(playerCard2);

		ArrayList<Card> hand2 = new ArrayList<>();
		Card bankerCard1 = new Card("Hearts", 1);
		Card bankerCard2 = new Card("Spades", 5);
		hand2.add(bankerCard1);
		hand2.add(bankerCard2);

		assertEquals("Draw", logic.whoWon(hand1, hand2), "Wrong! It should have been a draw.");
	}
	// Testing whoWon() with a win for the Player
	@Test
	void whoWonTest3(){
		BaccaratGameLogic logic = new BaccaratGameLogic();
		ArrayList<Card> hand1 = new ArrayList<>();
		Card playerCard1 = new Card("Diamonds", 3);
		Card playerCard2 = new Card("Spades", 4);
		hand1.add(playerCard1);
		hand1.add(playerCard2);

		ArrayList<Card> hand2 = new ArrayList<>();
		Card bankerCard1 = new Card("Hearts", 2);
		Card bankerCard2 = new Card("Spades", 1);
		hand2.add(bankerCard1);
		hand2.add(bankerCard2);

		assertEquals("Player", logic.whoWon(hand1, hand2), "Wrong! It should have been a draw.");
	}
	// Testing whoWon() method when banker has a higher natural win than the player
	@Test
	void whoWonTest4(){
		BaccaratGameLogic logic = new BaccaratGameLogic();
		ArrayList<Card> hand1 = new ArrayList<>();
		Card playerCard1 = new Card("Clubs", 5);
		Card playerCard2 = new Card("Spades", 3);
		hand1.add(playerCard1);
		hand1.add(playerCard2);

		ArrayList<Card> hand2 = new ArrayList<>();
		Card bankerCard1 = new Card("Hearts", 4);
		Card bankerCard2 = new Card("Spades", 5);
		hand2.add(bankerCard1);
		hand2.add(bankerCard2);

		assertEquals("Banker", logic.whoWon(hand1, hand2), "Wrong! It Banker should have won");
	}
	// handTotal() method testing   ======>
	// Testing handTotal() to ensure face cards are assigned 0
	@Test
	void handTotalTest1() {
		BaccaratGameLogic logic = new BaccaratGameLogic();
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card("Hearts", 12); // should be 0
		Card c2 = new Card("Diamonds", 5);
		hand.add(c1);
		hand.add(c2);
		int total = logic.handTotal(hand);
		assertEquals(5, total);
	}
	// Testing handTotal() to ensure first digit is removed from total greater than 9
	@Test
	void handTotalTest2() {
		BaccaratGameLogic logic = new BaccaratGameLogic();
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card("Clubs", 7);
		Card c2 = new Card("Diamonds", 7);
		hand.add(c1);
		hand.add(c2);
		int total = logic.handTotal(hand); // 14 should be 4
		assertEquals(4, total);
	}
	// evaluateBankerDraw() method testing   ======>
	// Testing evaluateBankerDraw() when Banker must draw a card
	@Test
	void evaluateBankerDrawTest1() {
		BaccaratGameLogic logic = new BaccaratGameLogic();
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card("Clubs", 10);
		Card c2 = new Card("Diamonds", 4);  // Banker total should be 4
		hand.add(c1);
		hand.add(c2);
		Card playerCard = new Card("Hearts", 6); // if playerCard not 0,1,8,9 banker must draw
		boolean result = logic.evaluateBankerDraw(hand, playerCard);
		assertTrue(result);
	}
	// Testing evaluateBankerDraw() when Banker must NOT draw a card
	@Test
	void evaluateBankerDrawTest2() {
		BaccaratGameLogic logic = new BaccaratGameLogic();
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card("Clubs", 11);
		Card c2 = new Card("Diamonds", 5);  // Banker total should be 5
		hand.add(c1);
		hand.add(c2);
		Card playerCard = new Card("Hearts", 3); // if playerCard is null,0,1,2,3,8,9 banker must NOT draw
		boolean result = logic.evaluateBankerDraw(hand, playerCard);
		assertFalse(result);
	}
	// evaluatePlayerDraw() method testing   ======>
	// Testing that evaluatePlayerDraw() must return true when total is <= 5
	@Test
	void evaluatePlayerDrawTest1() {
		BaccaratGameLogic logic = new BaccaratGameLogic();
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card("Clubs", 5);
		Card c2 = new Card("Diamonds", 8);  // Banker total should be 3
		hand.add(c1);
		hand.add(c2);
		boolean result = logic.evaluatePlayerDraw(hand);
		assertTrue(result);
	}
	// Testing that evaluatePlayerDraw() must return false if total is greater than 5
	@Test
	void evaluatePlayerDrawTest2() {
		BaccaratGameLogic logic = new BaccaratGameLogic();
		ArrayList<Card> hand = new ArrayList<>();
		Card c1 = new Card("Clubs", 7);
		Card c2 = new Card("Diamonds", 2);  // Banker total should be 9
		hand.add(c1);
		hand.add(c2);
		boolean result = logic.evaluatePlayerDraw(hand);
		assertFalse(result);
	}
	// 																		  //
	// * * * * * * * * 	TESTING BACCARAT DEALER CLASS METHODS  * * * * * * * //
	// 																		//
	// Testing constructor for class  ======>
	// Testing constructor to ensure deck and size is not null
	@Test
	void dealerConstructorTest1() {
		BaccaratDealer dealerObj = new BaccaratDealer();
		assertNotNull(dealerObj.deck, "Wrong! Deck should not be null");
		assertNotNull(dealerObj.size, "Wrong! Size should not be null");
	}
	// Testing constructor to ensure deck has 52 cards
	@Test
	void dealerConstructorTest2() {
		BaccaratDealer dealerObj = new BaccaratDealer();
		int result = dealerObj.size;
		assertEquals( 52, result, "Wrong! Size should be 52");
	}
	// Testing generateDeck() method  ======>
	// Testing generateDeck() to make sure deck has 52 cards and that they are all unique
	@Test
	void generateDeckTest1(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		assertEquals(52, dealer.size, "Wrong! There should be 52 cards in deck");
		Set<Card> unique = new HashSet<>(dealer.deck);
		assertEquals(52, unique.size(), "Wrong! There should be 52 unique cards");
	}
	// Testing generateDeck() to make sure deck is created with valid suites and values only
	@Test
	void generateDeckTest2(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		for (Card card : dealer.deck) {
			assertTrue(Arrays.asList("Hearts", "Diamonds", "Clubs", "Spades").contains(card.suite), "Incorrect suite");
		}
		for (Card card : dealer.deck) {
			assertTrue(card.value >= 1 && card.value <= 13, "Incorrect value"); // should only be a number from 1-13
		}
	}
	// Testing dealHand() method  ======>
	// Testing that deck returns 2 two cards to hand and the deck size decreases by 2
	@Test
	void dealHandTest1(){
		BaccaratDealer dealer = new BaccaratDealer();
		ArrayList<Card> hand = dealer.dealHand();
		assertEquals(2, hand.size(), "Wrong! Hand should contain 2 cards");
		assertEquals(50, dealer.deckSize(), "Wrong! Dealer deck should decrease by 2");
	}
	// Test to ensure each hand has two cards each time it is dealt and that the size decreases by 2
	@Test
	void dealHandTest2() {
		BaccaratDealer dealer = new BaccaratDealer();
		ArrayList<Card> hand;
		// Dealing hands until the deck needs to be regenerated
		int initialDeckSize = dealer.deckSize();
		int expectedDeckSize = initialDeckSize - 2;
		int dealt = 0;

		while (dealer.deckSize() >= 2) {
			hand = dealer.dealHand();
			dealt++;
			assertEquals(2, hand.size(), "Hand should contain 2 cards.");
			assertEquals(expectedDeckSize, dealer.deckSize(), "Deck size should decrease by 2 after dealing a hand.");
			expectedDeckSize = expectedDeckSize - 2;
		}
	}
	// Testing drawOne() method  ======>
	// Testing drawOne() to ensure card is drawn correctly and is removed from deck
	@Test
	void drawOneTest1() {
		BaccaratDealer dealer = new BaccaratDealer();
		Card card = dealer.drawOne();

		assertNotNull(card, "Wrong! Card should not be null");
		assertEquals(51, dealer.size, "Wrong! Deck size decrease by one");
	}
	// Testing that drawOne decreases in size by 1 each time
	@Test
	void drawOneTest2() {
		BaccaratDealer dealer = new BaccaratDealer();
		Card card;

		// Drawing cards until the deck needs to be regenerated
		int initialDeckSize = dealer.deckSize();
		int expectedDeckSize = initialDeckSize - 1;
		int cardsDrawn = 0;

		while (dealer.deckSize() >= 1) {
			card = dealer.drawOne();
			cardsDrawn++;
			assertNotNull(card, "Wrong! Drawn card should not be null.");
			assertEquals(expectedDeckSize, dealer.deckSize(), "Wrong! Deck size should decrease by 1 after drawing one card.");
			expectedDeckSize = expectedDeckSize - 1;
		}
	}
	// Testing shuffleDeck() method  ======>
	// Test original shuffled deck with newly shuffled deck. They should be different
	@Test
	void shuffleDeckTest1() {
		BaccaratDealer dealer = new BaccaratDealer();
		ArrayList<Card> deck1 = new ArrayList<>(dealer.deck); // Load deck order into an arrayList
		dealer.shuffleDeck();
		ArrayList<Card> deck2 = new ArrayList<>(dealer.deck); // Load shuffled deck into arrayList
		assertNotEquals(deck1, deck2, "Decks should be shuffled in a different order");
	}
	// Tests that shuffle method shuffles card and that deck isn't empty
	@Test
	void shuffleDeckTest2() {
		BaccaratDealer dealer = new BaccaratDealer();
		ArrayList<Card> deck1 = new ArrayList<>(dealer.deck); // Load deck order into an arrayList
		dealer.shuffleDeck();
		ArrayList<Card> deck2 = new ArrayList<>(dealer.deck); // Load shuffled deck into arrayList
		assertNotEquals(deck1, deck2, "Decks should be shuffled in a different order");
		assertNotEquals(deck1, deck2, "Decks should be shuffled in a different order");
	}
	// Testing deckSize() method  ======>
	// Test to ensure deckSize() method works properly
	@Test
	void deckSizeTest1() {
		BaccaratDealer dealer = new BaccaratDealer();
		assertEquals(52, dealer.deckSize(), "Wrong! Each new deck should have a size of 52 cards");
	}
	// Test to make sure deckSize() is not null when a new deck is made
	@Test
	void deckSizeTest2() {
		BaccaratDealer dealer = new BaccaratDealer();
		assertNotNull(dealer.deckSize(), "Wrong! Each new deck should have a size of 52 cards");
	}
	// 																		  //
	// * * * * * * * * 	TESTING BACCARAT DEALER CLASS METHODS  * * * * * * * //
	// 																		//
	// Testing constructor for class  ======>
	// Testing that suite and value are not null and assigned the correct respective values
	@Test
	void cardConstructorTest1() {
		Card card = new Card("Hearts", 5);
		assertNotNull(card.suite, "Card suite should not be null");
		assertNotNull(card.value, "Card value should not be null");
		assertNotNull(card.faceName, "Card faceName should not be null");
		assertEquals("Hearts", card.suite, "Wrong suite!");
		assertEquals(5, card.value, "Wrong suite!");
		assertEquals(5+"of"+"Hearts", card.faceName, "Wrong faceName!");
	}
	// Testing that suite and value are not null and assigned the correct respective values
	@Test
	void cardConstructorTest2() {
		Card card = new Card("Spades", 8);
		assertNotNull(card.suite, "Card suite should not be null");
		assertNotNull(card.value, "Card value should not be null");
		assertNotNull(card.faceName, "Card faceName should not be null");
		assertEquals("Spades", card.suite, "Wrong suite!");
		assertEquals(8, card.value, "Wrong suite!");
		assertEquals(8+"of"+"Spades", card.faceName, "Wrong faceName!");
	}
	// 																		  //
	// * * * * * * * * 	TESTING BACCARAT GAME CLASS METHOD  * * * * * * * //
	// 																		//
	// Testing evaluateWinnings()
	// Testing if winnings are calculated correctly for player 8:1
	@Test
	void evaluateWinningsTest1() {
		BaccaratGame game = new BaccaratGame();
		game.betOn = "Player";
		game.currentBet = 10;

		game.playerHand.add(new Card("Hearts", 5)); // playerTotal = 8
		game.playerHand.add(new Card("Hearts", 3));
		game.bankerHand.add(new Card("Clubs", 4)); // bankerTotal = 5
		game.bankerHand.add(new Card("Hearts", 1));

		double winnings = game.evaluateWinnings();

		assertEquals(20, winnings, "Wrong! Player won with 8:1 payout");
	}
	// Testing if winnings are calculated correctly for player 1:1 and 5% penalty
	@Test
	void evaluateWinningsTest2() {
		BaccaratGame game = new BaccaratGame();
		game.betOn = "Banker";
		game.currentBet = 5;

		game.playerHand.add(new Card("Hearts", 4)); // playerTotal = 7
		game.playerHand.add(new Card("Hearts", 3));
		game.bankerHand.add(new Card("Clubs", 5)); // bankerTotal = 8
		game.bankerHand.add(new Card("Hearts", 3));

		double winnings = game.evaluateWinnings();

		assertEquals(9.75, winnings, "Wrong! Banker won with 1:1 payout and 5% penalty");
	}

}
