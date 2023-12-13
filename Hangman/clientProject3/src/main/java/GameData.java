import java.io.Serializable;
import java.util.ArrayList;

public class GameData implements Serializable {

    private static final long serialVersionUID = 1L;
    String category1;
    String category2;
    String category3;
    int categoryOneAttempts;
    int categoryTwoAttempts;
    int categoryThreeAttempts;
    String categoryPicked;
    boolean categoryChosen;
    String wordChosen;
    String currentChar;
    boolean guessMade;
    boolean correctGuess;
    int guessesLeft;
    boolean categoryOneDone;
    boolean categoryTwoDone;
    boolean categoryThreeDone;
    int categoriesWon;
    boolean gameWon;
    boolean gameLost;
    boolean roundLost;
    boolean roundWon;
    ArrayList<String> wordSoFar = new ArrayList<String>();
    ArrayList<String> lettersGuessed = new ArrayList<String>();
    String narrator;

}
