package com.example.rollerofdice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    RadioButton radioButtonMod;
    TextView finalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button regRoll = findViewById(R.id.regRoll);

        regRoll.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                //after the bottom is clicked grab the information

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
                int radioId = radioGroupMod.getCheckedRadioButtonId();
                radioButtonMod = findViewById(radioId);

                //gets text from modifierInput
                editText =findViewById(R.id.modifierInput);
                temp = editText.getText().toString();
                int modifier = 0;
                if (temp.equals("")){
                    modifier = 0;
                }else {
                    modifier = Integer.parseInt(temp);
                }

                int rollTotal = 0;
                Random rand = new Random();
                for(int i = 1; i != numberOfDice+1; i++){
                    int randNumber = rand.nextInt(diceType+1);

                    if(randNumber == 0){
                        randNumber = 1;
                    }

                    if(radioButtonMod.equals("+")){
                        randNumber = randNumber + modifier;
                    }else{
                        randNumber = randNumber - modifier;
                    }
                    rollTotal += randNumber;
                }
                finalResult = findViewById(R.id.rollResult);
                finalResult.setText(String.valueOf(rollTotal));
                //Toast.makeText(MainActivity.this, radioButtonMod.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }
}