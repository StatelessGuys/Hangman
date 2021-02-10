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

    private HashSet<String> words = new HashSet<>();;
    private Random random;

    WordController() {
        words.add("MAN");
        words.add("HOUSE");
        words.add("STAR");
        words.add("LOVE");
        words.add("LLLL");
    }

    @RequestMapping(value = "/getRandomWord", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WordInfo controler() {
        random = new Random();

        String randomWord = words.stream().skip(random.nextInt(words.size())).findFirst().get();
        long sizeWord = randomWord.chars().count();
        long hashWord = randomWord.hashCode();

        WordInfo exampleRestControler = new WordInfo();
        exampleRestControler.setWordHash(hashWord);
        exampleRestControler.setLetterCount(sizeWord);

        return exampleRestControler;
    }

    @RequestMapping(value = "/getWordByHash", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Word WordByHash(@RequestParam(value = "wordHash", required = true) Long wordHash) {

        Word word = new Word();
        for(String name : words) {
            if(name.hashCode() == wordHash) {
                word.setWord(name);
                break;
            }
        }
        return word;
    }

    @RequestMapping(value = "/getLetterPositions", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PositionWord LetterPositions(@RequestParam (value = "wordHash", required = true) Long wordHash,
                                        @RequestParam (value = "letter", required = true) String letter) {
        PositionWord positionWord = new PositionWord();


//        Iterator<String> itr = words.iterator();
//
//        while (itr.hasNext()) {
//            String findWord = itr.next();
//            if(findWord.hashCode() == wordHash) {
//
//                break;
//            }
//        }

        for (String name : words) {

            if (name.hashCode() == wordHash) {

                List<Integer> indexes = new ArrayList<>();

                int index = 0;
                while (index != -1) {
                    index = name.indexOf(letter, index);
                    if (index != -1) {
                        indexes.add(index);
                        index++;
                    }
                }
                positionWord.setPosition(indexes);
                break;
            }
        }
        return positionWord;
    }
}
}