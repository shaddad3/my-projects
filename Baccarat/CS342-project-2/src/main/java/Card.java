import javafx.scene.control.Button;

public class Card {
    String suite;
    int value;
    String faceName;
    //Button cardButton;

    Card(String suite, int value) {
        this.suite = suite;
        this.value = value;
        //cardButton = new Button(value + " of " + suite); //maybe change value to faceName
        this.faceName = value + "of" + suite;
        //so Ace, Jack, etc are named correct
    }
}



