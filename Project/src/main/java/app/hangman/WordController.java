package app.hangman;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Random;

@RestController
public class WordController {

    @RequestMapping(value = "/getRandomWord", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WordInfo controler() {

        HashSet<String> word = new HashSet<String>();
        Random random = new Random();

        word.add("MAN");
        word.add("HOUSE");
        word.add("STAR");
        word.add("LOVE");

        String randomWord = word.stream().skip(random.nextInt(word.size())).findFirst().get();
        long sizeWord = randomWord.chars().count();
        long hashWord= randomWord.hashCode();

        WordInfo exampleRestControler = new WordInfo();
        exampleRestControler.setWordHash(hashWord);
        exampleRestControler.setLetterCount(sizeWord);

        return exampleRestControler;

    }
}
