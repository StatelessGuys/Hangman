package app.hangman.Config.Controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Random;

@RestController
public class WordControler {

    @GetMapping("/getRandomWord")
    public String home() {

        HashSet<String> word = new HashSet<String>();
        Random random = new Random();

        word.add("man");
        word.add("house");
        word.add("star");
        word.add("love");

        String randomWord = word.stream().skip(random.nextInt(word.size())).findFirst().get();
        long sizeWord = randomWord.chars().count();
        System.out.println(sizeWord);
        return String.format("%s, %s", randomWord.hashCode(), sizeWord);
    }
}
