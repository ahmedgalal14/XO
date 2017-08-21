package com.example.ahmedgalal.xo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 0 => O & 1 => X
    int activePlayer = 0;

    boolean gameIsActive = true;

    // 2 => unPlayed
    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6},
                                {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void onDrop (View view){
        ImageView X = (ImageView) view;
        int tapped = Integer.parseInt(X.getTag().toString());


        if (gameState[tapped] == 2 && gameIsActive == true) {

            gameState[tapped] = activePlayer;
            X.setTranslationY(-1000f);

            if (activePlayer == 0) {
                X.setImageResource(R.drawable.oo);
                activePlayer = 1;
            } else {
                X.setImageResource(R.drawable.xx);
                activePlayer = 0;
            }

            X.animate().translationYBy(1000f).rotation(360).setDuration(300);

            for (int[] winningPosition :winningPositions)
            {
                //someone has won
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2)
                {
                    gameIsActive = false;

                    String winner = "X";
                    if (gameState[winningPosition[0]] == 0)
                    {
                        winner = "O";
                    }

                    TextView winningMessage = (TextView) findViewById(R.id.winMessage);
                    winningMessage.setText(winner + " has WON!");
                    LinearLayout Layout = (LinearLayout) findViewById(R.id.playagainLayout);
                    Layout.setVisibility(view.VISIBLE);
                }
                else
                {
                    //There's a draw
                    boolean gameIsOver = true;
                    for (int counterState : gameState)
                    {

                        if (counterState == 2)
                        {
                            gameIsOver = false;
                        }
                    }

                    if (gameIsOver)
                    {
                        TextView winningMessage = (TextView) findViewById(R.id.winMessage);
                        winningMessage.setText("It's a draw");
                        LinearLayout Layout = (LinearLayout) findViewById(R.id.playagainLayout);
                        Layout.setVisibility(view.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain (View view) {
        //Reset gameActive to true
        gameIsActive = true;

        //Hide the linearLayout
        LinearLayout Layout = (LinearLayout) findViewById(R.id.playagainLayout);
        Layout.setVisibility(view.INVISIBLE);

        //Reset activePlyer
        activePlayer = 0;

        //Reset gameState Array
        for (int i = 0 ; i < gameState.length ; i++)
        {
            gameState[i] = 2;
        }

        //Reset all imagesRsc in gridLayout to empty
        GridLayout gLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < gLayout.getChildCount(); i++)
        {
            ((ImageView) gLayout.getChildAt(i)).setImageResource(0);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
