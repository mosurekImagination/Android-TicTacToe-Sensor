package com.example.android.tictactoe2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    Button b1;
    Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
       // actionBar.setTitle("TicTacToe");

        b1 = (Button) findViewById(R.id.SinglePlayer);
        b1.setOnClickListener(singlePlayerListener);
        b2 = (Button) findViewById(R.id.MultiPlayer);
        b2.setOnClickListener(multiPlayerListener);
    }

    public View.OnClickListener singlePlayerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ChooseTurnPanelActivity.class);
            startActivity(intent);
        }
    };

    public View.OnClickListener multiPlayerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            startActivity(intent);
        }
    };

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_exit:
                onBackPressed();
                return true;

            case R.id.menu_info:
                onBackPressed(); //TO-DO
                return true;

            case R.id.new_game:
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


}
