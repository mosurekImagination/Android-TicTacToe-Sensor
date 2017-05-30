package com.example.android.tictactoe2;



import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ComputerGameActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        buffor = new ArrayList<Button>();
        player1 = new ArrayList<Button>();//player
        player2 = new ArrayList<Button>();//computer
        buttons = new ButtonClass[9];
        addButtons();
        player1Sign = getString(R.string.x);
        player2Sign = getString(R.string.o);
        Intent intent = getIntent();
        turnPlayer1 = intent.getExtras().getBoolean(getString(R.string.p1Turn));

        if(!turnPlayer1)
            computerTurn();
    }


    /*
         *  2 7 6
         *  9 5 1
         *  4 3 8
         */

    private void computerTurn(){
        boolean ifChosenField = false;
        if(player2.size() >= 2){
            for(int i = 0; i < player2.size() - 1 && !ifChosenField; i ++)
                for(int j = i + 1; j < player2.size() && !ifChosenField; j ++){
                    ButtonClass b1 = (ButtonClass) player2.get(i);
                    ButtonClass b2 = (ButtonClass) player2.get(j);
                    Iterator<Button> it = buffor.iterator();
                    while(!ifChosenField && it.hasNext()){
                        ButtonClass bTemp = (ButtonClass) it.next();
                        if(bTemp.getNumber() == 15 - b1.getNumber() - b2.getNumber()){
                            player2.add(bTemp);
                            buffor.remove(bTemp);
                            bTemp.setText(player2Sign);
                            ifChosenField = true;
                        }
                    }

                }
        }
        if(!ifChosenField && player1.size() >=2){
            for(int i = 0; i < player1.size() - 1 && !ifChosenField; i ++)
                for(int j = i + 1; j < player1.size() && !ifChosenField; j ++){
                    ButtonClass b1 = (ButtonClass) player1.get(i);
                    ButtonClass b2 = (ButtonClass) player1.get(j);
                    Iterator it = buffor.iterator();
                    while(!ifChosenField && it.hasNext()){
                        ButtonClass bTemp = (ButtonClass) it.next();
                        if(bTemp.getNumber() == (15 - b1.getNumber() - b2.getNumber())){
                            player2.add(bTemp);
                            buffor.remove(bTemp);
                            bTemp.setText(player2Sign);
                            ifChosenField = true;
                        }
                    }

                }
        }
        if(!ifChosenField){
            Random rand = new Random();
            int index = rand.nextInt(buffor.size());
            ButtonClass bTemp = (ButtonClass) buffor.remove(index);
            player2.add(bTemp);
            bTemp.setText(player2Sign);

        }
        turnPlayer1 = true;
        ifChosenField = false;
    }


    protected void proceedTurn(ButtonClass button){
        if(turnPlayer1){
            player1.add(button);
            button.setText(player1Sign);
            buffor.remove(button);
            checkResult();
            turnPlayer1 = false;
        }
        if(buffor.size() > 0){
            computerTurn();
            checkResult();
        }

    }
}
