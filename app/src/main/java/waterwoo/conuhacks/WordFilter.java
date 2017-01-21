package waterwoo.conuhacks;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sean on 1/21/2017.
 */

public class WordFilter extends MainActivity{
    public static void main(String[] args) {
        List<String> fillerWords = Arrays.asList("um", "uhm", "uh");
        String speech = "Hi, my name is Sean uh, I like uhm dogs";
        for (String fillerWord : fillerWords) {
            speech = speech.replaceAll("(?i)\\b[^\\w -]*" + fillerWord + "[^\\w -]*\\b", "");
        }
        System.out.println(speech);
    }
}
