import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Collections;

public class BaccaratDealer {
    ArrayList<Card> deck;
    int size;
    //Button deckButton;

    BaccaratDealer() { //create a dealer, which has a deck and size
        this.generateDeck();
        this.shuffleDeck();
    }

    public void generateDeck() {
        // 4 suits total, 13 cards in each suite = 52 cards
        deck = new ArrayList<>();
        size = 0;

        ArrayList<String> suiteList = new ArrayList<String>(); // holds suites
        suiteList.add("Hearts");
        suiteList.add("Diamonds");
        suiteList.add("Clubs");
        suiteList.add("Spades");


        ArrayList<Integer> valueList = new ArrayList<Integer>(); // holds 13 cards
        for (int i = 1; i <= 13; i++){
            valueList.add(i);
        }

        // creates 13 cards for each suite then adds to deck (52 cards)
        for (String suite: suiteList) {
            for (Integer value: valueList) {
                Card currentCard = new Card(suite, value);
                deck.add(currentCard);
                size++;
            }
        }

        //shuffle the deck
        //this.deckButton = new Button("Deck");
//        this.shuffleDeck();
    }

    public ArrayList<Card> dealHand() {
        ArrayList<Card> hand = new ArrayList<>();
        if (this.size < 2) { //if the deck does not have enough Cards, make a new deck
            this.generateDeck();
            this.shuffleDeck();
        }

        //get 2 cards off the deck, put them in the hand, and remove them from the deck
        hand.add(this.deck.remove(0));
        hand.add(this.deck.remove(0)); //I think we remove index 0 both times

        //remove the 2 Cards from the deck
        deck.remove(0);
        deck.remove(0); //I think we remove index 0 both times
//        hand.add(this.drawOne());
        size = size - 2;

        return hand;
    }

    public Card drawOne() {
        Card drawnCard;
        if (this.size < 1) { //if the deck does not have enough Cards, make a new deck
            this.generateDeck();
            this.shuffleDeck();
        }

        //get a Card off the deck, remove it from the deck, and return the Card
        drawnCard = this.deck.remove(0);
        size--;
        return drawnCard;
    }


    public void shuffleDeck() {
        Collections.shuffle(deck);
    }
}
