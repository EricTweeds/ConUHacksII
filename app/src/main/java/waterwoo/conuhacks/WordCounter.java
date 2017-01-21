package waterwoo.conuhacks;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Sean on 1/21/2017.
 */

public class WordCounter extends MainActivity{
    public static void main(String[] args) throws IOException{
        //input stream and scanner
        FileInputStream fin = new FileInputStream("test.txt");
        Scanner fileInput = new Scanner(fin);

        //creating array list
        ArrayList<String> speech = new ArrayList<String>();
        ArrayList<Integer> wordCount = new ArrayList<Integer>();

        //read through file and find words
        while(fileInput.hasNext()){
            //get next word
            String nextWord = fileInput.next();
            //determining if word is in the ArrayList
            if (speech.contains(nextWord)){
                int index = speech.indexOf(nextWord);

                //will increment word by 1 if it finds it for a second time
                wordCount.set(index, wordCount.get(index) + 1);
            }
            else{
                //if it's first time the word is appearing
                speech.add(nextWord);
                wordCount.add(1);
            }
        }
        //close reading file
        fileInput.close();
        fin.close();

        //print out results
        for(int i = 0; i < speech.size(); i++){
            System.out.println(speech.get(i) + "occurred " + wordCount.get(i) + "time(s)");
        }
    }
}
