import java.util.ArrayList;

public class BaccaratGameLogic {
    // hand1 = player     hand2=banker
    public String whoWon(ArrayList<Card> hand1,ArrayList<Card> hand2) {
        // player
        int playerTotal = handTotal(hand1);
        int bankerTotal = handTotal(hand2);
        String playerWon = "Player"; //changed this
        String bankerWon = "Banker"; //changed this
        String draw = "Draw";


        if (playerTotal == bankerTotal) {
            return draw;
        }
        else if (playerTotal == 8 || playerTotal == 9) { // natural win for player
            if (playerTotal < bankerTotal) { //if player has an 8 but banker has a 9
                return bankerWon;
            }
            return playerWon;
        }
        else if (bankerTotal == 8 || bankerTotal == 9) { // natural win for banker
            return bankerWon;
        }
        else if (playerTotal < bankerTotal) { // Whoever has higher score wins
            return bankerWon;
        }
        else {
            return playerWon;
        }
    }

    public int handTotal(ArrayList<Card> hand) {
        int total = 0;
        // 10s and face cards have a value of 0
        for (Card card: hand) {
            if (card.value < 10) {
                total += card.value; // adds aces and number values less than 10
            }
        }

        // checks if total value of the two cards is greater than 9, if so remove the
        // first number of the total.
        if (total > 9) {
            total = total % 10; // ex. 15 % 10 = 5
        }
        return total;
    }

    public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {

        int bankerTotal = handTotal(hand); // calculate total of banker's hand

        // if banker hand is 7 or greater banker doesn't draw a card
        if (bankerTotal >= 7) {
            return false;
        } else {  // banker drawing depends on playerCard
            ArrayList<Integer> playerValues4 = new ArrayList<Integer>(); //case 4: values where banker won't draw
            playerValues4.add(0);
            playerValues4.add(1);
            playerValues4.add(8);
            playerValues4.add(9);

            ArrayList<Integer> playerValues5 = new ArrayList<Integer>(); // case 5: values where banker won't draw
            playerValues5.add(0);
            playerValues5.add(1);
            playerValues5.add(2);
            playerValues5.add(3);
            playerValues5.add(8);
            playerValues5.add(9);

            ArrayList<Integer> playerValues6 = new ArrayList<Integer>(); // case 6: values where banker won't draw
            playerValues6.add(0);
            playerValues6.add(1);
            playerValues6.add(2);
            playerValues6.add(3);
            playerValues6.add(4);
            playerValues6.add(5);
            playerValues6.add(8);
            playerValues6.add(9);

            switch (bankerTotal) {
                case 0:
                case 1:
                case 2:
                    return true; // banker draws if total is less than 2
                case 3: // if player doesn't draw third card then null pointer exception case
                    if (playerCard == null || playerCard.value != 8) { // banker draws if total is 3 and playerCard is not 8
                        return true;
                    }
                case 4: // null checks if player didn't draw a card (null pointer exception)
                    if (playerCard == null || !playerValues4.contains(playerCard.value)) { // if playerCard's value is not null,0,1,8,9 then banker draws a card
                        return true;
                    }
                case 5:
                    if (playerCard == null || !playerValues5.contains(playerCard.value)) { // if playerCard's value is not null,0,1,2,3,8,9 then banker draws card
                        return true;
                    }
                case 6:
                    if (playerCard != null && !playerValues6.contains(playerCard.value)) { // if playerCard's value is not null,0,1,2,3,4,5,8,9 then banker draws
                        return true; // value is 6 or 7
                    }
            }
        }
        return false;
    }

    public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
        int playerTotal = handTotal(hand);

        return playerTotal <= 5; // if true draw card else false so player doesn't draw card
    }
}
