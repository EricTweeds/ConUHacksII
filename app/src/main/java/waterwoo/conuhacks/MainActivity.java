package waterwoo.conuhacks;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

    private TextView resultText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = (TextView)findViewById(R.id.TVresult);
    }
    //mic function
    public void onButtonClick(View v){
        if(v.getId()== R.id.imageButton){
            promptSpeechInput();
        }
    }

    public void promptSpeechInput(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!");

        try{
            startActivityForResult(i, 1000);
        }
        catch(ActivityNotFoundException a) {
            Toast.makeText(MainActivity.this, "Sorry! Your device does not support speech recognition!", Toast.LENGTH_LONG).show();
        }
    }

    //receiving speech input
    public void onActivityResult(int request_code, int result_code, Intent i){
        super.onActivityResult(request_code, result_code, i);

        switch(request_code){
            case 1000: if(result_code == RESULT_OK && i != null){
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                resultText.setText(result.get(0));
            }
                break;
        }
    }

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
    public void main(String[] args) throws IOException {
        //input stream and scanner
        TextView filterCount;
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
        //debugging

        //filtering filler words
        String filter1 = "uhm";
        String filter2 = "um";
        int filterCount = 0;
        for(int i = 0; i < speech.size(); i++){
            if(speech.get(i).contains(filter1) || speech.get(i).contains(filter2)) {
                filterCount++;
            }
        }

        System.out.println("******");
        TextView filterText = (TextView)findViewById(R.id.TVFilterCount);
        filterText.setText("" + filterCount);
        //close reading file
        fileInput.close();
        fin.close();


        //print out results
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for(int i = 0; i < speech.size(); i++) {
            map.put(speech.get(i), wordCount.get(i));
        }

        Map<String, Integer> sortedMap = MainActivity.sortByValue(map);

        for(Map.Entry<String, Integer> entry: sortedMap.entrySet()) {
            System.out.println(entry.getKey() + " appeared: " + entry.getValue() + " times");
        }
    }
}