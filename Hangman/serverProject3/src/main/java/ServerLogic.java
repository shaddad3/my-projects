import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ServerLogic {

    ArrayList<String> categoryFirstWords = new ArrayList<>();

    ArrayList<String> categorySecondWords = new ArrayList<>();

    ArrayList<String> categoryThirdWords = new ArrayList<>();

    //isInWord() takes two Strings, and checks to see if the second String, which is
    //only one character long, is in the first String. If it is, return true, otherwise, false
    public boolean isInWord(String word, String guess) {
        return (word.contains(guess));
    }

    //gameWon() checks to see if the amount of categories won is 3, if so return true, otherwise, false
    public boolean gameWon(int categoriesWon) {
        return (categoriesWon == 3);
    }

    //pickWord() takes in the category chosen, and matches it to the array list of words for that
    //category. It then picks a random word from that category and returns it.
    public String pickWord(String category) {
        String word = "";
        Random rand = new Random();

        if (Objects.equals(category, "Fruits")) { //pick word from "first" category

            word = categoryFirstWords.remove(rand.nextInt(categoryFirstWords.size())); //get and remove the word from the list of words
        }
        else if (Objects.equals(category, "Insects")) { //pick word from "second" category
            word = categorySecondWords.remove(rand.nextInt(categorySecondWords.size())); //get and remove the word from the list of words
        }
        else if (Objects.equals(category, "Vegetables")) { //pick word from "third" category
            word = categoryThirdWords.remove(rand.nextInt(categoryThirdWords.size())); //get and remove the word from the list of words
        }

        return word;
    }

    //duplicateGuess() checks to see if the character guessed is already in an array list containing
    //all the characters that have been guessed up until now, if so, return true, otherwise, false.
    public boolean duplicateGuess(String guess, ArrayList<String> wordBank) {
        for (int i = 0; i < wordBank.size(); i++) {
            if (Objects.equals(guess, wordBank.get(i))) { //guess character equals an index of the letters guessed
                return true;
            }
        }
        return false;
    }

    //completeWord() checks to see if the array list containing all the correct guesses and their
    //indices is all filled up and matches the word the user is trying to guess. If so, return true, otherwise, false.
    public boolean completeWord(String word, ArrayList<String> wordSoFar) {
        for (int i = 0; i < word.length(); i++) {
            if (!(Objects.equals(word.charAt(i), wordSoFar.get(i).charAt(0)))) { //not every index of the array matches the word, so return false
                return false;
            }
        }
        return true;
    }

    //noMoreGuesses() checks to see if the amount of guesses left is 0, if so, return true, otherwise, false.
    public boolean noMoreGuesses(int guesses) {
        return (guesses == 0);
    }

    //gameLost() checks to see if any of the attempt numbers have hit 0, if so return true, otherwise, false.
    public boolean gameLost(int attempts1, int attempts2, int attempts3) {
        if ((attempts1 == 0) || (attempts2 == 0) || attempts3 == 0) {
            return true;
        }
        return false;
    }

}
