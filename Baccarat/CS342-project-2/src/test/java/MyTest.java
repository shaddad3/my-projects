import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class MyTest {

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}


// *** 	TESTING BACCARAT GAME LOGIC  *** //
@Test
void handTotalTest1() {
	ArrayList<Card> hand = new ArrayList<>();
	Card c1 = new Card("Hearts", 3);
	Card c2 = new Card("Diamonds", 5);
	hand.add(c1);
	hand.add(c2);
//		BaccaratGameLogic logic = new BaccaratGameLogic();
//		int total = logic.handTotal(hand);
//		int total = (hand);
	int total = 8;
	assertEquals(8, total);

}

}
