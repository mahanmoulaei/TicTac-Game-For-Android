package com.example.tictacgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


        grid_size = Integer.parseInt("3");

        my_board = new char[grid_size][grid_size];

        gameBoard = (TableLayout) findViewById(R.id.mainBoard);
        //gameBoard = findViewById(R.id.mainBoard);

        textViewTurn = (TextView) findViewById(R.id.textViewTurn);
        //textViewTurn = findViewById(R.id.textViewTurn);

        ResetBoard();

        textViewTurn.setText("Turn : " + turn);

        for (int i=0; i < gameBoard.getChildCount(); i++){
            TableRow row = (TableRow)  gameBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++){
                TextView textView = (TextView) row.getChildAt(j);
                textView.setText(" ");
                textView.setOnClickListener(Move(i, j, textView));
            }
        }

        buttonReset = findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent current = getIntent();
                finish();
                startActivity(current);
            }
        });
    }

    protected void ResetBoard() {
        turn = 'X';

        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                my_board[i][j] = ' ';
            }
        }
        textViewTurn.setText("Turn : " + turn);
    }

    protected boolean Cell_Set(int i, int j) {
        return (my_board[i][j] == ' '); //if what I have inside of my cell is empty is equal to empty ' ', the cell has no value => return true
    }

    View.OnClickListener Move(final int i, final int j, final TextView textView) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Cell_Set(i, j)) { //lets return after set the cell
                    my_board[i][j] = turn;
                    if (turn == 'X') {
                        textView.setText(R.string.X);
                        turn = 'O';
                    } else if (turn == 'O') {
                        textView.setText(R.string.O);
                        turn = 'X';
                    }

                    if (GameStatus() == 0) {
                        textViewTurn.setText("Turn : Player " + turn);
                    } else if (GameStatus() == -1) {
                        StopMatch("Draw");
                    } else {
                        StopMatch("Win");
                    }

                } else {
                    textViewTurn.setText(" What the fuck dude? Are you blind? Choose An Empty Cell!");
                    //TODO: Replace with Toast instead
                }
            }
        };
    }

    protected int GameStatus() {
        //0  : Continue
        //1  : Win
        //-1 : Draw

        int rowX = 0, columnX = 0, column = 0;
        for (int i = 0; i < grid_size; i++) {
            if (CheckRow(i, 'X')) {
                return 1;
            }
            if (CheckColumn(i, 'X')) {
                return 1;
            }
            if (CheckDiagonal(i, 'X')) {
                return 1;
            }
            if (CheckRow(i, 'O')) {
                return 1;
            }
            if (CheckColumn(i, 'O')) {
                return 1;
            }
            if (CheckDiagonal(i, 'O')) {
                return 1;
            }
        }

        boolean boardFull = true;

        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                if (my_board[i][j] == ' ') {
                    boardFull = false;
                    break;
                }
            }
        }

        if (boardFull) {
            return -1;
        } else {
            return 0;
        }

    }

    protected void StopMatch(String status) {
        if (status == "Draw") {
            textViewTurn.setText("This is a Draw Match!");
        } else if (status == "Win") {
            textViewTurn.setText("Player " + turn + " Lost!");
        }

        DisableBoardOnClickListener();
    }

    protected void DisableBoardOnClickListener() {
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            TableRow row = (TableRow) gameBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView tv = (TextView) row.getChildAt(j);
                tv.setOnClickListener(null);
            }
        }
    }

    protected boolean CheckRow(int r, char player) {
        int countEqual = 0;

        for (int i = 0; i < grid_size; i++) {
            if (my_board[r][i] == player) {
                countEqual++;
            }
        }

        if (countEqual == grid_size) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean CheckColumn(int c, char player) {
        int countEqual = 0;

        for (int i = 0; i < grid_size; i++) {
            if (my_board[i][c] == player) {
                countEqual++;
            }
        }

        if (countEqual == grid_size) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean CheckDiagonal(int c, char player) {
        int countEqual1 = 0, countEqual2 = 0;

        for (int i = 0; i < grid_size; i++) {
            if (my_board[i][i] == player) {
                countEqual1++;
            }
        }

        for (int j = 0; j < grid_size; j++) {
            if (my_board[j][grid_size - 1 - j] == player) {
                countEqual2++;
            }
        }

        if (countEqual1 == grid_size || countEqual2 == grid_size) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        //Inflate the menu - this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_board, menu);
        return true;
    }

    //handle action bar item clicks here - the action bar will automatically handle clicks on the home/up button, as long as you specify parent activity in AndroidManifest.xml
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}