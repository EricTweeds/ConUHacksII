package waterwoo.conuhacks;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Sean on 1/21/2017.
 */

public class WordCounter extends MainActivity{

    //method to sort results by occurrences
    public static Map<String, Integer> sortByValue(Map<String, Integer> map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static void main(String[] args) throws IOException{
        //input stream and scanner
        FileInputStream fin = new FileInputStream("C:/Users/Sean/Documents/ConuHacks2017/conuhacks/ConUHacksII/app/src/main/test.txt");
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
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for(int i = 0; i < speech.size(); i++) {
            map.put(speech.get(i), wordCount.get(i));
        }
        for(Map.Entry<String, Integer> entry: map.entrySet()) {
            System.out.println(entry.getKey() + " appeared: " + entry.getValue() + " times");
        }
        System.out.println("********");

        Map<String, Integer> sortedMap = WordCounter.sortByValue(map);

        for(Map.Entry<String, Integer> entry: sortedMap.entrySet()) {
            System.out.println(entry.getKey() + " appeared: " + entry.getValue() + " times");
        }
    }
}
