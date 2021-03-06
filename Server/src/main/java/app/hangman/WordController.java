package app.hangman;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class WordController {

    private List<String> words;
    private Random random;
    private DataBase dbo;

    public WordController() {
        words = new ArrayList<>();   
        random = new Random();
        dbo = new DataBase();

        loadData();   
    }

    private String findWordByHash(Long wordHash)
    {
        for(String word : words) {
            if(word.hashCode() == wordHash) {
                return word;
            }
        }

        return "";
    }

    private void loadData()
    {
        this.words = dbo.readWords();
    }

    @RequestMapping(value = "/getRandomWord", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WordInfo getRandomWord() {
        String randomWord = this.words.get(this.random.nextInt(this.words.size()));
        long wordHash = randomWord.hashCode();
        long letterCount = randomWord.length();

        WordInfo wordInfo = new WordInfo(wordHash, letterCount);

        return wordInfo;
    }

    @RequestMapping(value = "/getWordByHash", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Word getWordByHash(@RequestParam(value = "wordHash", required = true) Long wordHash) {
        Word word = new Word(this.findWordByHash(wordHash));
        
        return word;
    }

    @RequestMapping(value = "/getLetterPositions", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PositionWord getLetterPositions(@RequestParam (value = "wordHash", required = true) Long wordHash,
                                        @RequestParam (value = "letter", required = true) String letter) {
        PositionWord positionWord = new PositionWord();

        String word = this.findWordByHash(wordHash);
        if (!word.isEmpty())
        {
            int index = 0;
            while (index != -1) {
                index = word.indexOf(letter, index);
                if (index != -1) {
                    positionWord.addPosition(index);
                    index++;
                }
            }
        }
        
        return positionWord;
    }
}