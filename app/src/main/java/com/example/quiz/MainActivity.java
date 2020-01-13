package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView startImageView;
    ImageView gameImageView;
    ImageView clubLogosImg;
    ImageView gameOverImageView;
    ImageView preGameImageView;
    Button preGame;
    Button startButton;


    int currentImage;
    int[] clubLogosTable = {R.drawable.ajax, R.drawable.atalanta, R.drawable.atletico, R.drawable.barcelona, R.drawable.bayer,
            R.drawable.bayern, R.drawable.benfica, R.drawable.borussia, R.drawable.chelsea, R.drawable.clubbrugge, R.drawable.crvenazvezda,
            R.drawable.dinamozagreb, R.drawable.galatasaray, R.drawable.genk, R.drawable.inter, R.drawable.juventus, R.drawable.liverpool,
            R.drawable.lokomotiv, R.drawable.machestercity, R.drawable.napoli, R.drawable.ol, R.drawable.olympiacos, R.drawable.osclille,
            R.drawable.psg, R.drawable.rblipsk, R.drawable.rbsalzburg, R.drawable.realmadryt, R.drawable.shakhtar, R.drawable.slavia,
            R.drawable.tottenham, R.drawable.valencia, R.drawable.zenit};

    String currentName;
    String[] clubNamesTable = {"Ajax Amsterdam", "Atalanta Bergamo", "Atletico Madryt", "FC Barcelona", "Bayer Leverkusen",
            "Bayern Monachium", "Benfica Lizbona", "Borussia Dortmund", "Chelsea Londyn", "Club Brugge", "Crvena Zvezda",
            "Dinamo Zagreb", "Galatasaray", "Genk", "Inter Mediolan", "Juventus Turyn", "Liverpool FC",
            "Lokomotiv Moskwa", "Manchester City", "Napoli", "Olympic Lyon", "Olympiacos Pireus", "OSC Lille",
            "PSG", "RB Lipsk", "RB Salzburg", "Real Madryt", "Shakhtar Donieck", "Slavia Praga", "Tottenham Spurs", "Valencia", "Zenit"};

    ArrayList<Integer> usedIndexes;

    androidx.gridlayout.widget.GridLayout answersLayout;

    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;
    String[] answers;
    Button button0;
    Button button1;
    Button button2;
    Button button3;


    TextView scoreTextView;
    TextView preGameText;
    TextView gameScore;
    TextView gameTime;

    Chronometer timer;
    Handler handler;
    long tMilliSec, tStart, tBuff, tUpdate = 0L;
    int sec, min, milliSec;

    Random random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startImageView = findViewById(R.id.startImageView);
        gameImageView = findViewById(R.id.gameImageView);
        startButton = findViewById(R.id.startButton);
        gameOverImageView = findViewById(R.id.gameOverImageView);
        clubLogosImg = findViewById(R.id.clubLogosImg);
        preGameImageView = findViewById(R.id.preGameImageView);
        preGame = findViewById(R.id.preGameButton);
        answersLayout = findViewById(R.id.answersLayout);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        scoreTextView = findViewById(R.id.scoreTextView);
        preGameText = findViewById(R.id.preGameText);
        gameScore = findViewById(R.id.gameScore);
        //gameTime = findViewById(R.id.gameTime);

        startImageView.setVisibility(View.VISIBLE);
        gameImageView.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        preGame.setVisibility(View.VISIBLE);
        gameOverImageView.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);
        gameScore.setVisibility(View.INVISIBLE);
//        gameTime.setVisibility(View.INVISIBLE);


        answersLayout.setVisibility(View.INVISIBLE);

        timer = findViewById(R.id.timer);
        handler = new Handler();
        timer.setVisibility(View.INVISIBLE);

        usedIndexes = new ArrayList<>();
        random = new Random();
        answers = new String[4];
    }


    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate / 1000);
            min = sec / 60;
            sec = sec % 60;
            milliSec = (int) (tUpdate % 100);
            timer.setText(String.format("%02d", min) + ":" + String.format("%02d", sec) + ":" +
                    String.format("%02d", milliSec));
            handler.postDelayed(this, 60);

        }
    };


    public void startTimer() {

        tStart = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
        timer.start();
    }



    public void preGame(View view){

        preGameImageView.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.VISIBLE);
        preGameText.setText

                ("       Witaj w quizie piłkarskim! " +
                        "\n\n\n\n        Twoim zadaniem będzie " +
                        "\n  odgadnięcie 10 herbów klubów " +
                        "\n   piłkarskich w jak najkrótszym" +
                        "\n                       czasie!");
    }


    public void startGame(View view) {

        preGameText.setText("");
        startImageView.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        preGame.setVisibility(View.INVISIBLE);
        preGameImageView.setVisibility(View.INVISIBLE);
        gameImageView.setVisibility(View.VISIBLE);
        clubLogosImg.setVisibility(View.VISIBLE);
        answersLayout.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        gameScore.setText("");
//        gameTime.setText("");
        scoreTextView.setText("0/0");
        timer.setVisibility(View.VISIBLE);
        startTimer();
        newQuestion();
    }


    int getClubIndex() {


        int randomClub = random.nextInt(31);

        if (usedIndexes.contains(randomClub)) {
            return getClubIndex();
        }
        usedIndexes.add(randomClub);

        return randomClub;
    }


    private String getWrongAnswerName() {
        int wrongAnswer = random.nextInt(31);
        String wrongAnswerClubName = clubNamesTable[wrongAnswer];

        if (Arrays.asList( answers).contains(wrongAnswerClubName) == true) {
            return getWrongAnswerName();
        }

        return wrongAnswerClubName;
    }


    public void newQuestion() {
        checkEndgameConditions();
        int randomClub = getClubIndex();
        currentImage = randomClub;

        clubLogosImg.setImageResource(clubLogosTable[currentImage]);
        currentName = clubNamesTable[currentImage];
        Log.i("Aktualny klub: ", currentName);
        Log.i("Aktualna liczba: ", String.valueOf(currentImage));


        locationOfCorrectAnswer = random.nextInt(4);
        answers = new String[4];
        answers[locationOfCorrectAnswer] = currentName;


        for (int i = 0; i < 4; i++) {

            if (i == locationOfCorrectAnswer) {
                continue;
            }

            String wrongAnswerClubName = getWrongAnswerName();
            answers[i] = wrongAnswerClubName;

        }

        button0.setText((answers[0]));
        button1.setText((answers[1]));
        button2.setText((answers[2]));
        button3.setText((answers[3]));


    }

    public void chooseAnswer(View view) {
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            score++;
        }

        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        newQuestion();

    }

    void checkEndgameConditions() {

        boolean isGameEnded = usedIndexes.size() == clubLogosTable.length || numberOfQuestions == 10;

        if (isGameEnded == true) {
            tBuff += tMilliSec;
            handler.removeCallbacks(runnable);
            timer.stop();
            String czas = timer.getText().toString();


            gameImageView.setVisibility(View.INVISIBLE);
            clubLogosImg.setVisibility(View.INVISIBLE);
            answersLayout.setVisibility(View.INVISIBLE);
            scoreTextView.setVisibility(View.INVISIBLE);
            gameOverImageView.setVisibility(View.VISIBLE);
            gameScore.setText("Score");


            //gameTime.setText("Time");
         //   gameStatistics();

            Log.i("Czas", czas);

        }
    }

//    void gameStatistics(){
//
//        gameScore.setVisibility(View.VISIBLE);
//        gameTime.setVisibility(View.VISIBLE);
//
//
//    }


}


























