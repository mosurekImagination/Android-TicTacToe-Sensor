package com.example.android.tictactoe2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseTurnPanelActivity extends AppCompatActivity {


    Button b1;
    Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_turn_panel);
        b1 = (Button) findViewById(R.id.PlayerTurn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ComputerGameActivity.class);
                intent.putExtra(getResources().getString(R.string.p1Turn),true);
                //finish();
                startActivity(intent);
            }
        });
        b2 = (Button) findViewById(R.id.ComputerTurn);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ComputerGameActivity.class);
                intent.putExtra(getResources().getString(R.string.p1Turn), false);
                //finish();
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        finish();
        startActivity(intent);
    }
}
