package com.example.android.tictactoe2;

import android.content.Context;
import android.widget.Button;

/**
 * Created by Jan on 09.02.2017.
 */
public class ButtonClass extends Button {
    int number;
    public ButtonClass(Context context) {
        super(context);
    }
    public ButtonClass(Context context, int number){
        super(context);
        this.number = number;
    }
    public void setNumber(int i){
        this.number = i;
    }
    public int getNumber(){
        return this.number;
    }
}
