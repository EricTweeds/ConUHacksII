package waterwoo.conuhacks;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Sean on 1/21/2017.
 */

public class WordFilter extends WordCounter{
    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream("C:/Users/Sean/Documents/ConuHacks2017/conuhacks/ConUHacksII/app/src/main/test.txt");
        Scanner reader = new Scanner(fin);

        List<String> speech = new ArrayList<String>();

        //while loop so we can go through every line in the text and pass it through our string
        while(reader.hasNext()){
            String nextWord = reader.next();
            speech.add(nextWord);
        }
        //testing to make sure string is outputted
        System.out.println(speech);

        String filter1 = "uhm";
        String filter2 = "umm";
        Integer filterCount = 0;

        for(int i = 0; i < speech.size(); i++){
            if(speech.get(i).contains(filter1) || speech.get(i).contains(filter2)) {
                filterCount++;
            }
        }
        System.out.println("You said uhm/umm this many times: " + filterCount);
    }
}
