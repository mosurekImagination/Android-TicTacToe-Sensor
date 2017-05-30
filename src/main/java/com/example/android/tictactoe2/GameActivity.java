package com.example.android.tictactoe2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener{

    protected ArrayList<Button> buffor;
    protected ArrayList<Button> player1;
    String player1Sign;
    String player2Sign;
    protected ArrayList<Button> player2;
    protected boolean turnPlayer1;
    protected final int numberOfButtons = 9;
    protected ButtonClass [] buttons;
    private SensorManager mSensorManager;
    private boolean mInitialized;
    private Sensor mAccelerometer;
    private final float NOISE = (float) 1.5;
    private float mLastX, mLastY;
    private int selected;
    private int selectedBuffer;
    private long timeAfterMove;
    /*
     *  2 7 6
     *  9 5 1
     *  4 3 8
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        buffor = new ArrayList<Button>();
        player1 = new ArrayList<Button>();
        player2 = new ArrayList<Button>();
        buttons = new ButtonClass[9];
        timeAfterMove = System.currentTimeMillis();
        addButtons();
        buttons[selected].setBackgroundColor(ContextCompat.getColor(this, R.color.selected));
        player1Sign = getResources().getString(R.string.x);
        player2Sign = getResources().getString(R.string.o);
        turnPlayer1 = true;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
    protected View.OnClickListener listener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            proceedClick(buttons[selected]);

        }
    };

    protected void addButtons()
    {

        buttons[0]= new ButtonClass(getApplicationContext(),2);
        buttons[1]= new ButtonClass(getApplicationContext(),7);
        buttons[2]= new ButtonClass(getApplicationContext(),6);

        buttons[3]= new ButtonClass(getApplicationContext(),9);
        buttons[4]= new ButtonClass(getApplicationContext(),5);
        buttons[5]= new ButtonClass(getApplicationContext(),1);

        buttons[6]= new ButtonClass(getApplicationContext(),4);
        buttons[7]= new ButtonClass(getApplicationContext(),3);
        buttons[8]= new ButtonClass(getApplicationContext(),8);


        for (int i = 0; i < numberOfButtons; i++){
            buttons[i].setOnClickListener(listener);
            buttons[i].setBackgroundColor(ContextCompat.getColor(this, R.color.background));
            LinearLayout lL;
            if(i<3) {lL = (LinearLayout) findViewById(R.id.lLay0);}
            else if(i<6){lL = (LinearLayout) findViewById(R.id.lLay1);}
            else {lL = (LinearLayout) findViewById(R.id.lLay2);}

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1);
            lL.addView(buttons[i],lp);
            buffor.add(buttons[i]);
        }

        selected = 4;
        changeColorMove();
    }

    protected void proceedClick(ButtonClass button){
        if(button.getText() != getResources().getString(R.string.x) && button.getText() != getResources().getString(R.string.o)){
            proceedTurn(button);
        }

    }

    private void changeColorMove()
    {
        buttons[selectedBuffer].setBackgroundColor(ContextCompat.getColor(this, R.color.background));
        buttons[selected].setBackgroundColor(ContextCompat.getColor(this, R.color.selected));
    }

    protected void proceedTurn(ButtonClass button){//different for child
        if(turnPlayer1){
            player1.add(button);
            button.setText(player1Sign);
            buffor.indexOf(button);
            buffor.remove(button);
            checkResult();
            turnPlayer1 = false;

        }
        else{
            player2.add(button);
            button.setText(player2Sign);
            buffor.remove(button);
            checkResult();
            turnPlayer1 = true;
        }


    }

    private void goLeft()
    {
        selectedBuffer = selected;
        switch (selected)
        {
            case 1: { selected = 0; break;}
            case 2: { selected = 1; break;}
            case 4: { selected = 3; break;}
            case 5: { selected = 4; break;}
            case 7: { selected = 6; break;}
            case 8: { selected = 7; break;}
        }
        changeColorMove();
    }

    private void goRight()
    {
        selectedBuffer = selected;
        switch (selected)
        {
            case 0: { selected = 1; break;}
            case 1: { selected = 2; break;}
            case 3: { selected = 4; break;}
            case 4: { selected = 5; break;}
            case 6: { selected = 7; break;}
            case 7: { selected = 8; break;}
        }
        changeColorMove();
    }

    private void goTop()
    {
        selectedBuffer = selected;
        switch (selected)
        {
            case 3: { selected = 0; break;}
            case 4: { selected = 1; break;}
            case 5: { selected = 2; break;}
            case 6: { selected = 3; break;}
            case 7: { selected = 4; break;}
            case 8: { selected = 5; break;}
        }
        changeColorMove();
    }

    private void goDown()
    {
        selectedBuffer = selected;
        switch (selected)
        {
            case 0: { selected = 3; break;}
            case 1: { selected = 4; break;}
            case 2: { selected = 5; break;}
            case 3: { selected = 6; break;}
            case 4: { selected = 7; break;}
            case 5: { selected = 8; break;}
        }
        changeColorMove();
    }


    protected void checkResult(){
        if(buffor.size() <= numberOfButtons-5){


                if(checkPlayerResult(player1)){

                    change(player1Sign);
                }
                else if(checkPlayerResult(player2)){

                    change(player2Sign);
                }
                else if(buffor.size() == 0){

                    change("-");
            }
        }
    }
    protected void change(String winnerSign){
        Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
        intent.putExtra(getResources().getString(R.string.winner), winnerSign);
        //finish();
        startActivity(intent);
    }

    protected boolean checkPlayerResult(ArrayList<Button> player){
        for(int a = 0; a < player.size() - 2; a++)
            for(int b = a + 1; b < player.size() - 1; b += 1)
                for(int c = b + 1; c < player.size(); c = c + 1){
                    ButtonClass aa =  (ButtonClass) player.get(a);
                    ButtonClass bb =  (ButtonClass) player.get(b);
                    ButtonClass cc =  (ButtonClass) player.get(c);
                    if(aa.getNumber() + bb.getNumber() + cc.getNumber() == 15) return true;
                }
        return false;
    }

    protected void onResume() {

        super.onResume();

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
    protected void onPause() {

        super.onPause();

        mSensorManager.unregisterListener(this);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];

            if (!mInitialized) {

                mLastX = x;

                mLastY = y;

                mInitialized = true;

            } else
                {
                float deltaX = mLastX - x;
                float deltaY = mLastY - y;

                if (Math.abs(deltaX) < NOISE) deltaX = (float) 0.0;
                if (Math.abs(deltaY) < NOISE) deltaY = (float) 0.0;

                mLastX = x;

                mLastY = y;
                float time=System.currentTimeMillis() - timeAfterMove;
                if((deltaX != 0 || deltaY !=0) && time > 1000)
                {
                    if (deltaX > 0) {
                        goRight();
                    } else if (deltaY > 0) {
                        goTop();
                    } else if (deltaX < 0) {
                        goLeft();
                    } else if (deltaY < 0) {
                        goDown();
                    }

                    timeAfterMove = System.currentTimeMillis();
                }

                }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {
        proceedClick(buttons[selected]);
    }
}
