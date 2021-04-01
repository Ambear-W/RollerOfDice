package com.example.rollerofdice;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroupMod;
    RadioButton plus, minus;
    TextView finalResult;
    TextView rollBreakDownText;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button regRoll = findViewById(R.id.regRoll);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        //if the roll button is click then roll the dice and vibrate the phone when done
        regRoll.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //after the button is clicked grab the information

                //gets the text from numberOfDiceInput
                EditText editText =findViewById(R.id.numberOfDiceInput);
                String temp = editText.getText().toString();
                int numberOfDice = 0;
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

                int modifier = 0;
                if (temp.equals("")){
                    modifier = 0;
                }else {
                    modifier = Integer.parseInt(temp);
                }

                //does the actual rolling
                int rollTotal = 0;
                String rollBreakDown = "";
                int randNumber = 0;
                Random rand = new Random();
                for(int i = 1; i != numberOfDice+1; i++){
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
            }
        });
    }
}