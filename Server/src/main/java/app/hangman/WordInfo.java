package app.hangman;

public class WordInfo {
    private Long wordHash;
    private Long letterCount;

    public WordInfo(Long hash, Long count)
    {
        this.wordHash = hash;
        this.letterCount = count;
    }
    public Long getWordHash() {
        return wordHash;
    }

    public Long getLetterCount() {
        return letterCount;
    }
}
