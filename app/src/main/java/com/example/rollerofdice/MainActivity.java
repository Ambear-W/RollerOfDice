package com.example.rollerofdice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.icu.util.Output;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Random;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroupMod;
    RadioButton plus, minus;
    TextView finalResult;
    TextView rollBreakDownText;
    Vibrator vibrator;
    TextView historyText;

    int numberOfDice = 0;
    int modifier = 0;
    int rollTotal = 0;
    int randNumber = 0;

    String filename = "historyRoll.txt";
    String filepath = "MyFileDir";
    String fileContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button regRoll = findViewById(R.id.regRoll);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        final Button history = findViewById(R.id.history);
        //File myExternalFile = new File(getExternalFilesDir(filepath), filename);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyText = findViewById(R.id.historyRolls);

                FileReader fr = null;
                File myExternalFile = new File(getExternalFilesDir(filepath), filename);
                StringBuilder stringBuilder = new StringBuilder();

                if(finalResult == null || rollBreakDownText == null){

                }else{
                    finalResult.setText("");
                    rollBreakDownText.setText("");
                }

                try{
                    fr = new FileReader(myExternalFile);
                    BufferedReader br = new BufferedReader(fr);
                    String line = br.readLine();
                    while(line != null){
                        stringBuilder.append(line).append('\n');
                        line = br.readLine();
                    }

                } catch(FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }finally{
                    String fileContents = stringBuilder.toString();
                    historyText.setText(fileContents);
                }

            }
        });

        //if the roll button is click then roll the dice and vibrate the phone when done
        regRoll.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //after the button is clicked grab the information
                if(historyText == null){

                }else{
                    historyText.setText("");
                }

                //gets the text from numberOfDiceInput
                EditText editText =findViewById(R.id.numberOfDiceInput);
                String temp = editText.getText().toString();
                if (temp.equals("")){
                    numberOfDice = 1;
                }else{
                    numberOfDice = Integer.parseInt(temp);
                }

                //check for type of dice
                Spinner gettingDiceType = (Spinner) findViewById(R.id.typeOfDiceInput);
                int diceType = Integer.parseInt(gettingDiceType.getSelectedItem().toString());

                //check for mod type seleciton (plus or minus)
                radioGroupMod = findViewById(R.id.modTypeGroup);
                plus = findViewById(R.id.plus);
                minus = findViewById(R.id.minus);

                //gets text from modifierInput
                editText =findViewById(R.id.modifierInput);
                temp = editText.getText().toString();

                if (temp.equals("")){
                    modifier = 0;
                }else {
                    modifier = Integer.parseInt(temp);
                }

                //does the actual rolling
                String rollBreakDown = "";
                Random rand = new Random();
                for(int i = 1; i != numberOfDice + 1; i++){
                    randNumber = rand.nextInt(diceType) + 1;

                    //adds text to the final roll to change on screen
                    if(i == numberOfDice){
                        rollBreakDown = rollBreakDown.concat(String.valueOf(randNumber) + " + (" + modifier + ")" );
                    }else{
                        rollBreakDown = rollBreakDown.concat(String.valueOf(randNumber) + " + (" + modifier + ") + ");
                    }

                    //finds out the modifier and then add/minus it
                    if(plus.isChecked()){
                        randNumber = randNumber + modifier;
                    }
                    if(minus.isChecked()){
                        randNumber = randNumber - modifier;
                    }

                    rollTotal += randNumber;
                }

                //sets the text on screen to the roll
                finalResult = findViewById(R.id.rollResult);
                finalResult.setText(String.valueOf(rollTotal));

                rollBreakDownText = findViewById(R.id.rollBreakDown);
                rollBreakDownText.setText(String.valueOf(rollBreakDown));

                //vibrates the phone
                vibrator.vibrate(1000);

                //makes the rollHistory file and adds it into memory

                fileContent = rollBreakDown + " = " + (String.valueOf(rollTotal)) + "\n";

                if(!fileContent.equals("")){
                    File myExternalFile = new File(getExternalFilesDir(filepath), filename);
                    FileOutputStream fos = null;
                    try{
                        fos = new FileOutputStream(myExternalFile, true);
                        fos.write(fileContent.getBytes());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                 }

                numberOfDice = 0;
                modifier = 0;
                rollTotal = 0;
                randNumber = 0;

            }
        });
    }
}