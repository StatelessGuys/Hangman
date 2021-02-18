package app.hangman;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Stream;

@RestController
public class WordController {

    private ArrayList<String> words;
    private Random random;

    public WordController() {
        words = new ArrayList<>();   
        random = new Random();  

        //in future it should read data from db
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
        this.words.add("MAN");
        this.words.add("HOUSE");
        this.words.add("STAR");
        this.words.add("LOVE");
        this.words.add("LLLL");
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

    @RequestMapping(value = "/get")
    public List<WordJDBC> ge() {
        DataBase dbo = new DataBase();
        return dbo.index();
    }

}