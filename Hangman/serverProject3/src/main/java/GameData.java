import java.io.Serializable;
import java.util.ArrayList;

public class GameData implements Serializable {

    private static final long serialVersionUID = 1L;
    String category1 = "Fruits";
    String category2 = "Insects";
    String category3 = "Vegetables";
    int categoryOneAttempts = 3;
    int categoryTwoAttempts = 3;
    int categoryThreeAttempts = 3;
    String categoryPicked = "";
    boolean categoryChosen = false;
    String wordChosen = "";
    String currentChar = "";
    boolean guessMade = false;
    boolean correctGuess = false;
    int guessesLeft = 6;
    boolean categoryOneDone = false;
    boolean categoryTwoDone = false;
    boolean categoryThreeDone = false;
    int categoriesWon = 0;
    boolean gameWon = false;
    boolean gameLost = false;
    boolean roundLost = false;
    boolean roundWon = false;
    ArrayList<String> wordSoFar = new ArrayList<String>();
    ArrayList<String> lettersGuessed = new ArrayList<String>();
    String narrator;

}
