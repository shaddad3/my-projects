import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class MyTest {

	// * * * * * * * * * *      TESTING isInWord() FUNCTION      * * * * * * * * * * //
	// Test when letter guessed is in word
	@Test
	void isInWordTest1() {
		ServerLogic logic = new ServerLogic();
		boolean result = logic.isInWord("banana", "a");
		assertTrue(result);
	}

	// Test when letter guessed is not in word
	@Test
	void isInWordTest2() {
		ServerLogic logic = new ServerLogic();
		boolean result = logic.isInWord("orange", "q");
		assertFalse(result);
	}

	// * * * * * * * * * *      TESTING gameWon() FUNCTION      * * * * * * * * * * //
	// Test that function returns false when categories won is not 3
	@Test
	void gameWonTest1(){
		ServerLogic logic = new ServerLogic();
		boolean result = logic.gameWon(1);
		assertFalse(result);
	}
	// Test that function returns true when categories won is 3
	@Test
	void gameWonTest2(){
		ServerLogic logic = new ServerLogic();
		boolean result = logic.gameWon(3);
		assertTrue(result);
	}

	// * * * * * * * * * *      TESTING pickWord() FUNCTION      * * * * * * * * * * //
	// Tests that word selected from the first category.
	@Test
	void pickWordTest1(){
		ServerLogic logic = new ServerLogic();
		logic.categoryFirstWords.add("apple");
		logic.categoryFirstWords.add("mango");
		logic.categoryFirstWords.add("banana");

		logic.categorySecondWords.add("spider");
		logic.categorySecondWords.add("ant");
		logic.categorySecondWords.add("cicada");

		logic.categoryThirdWords.add("carrot");
		logic.categoryThirdWords.add("broccoli");
		logic.categoryThirdWords.add("tomato");

		int initialSize = logic.categoryFirstWords.size();

		String randomFruitWord = logic.pickWord("Fruits"); // picks random word from fruits array
		assertNotNull(randomFruitWord);

		ArrayList<String> tester = new ArrayList<>(); // array to check if selected random word is in list since it is removed when it is assigned in function
		tester.add("apple");
		tester.add("mango");
		tester.add("banana");

		boolean result = tester.contains(randomFruitWord);  // makes sure word is in fruits array
		assertTrue(result);

		assertEquals(initialSize-1, logic.categoryFirstWords.size());// size of categoryFirstWords should be decremented by 1

	}
	// Tests that word selected from the second category.
	@Test
	void pickWordTest2(){
		ServerLogic logic = new ServerLogic();
		logic.categoryFirstWords.add("apple");
		logic.categoryFirstWords.add("mango");
		logic.categoryFirstWords.add("banana");

		logic.categorySecondWords.add("spider");
		logic.categorySecondWords.add("ant");
		logic.categorySecondWords.add("cicada");

		logic.categoryThirdWords.add("carrot");
		logic.categoryThirdWords.add("broccoli");
		logic.categoryThirdWords.add("tomato");

		int initialSize = logic.categorySecondWords.size();

		String randomInsectWord = logic.pickWord("Insects"); // picks random word from insects array
		assertNotNull(randomInsectWord);

		ArrayList<String> tester = new ArrayList<>(); // array to check if selected random word is in list since it is removed when it is assigned in function
		tester.add("spider");
		tester.add("ant");
		tester.add("cicada");

		boolean result = tester.contains(randomInsectWord);  // makes sure word is in insects array
		assertTrue(result);

		assertEquals(initialSize-1, logic.categorySecondWords.size());// size of categorySecondWords should be decremented by 1

	}

	// Tests that word selected from the third category.
	@Test
	void pickWordTest3(){
		ServerLogic logic = new ServerLogic();
		logic.categoryFirstWords.add("apple");
		logic.categoryFirstWords.add("mango");
		logic.categoryFirstWords.add("banana");

		logic.categorySecondWords.add("spider");
		logic.categorySecondWords.add("ant");
		logic.categorySecondWords.add("cicada");

		logic.categoryThirdWords.add("carrot");
		logic.categoryThirdWords.add("broccoli");
		logic.categoryThirdWords.add("tomato");

		int initialSize = logic.categoryThirdWords.size();

		String randomVeggieWord = logic.pickWord("Vegetables"); // picks random word from Vegetables array
		assertNotNull(randomVeggieWord);

		ArrayList<String> tester = new ArrayList<>(); // array to check if selected random word is in list since it is removed when it is assigned in function
		tester.add("carrot");
		tester.add("broccoli");
		tester.add("tomato");

		boolean result = tester.contains(randomVeggieWord);  // makes sure word is in Vegetables array
		assertTrue(result);

		assertEquals(initialSize-1, logic.categoryThirdWords.size());// size of categoryThirdWords should be decremented by 1

	}

	// * * * * * * * * * *      TESTING duplicateGuess() FUNCTION      * * * * * * * * * * //
	// Tests that duplicate returns true when letter was already guessed
	@Test
	void duplicateGuessTest1(){
		ServerLogic logic = new ServerLogic();
		ArrayList<String> wordBank = new ArrayList<>();
		wordBank.add("a");
		wordBank.add("b");
		assertTrue(logic.duplicateGuess("a", wordBank));
		assertTrue(logic.duplicateGuess("b", wordBank));
	}
	// Tests that duplicate returns false when letter not guessed yet
	@Test
	void duplicateGuessTest2(){
		ServerLogic logic = new ServerLogic();
		ArrayList<String> wordBank = new ArrayList<>();
		wordBank.add("a");
		wordBank.add("b");
		assertFalse(logic.duplicateGuess("d", wordBank));
		assertFalse(logic.duplicateGuess("e", wordBank));
	}
	// make a case-sensitive one
	// * * * * * * * * * *      TESTING completeWord() FUNCTION      * * * * * * * * * * //
	// Test that function returns true when word is complete
	@Test
	void completeWordTest1(){
		ServerLogic logic = new ServerLogic();
		ArrayList<String> wordSoFar = new ArrayList<>();
		wordSoFar.add("a");
		wordSoFar.add("p");
		wordSoFar.add("p");
		wordSoFar.add("l");
		wordSoFar.add("e");
		boolean result = logic.completeWord("apple", wordSoFar);
		assertTrue(result);
	}
	// Test that function returns false when word is complete
	@Test
	void completeWordTest2(){
		ServerLogic logic = new ServerLogic();
		ArrayList<String> wordSoFar = new ArrayList<>();
		wordSoFar.add("a");
		wordSoFar.add("p");
		wordSoFar.add("p");
		wordSoFar.add("l");
		wordSoFar.add("z");
		boolean result = logic.completeWord("apple", wordSoFar);
		assertFalse(result);
	}
	// * * * * * * * * * *      TESTING noMoreGuesses() FUNCTION      * * * * * * * * * * //
	// Test to make sure user knows they have guesses left
	@Test
	void noMoreGuessesTest1(){
		ServerLogic logic = new ServerLogic();
		boolean result = logic.noMoreGuesses(1); // should return false
		assertFalse(result);
	}
	// Test to make sure user knows they don't have guesses left
	@Test
	void noMoreGuessesTest2(){
		ServerLogic logic = new ServerLogic();
		boolean result = logic.noMoreGuesses(0); // should return true
		assertTrue(result);
	}

	// * * * * * * * * * *      TESTING gameLost() FUNCTION      * * * * * * * * * * //
	// Ensures game is lost when one or more attempts is 0
	@Test
	void gameLostTest1(){
		ServerLogic logic = new ServerLogic();
		boolean result = logic.gameLost(0,1,2);
		assertTrue(result);
	}
	// Ensures game is not lost if attempts are greater than 0
	@Test
	void gameLostTest2(){
		ServerLogic logic = new ServerLogic();
		boolean result = logic.gameLost(1,1,2);
		assertFalse(result);
	}

}
