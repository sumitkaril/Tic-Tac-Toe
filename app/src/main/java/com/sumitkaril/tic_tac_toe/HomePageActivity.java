package com.sumitkaril.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Random;

public class HomePageActivity extends AppCompatActivity {

    private RelativeLayout usersLayout, enterNameLayout,gameLayout;
    private Button twoPlayerBtn, singlePlayerBtn;
    ImageView img0,img1, img2, img3, img4, img5, img6, img7, img8;
    int computerTapped;
    int delayForComputer=500;

    int noOfPlacesWhereImageIsSet=0;
    int codeRunningStage=0;

    String firstPlayer="";
    String secondPlayer="";
    LinearLayout firstPlayerNameLinearLayout, secondPlayerNameLinearLayout;
    private Button skipBtn, nextBtn;
    TextView firstPlayerTextView, secondPlayerTextView;
    boolean doubleBackToExitPressedOnce = false;
    int noOfTimesGamePlayed=0;
    int noOfPlayers;

    Button playAgainButton;
    TextView winnerTextView;


    int activePlayer=0;
    int gameWonYellow = 0;
    int gameWonRed = 0;
    int[] gameState ={2,2,2,2,2,2,2,2,2};
    int[][] winningPositions ={{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    boolean gameActive = true;
    int tappedCounter;
    ImageView counter;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    EditText firstPlayerName,secondPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        googleAds();
        usersLayout = findViewById(R.id.usersRelativeLayout);
        enterNameLayout = findViewById(R.id.player_name_relative_layout);
        gameLayout = findViewById(R.id.gameRelativeLayout);
        twoPlayerBtn = findViewById(R.id.two_user_btn);
        singlePlayerBtn = findViewById(R.id.one_user_btn);

        //finding image view ids
        img0 = findViewById(R.id.imageView1);
        img1 = findViewById(R.id.imageView2);
        img2 = findViewById(R.id.imageView3);
        img3 = findViewById(R.id.imageView4);
        img4 = findViewById(R.id.imageView5);
        img5 = findViewById(R.id.imageView6);
        img6 = findViewById(R.id.imageView7);
        img7 = findViewById(R.id.imageView8);
        img8 = findViewById(R.id.imageView9);


        firstPlayerName = findViewById(R.id.first_player_name);
        secondPlayerName = findViewById(R.id.second_player_name);
        firstPlayerNameLinearLayout = findViewById(R.id.first_player_linear_layout);
        secondPlayerNameLinearLayout = findViewById(R.id.second_player_linear_layout);

        skipBtn = findViewById(R.id.skip_to_game_layout_btn);
        nextBtn = findViewById(R.id.next_to_game_layout_btn);

        firstPlayerTextView = findViewById(R.id.first_player_name_text_view);
        secondPlayerTextView = findViewById(R.id.second_player_name_text_view);

        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        winnerTextView = (TextView) findViewById(R.id.winnerTextView);



        onClickListener();

    }

    private void googleAds() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1146202650959603/6357058009");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
    }

    public void dropIn(View view) {
        if (noOfPlayers ==2) {
            counter =(ImageView) view;
            tappedCounter = Integer.parseInt(counter.getTag().toString());
            twoPlayerArePlaying();
            }

        // when player will play with computer
        else if (noOfPlayers ==1){
            counter =(ImageView) view;
            tappedCounter = Integer.parseInt(counter.getTag().toString());
            onePlayerIsPlaying();

        }
    }

    private void onePlayerIsPlaying() {
        if (gameState[tappedCounter] == 2 && gameActive) {
            gameState[tappedCounter] = activePlayer;
            activePlayer = 1;
            counter.setTranslationY(-1500);
            counter.setImageResource(R.drawable.yellow);
            counter.animate().translationYBy(1500).rotation(720).setDuration(300);

            codeRunningStage=0;
            testOfWinner();

            while (codeRunningStage==0 || codeRunningStage==2 ||codeRunningStage==3
                    ||codeRunningStage==4 ||codeRunningStage==5 ||codeRunningStage==6){
            }
            if (codeRunningStage==1){
                codeRunningStage=2;
                turnOfComputer(1);
            }
        }


    }

    private void turnOfComputer(int check) {
        while (codeRunningStage==0 ||codeRunningStage==1||codeRunningStage==3
                ||codeRunningStage==4||codeRunningStage==5||codeRunningStage==6){
        }

        if (check ==1 )
        {
            if (noOfPlacesWhereImageIsSet==9)
            {
                TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                winnerTextView.setText("Draw");
                return;
            }

            codeRunningStage=3;
            computerTapped = tappedCounter;
//            call for the logic of computer
            logicForComputerTurn(computerTapped);

            while (codeRunningStage == 0||codeRunningStage==1||codeRunningStage==2
                    ||codeRunningStage==3||codeRunningStage==5||codeRunningStage==6) {
            }

            if (codeRunningStage==4)
            {
                if (gameState[computerTapped] == 2 && gameActive)
                {
                    gameState[computerTapped] = activePlayer;//activePlayer
                    codeRunningStage=5;
                    activePlayer=0;
                    settingImageByComputer();
                    while (codeRunningStage==0||codeRunningStage==1||codeRunningStage==2
                            ||codeRunningStage==3||codeRunningStage==4||codeRunningStage==5){

                    }
                    if (codeRunningStage==6){
                        codeRunningStage=0;
                        testOfWinner();
                    }
                }
            }
        }

        //when computer has its first turn
        else {
            Random rand = new Random();
            computerTapped = rand.nextInt(9);
            if (gameState[computerTapped] == 2 && gameActive) {
                gameState[computerTapped] = activePlayer;
                codeRunningStage=5;
                activePlayer=0;
                settingImageByComputer();
                while (codeRunningStage==0||codeRunningStage==1||codeRunningStage==2
                        ||codeRunningStage==3||codeRunningStage==4||codeRunningStage==5){

                }
                if (codeRunningStage==6){
                    codeRunningStage=0;
                    testOfWinner();
                }
            }

        }
    }

    private void twoPlayerArePlaying() {

        if (gameState[tappedCounter] == 2 && gameActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1500);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1500).rotation(720).setDuration(300);
            codeRunningStage=0;
            //check for winner
            testOfWinner();
        }
    }

    private void testOfWinner() {

        while (codeRunningStage==1||codeRunningStage==2||codeRunningStage==3||
                codeRunningStage==4||codeRunningStage==5||codeRunningStage==6){
        }

        noOfPlacesWhereImageIsSet++;
        String winner = "";
            Log.e("step counter", String.valueOf(noOfPlacesWhereImageIsSet));
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {

                    gameActive = false;

                    if (activePlayer == 1) {
                        winner = firstPlayer;
                        gameWonYellow = gameWonYellow + 1;

                    } else {
                        winner = secondPlayer;
                        gameWonRed = gameWonRed + 1;
                    }
                    noOfTimesGamePlayed =noOfTimesGamePlayed+1;

                    if (noOfTimesGamePlayed % 2 ==0){
                        activePlayer=0;
                    }else {
                        activePlayer=1;
                    }

                    Toast.makeText(this, firstPlayer+" won " + gameWonYellow + " times and "+secondPlayer+"  won "
                            + gameWonRed + " times", Toast.LENGTH_LONG).show();

                    String winnertest=winner;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                            TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

                            winnerTextView.setText(winnertest + " has Won!");

                            playAgainButton.setVisibility(View.VISIBLE);
                            winnerTextView.setVisibility(View.VISIBLE);
                            winnerTextView.setBackgroundResource(R.drawable.bouquet);

                        }
                    }, 800);


                }
            }

            if (noOfPlacesWhereImageIsSet==9 && winner.equals("")){
                playAgainButton.setVisibility(View.VISIBLE);
                winnerTextView.setText("Draw");
                noOfTimesGamePlayed =noOfTimesGamePlayed+1;
                winnerTextView.setBackground(null);
                winnerTextView.setVisibility(View.VISIBLE);
            }
            codeRunningStage=1;
    }

    private void settingImageByComputer() {
        while (codeRunningStage==0||codeRunningStage==1||codeRunningStage==2
                ||codeRunningStage==3||codeRunningStage==4||codeRunningStage==6){

        }
        if (codeRunningStage==5){
            if (computerTapped==0){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img0.setTranslationY(-1500);
                        activePlayer = 0;
                        img0.setImageResource(R.drawable.red);
                        img0.animate().translationYBy(1500).rotation(720).setDuration(300);

                    }
                }, delayForComputer);

            }else if (computerTapped==1){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img1.setTranslationY(-1500);
                        activePlayer = 0;
                        img1.setImageResource(R.drawable.red);
                        img1.animate().translationYBy(1500).rotation(720).setDuration(300);

                    }
                }, delayForComputer);


            }else  if (computerTapped==2){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img2.setTranslationY(-1500);
                        activePlayer = 0;
                        img2.setImageResource(R.drawable.red);
                        img2.animate().translationYBy(1500).rotation(720).setDuration(300);

                    }
                }, delayForComputer);

            }else  if (computerTapped==3){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img3.setTranslationY(-1500);
                        activePlayer = 0;
                        img3.setImageResource(R.drawable.red);
                        img3.animate().translationYBy(1500).rotation(720).setDuration(300);

                        codeRunningStage=6;
                    }
                }, delayForComputer);

            }else  if (computerTapped==4){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img4.setTranslationY(-1500);
                        activePlayer = 0;
                        img4.setImageResource(R.drawable.red);
                        img4.animate().translationYBy(1500).rotation(720).setDuration(300);

                        codeRunningStage=6;
                    }
                }, delayForComputer);
            }else  if (computerTapped==5){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img5.setTranslationY(-1500);
                        activePlayer = 0;
                        img5.setImageResource(R.drawable.red);
                        img5.animate().translationYBy(1500).rotation(720).setDuration(300);

                        codeRunningStage=6;
                    }
                }, delayForComputer);

            }else  if (computerTapped==6){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img6.setTranslationY(-1500);
                        activePlayer = 0;
                        img6.setImageResource(R.drawable.red);
                        img6.animate().translationYBy(1500).rotation(720).setDuration(300);

                        codeRunningStage=6;
                    }
                }, delayForComputer);

            }else  if (computerTapped==7){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img7.setTranslationY(-1500);
                        activePlayer = 0;
                        img7.setImageResource(R.drawable.red);
                        img7.animate().translationYBy(1500).rotation(720).setDuration(300);

                        codeRunningStage=6;
                    }
                }, delayForComputer);

            }else  if (computerTapped==8){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img8.setTranslationY(-1500);
                        activePlayer = 0;
                        img8.setImageResource(R.drawable.red);
                        img8.animate().translationYBy(1500).rotation(720).setDuration(300);

                        codeRunningStage=6;
                    }
                }, delayForComputer);
            }
            codeRunningStage=6;
        }
    }

    private void logicForComputerTurn(int computer){
        while (codeRunningStage == 0||codeRunningStage==1||codeRunningStage==2
                ||codeRunningStage==4||codeRunningStage==5||codeRunningStage==6) {
        }

        if (codeRunningStage==3){
            for (int[] checkingPositions : winningPositions) {
                if (gameState[checkingPositions[0]] == 1 && gameState[checkingPositions[1]] == 1 && gameState[checkingPositions[2]]==2 ) {
                    computerTapped =checkingPositions[2];
                    codeRunningStage=4;
                    return;
                }

                else if (gameState[checkingPositions[0]]==2 && gameState[checkingPositions[1]] == 1 && gameState[checkingPositions[2]] == 1 ){
                    computerTapped=checkingPositions[0];
                    codeRunningStage=4;
                    return;
                }

                else if (gameState[checkingPositions[0]] == 1 && gameState[checkingPositions[1]]==2 && gameState[checkingPositions[2]] == 1  ){
                    computerTapped=checkingPositions[1];
                    codeRunningStage=4;
                    return;
                }

                else if (gameState[checkingPositions[0]] == 0 && gameState[checkingPositions[1]] == 0 && gameState[checkingPositions[2]]==2 ) {
                    computerTapped =checkingPositions[2];
                    codeRunningStage=4;
                    return;
                }

                else if (gameState[checkingPositions[1]] == 0 && gameState[checkingPositions[2]] == 0 && gameState[checkingPositions[0]]==2 ){
                    computerTapped=checkingPositions[0];
                    codeRunningStage=4;
                    return;
                }

                else if (gameState[checkingPositions[0]] == 0 && gameState[checkingPositions[2]] == 0 && gameState[checkingPositions[1]]==2 ){
                    computerTapped=checkingPositions[1];
                    codeRunningStage=4;
                    return;
                }
            }

            if (codeRunningStage!=4){
                while (gameState[computer] == 0 || gameState[computer]==1){
                    Random rand = new Random();
                    computer = rand.nextInt(9);
                }
                codeRunningStage=4;
                computerTapped=computer;
                return;
            }
        }
    }

    public void reset(View view){

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout =(GridLayout) findViewById(R.id.gridLayout);
        for (int i=0; i<gridLayout.getChildCount();i++)
        {
            ImageView counter= (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        for (int i=0; i<gameState.length; i++)
        {
            gameState[i]=2;

        }

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }


        activePlayer=0;
        gameActive=true;
        noOfPlacesWhereImageIsSet=0;
        codeRunningStage=0;
        noOfTimesGamePlayed=0;
        gameWonYellow=0;
        gameWonRed=0;
        usersLayout.setVisibility(View.VISIBLE);
        enterNameLayout.setVisibility(View.GONE);
        gameLayout.setVisibility(View.GONE);

    }

    public void playAgain(View view){

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);
        noOfPlacesWhereImageIsSet=0;
        codeRunningStage=0;

        GridLayout gridLayout =  (GridLayout) findViewById(R.id.gridLayout);
        for (int i=0; i<gridLayout.getChildCount();i++)
        {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        for (int i=0; i<gameState.length; i++)
        {
            gameState[i]=2;
        }
        gameActive=true;


        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }


        if (noOfPlayers==1 && noOfTimesGamePlayed%2!=0){
            codeRunningStage=2;
            turnOfComputer(2);
        }


    }

    private void onClickListener() {

        twoPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noOfPlayers = 2;
                usersLayout.setVisibility(View.GONE);
                enterNameLayout.setVisibility(View.VISIBLE);
                secondPlayerNameLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        singlePlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noOfPlayers=1;
                usersLayout.setVisibility(View.GONE);
                enterNameLayout.setVisibility(View.VISIBLE);
                secondPlayerNameLinearLayout.setVisibility(View.GONE);
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNameLayout.setVisibility(View.GONE);
                gameLayout.setVisibility(View.VISIBLE);
                if (noOfPlayers==1){
                    firstPlayer ="Player1";
                    secondPlayer="Computer";
                    firstPlayerTextView.setText(firstPlayer);
                    secondPlayerTextView.setText(secondPlayer);
                }else {
                    firstPlayer ="player1";
                    secondPlayer = "Computer";
                    firstPlayerTextView.setText(firstPlayer);
                    secondPlayerTextView.setText(secondPlayer);
                }
                hideKeyboard(HomePageActivity.this);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNameLayout.setVisibility(View.GONE);
                hideKeyboard(HomePageActivity.this);
                gameLayout.setVisibility(View.VISIBLE);
                if (noOfPlayers==1){
                    firstPlayer =firstPlayerName.getText().toString();
                    secondPlayer="Computer";
                    firstPlayerTextView.setText(firstPlayer);
                    secondPlayerTextView.setText(secondPlayer);
                    if (firstPlayer.equals("")){
                        firstPlayer = "Player1";
                        firstPlayerTextView.setText(firstPlayer);
                    }
                }else if (noOfPlayers ==2){
                    firstPlayer =firstPlayerName.getText().toString();
                    secondPlayer = secondPlayerName.getText().toString();

                    if (firstPlayer.equals("") && secondPlayer.equals("")){
                        firstPlayer="Player1";
                        secondPlayer="Player2";
                    }else if (firstPlayer.equals("")){
                        firstPlayer ="Player1";
                    }else if (secondPlayer.equals("")){
                        secondPlayer="Player1";
                    }
                    firstPlayerTextView.setText(firstPlayer);
                    secondPlayerTextView.setText(secondPlayer);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}