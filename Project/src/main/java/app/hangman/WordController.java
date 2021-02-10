package app.hangman;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        word.add("LLLL");

    }

    static public String randomWord;

    {
        randomWord = word.stream().skip(random.nextInt(word.size())).findFirst().get();
    }

    long hash = randomWord.hashCode();

    @RequestMapping(value = "/getRandomWord", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WordInfo controler() {



        long sizeWord = randomWord.chars().count();
        long hashWord = randomWord.hashCode();

        WordInfo exampleRestControler = new WordInfo();
        exampleRestControler.setWordHash(hashWord);
        exampleRestControler.setLetterCount(sizeWord);

        return exampleRestControler;
    }

    @RequestMapping(value = "/getWordByHash", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Word WordByHash(@RequestParam (value = "wordHash", required = true) Long wordHash) {

        Word word = new Word();

        if (wordHash == hash) {
            word.setWord(randomWord);
        }
        return word;
    }

    @RequestMapping(value = "/getLetterPositions", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PositionWord LetterPositions(@RequestParam (value = "wordHash", required = true) Long wordHash,
                                        @RequestParam (value = "letter", required = true) String letter)
    {
        PositionWord positionWord = new PositionWord();


        if (wordHash == hash) {

            List<Integer> indexes = new ArrayList<>();

            int index = 0;
            while(index != -1){
                index = randomWord.indexOf(letter, index);
                if (index != -1) {
                    indexes.add(index);
                    index++;
                }
            }
            positionWord.setPosition(indexes);
        }

        else if(wordHash != hash) {
            positionWord.setPosition(null);
        }

        return positionWord;
    }
}