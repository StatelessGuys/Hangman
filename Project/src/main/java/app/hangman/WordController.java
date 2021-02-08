package app.hangman;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Random;

@RestController
public class WordController {


    HashSet<String> word = new HashSet<String>();
    Random random = new Random();

    {
        word.add("MAN");
        word.add("HOUSE");
        word.add("STAR");
        word.add("LOVE");
    }

    static public String randomWord;

    @RequestMapping(value = "/getRandomWord", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WordInfo controler() {

        randomWord = word.stream().skip(random.nextInt(word.size())).findFirst().get();

        long sizeWord = randomWord.chars().count();
        long hashWord = randomWord.hashCode();

        WordInfo exampleRestControler = new WordInfo();
        exampleRestControler.setWordHash(hashWord);
        exampleRestControler.setLetterCount(sizeWord);

        return exampleRestControler;
    }

    @RequestMapping(value = "/getWordByHash", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Word WordByHash() {

        Word word = new Word();
        word.setWordByHash(randomWord);
        return word;
    }

    @RequestMapping(value = "/getLetterPositions", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PositionWord LetterPositions(@RequestParam (value = "hashcode", required = true) Long hash,
                                        @RequestParam (value = "letter", required = true) String letter)
    {

        PositionWord positionWord = new PositionWord();
        long hashWord = randomWord.hashCode();

        if (hashWord == hash) {
            positionWord.setPosition(randomWord.indexOf(letter));
        }
        else if(hashWord != hash) {
            positionWord.setPosition(404);
        }

        return positionWord;
    }
}