package app.hangman;

public class WordInfo {
    Long wordHash;
    Long letterCount;

    public Long getWordHash() {
        return wordHash;
    }

    public Long getLetterCount() {
        return letterCount;
    }

    public void setWordHash(Long wordHash) {
        this.wordHash = wordHash;
    }

    public void setLetterCount(Long letterCount) {
        this.letterCount = letterCount;
    }
}
