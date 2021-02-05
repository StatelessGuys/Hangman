package app.hangman;

public class Word {
    String word;

    public String getWord() {
        return word;
    }

    public String setWordByHash(String word) {
        this.word = word;
        return word;
    }
}
