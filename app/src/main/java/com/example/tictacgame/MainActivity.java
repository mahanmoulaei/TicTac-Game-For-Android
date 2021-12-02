package com.example.tictacgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int grid_size;
    TableLayout gameBoard;
    TextView textViewTurn;
    char[][] my_board;
    char turn;

    Button buttonReset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void ResetBoard() {
    }
}