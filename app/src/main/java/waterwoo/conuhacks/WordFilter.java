package waterwoo.conuhacks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 1/21/2017.
 */

public class WordFilter extends WordCounter{
    public static void main(String[] args) throws IOException {
        FileReader file = new FileReader("C:/Users/Sean/Documents/ConuHacks2017/conuhacks/ConUHacksII/app/src/main/test.txt");
        BufferedReader reader = new BufferedReader(file);

        List<String> speech = new ArrayList<String>();
        String line = reader.readLine();

        //while loop so we can go through every line in the text and pass it through our string
        while(line != null){
            speech.add(line);
            line = reader.readLine();
        }
        //testing to make sure string is outputted
        System.out.println(speech);

        String filter = "uhm";
        List<String> filteredSpeech = new ArrayList<String>();

        for(int i = 0; i < speech.size(); i++){
            if(speech.get(i).contains(filter)){
                filteredSpeech.add(speech.get(i));
                System.out.println("You said " + filter + " this many times: " + filteredSpeech.size());
            }
        }
    }
}
