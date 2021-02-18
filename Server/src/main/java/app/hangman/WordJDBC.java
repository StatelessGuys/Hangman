package app.hangman;

public class WordJDBC {

    Integer id;
    String words;

    public  WordJDBC() {}

    public WordJDBC( Integer id , String word) {
        this.id = id;
        this.words = word;
    }

    public void setWord(String word) {
        this.words = word;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return words;
    }

    public Integer getId() {
        return id;
    }
}
