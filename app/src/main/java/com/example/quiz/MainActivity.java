package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
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

    //Zmienne używane w timerze
    private static final long START_TIME_IN_MILLIS = 3000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    ImageView startImageView;
    ImageView gameImageView;
    ImageView clubLogosImg;
    ImageView gameOverImageView;
    ImageView preGameImageView;
    Button preGameButton;
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
    TextView gameFinalScore;
    TextView gameFinalTime;
    TextView timerTextView;

    Chronometer timer;
    Handler handler;
    long tMilliSec, tStart, tBuff, tUpdate = 0L;
    int sec, min, milliSec;

    Random random;

    Button aboutButton;
    Button backButton1;
    Button backButton2;
    TextView aboutTextView;

    boolean gameStarted;
    boolean isGameEnded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startImageView = findViewById(R.id.startImageView);
        gameImageView = findViewById(R.id.gameImageView);
        startButton = findViewById(R.id.startButton);
        gameOverImageView = findViewById(R.id.gameOverImageView);
        preGameImageView = findViewById(R.id.preGameImageView);
        preGameButton = findViewById(R.id.preGameButton);
        answersLayout = findViewById(R.id.answersLayout);
        clubLogosImg = findViewById(R.id.clubLogosImg);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        scoreTextView = findViewById(R.id.scoreTextView);
        preGameText = findViewById(R.id.preGameText);
        gameFinalScore = findViewById(R.id.gameFinalScore);
        gameFinalTime = findViewById(R.id.gameFinalTime);
        timerTextView = findViewById(R.id.timerTextView);


        timer = findViewById(R.id.timer);
        handler = new Handler();
        timer.setVisibility(View.INVISIBLE);

        usedIndexes = new ArrayList<>();
        random = new Random();
        answers = new String[4];

        aboutButton = findViewById(R.id.aboutButton);
        backButton1 = findViewById(R.id.backButton1);
        backButton2 = findViewById(R.id.backButton2);
        aboutTextView = findViewById(R.id.aboutTextView);


        gameStarted = false;



        appOpened();

    }


    public void appOpened (){

        startImageView.setVisibility(View.VISIBLE);
        gameImageView.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        preGameButton.setVisibility(View.VISIBLE);
        gameOverImageView.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);
        answersLayout.setVisibility(View.INVISIBLE);
        aboutButton.setVisibility(View.VISIBLE);
        backButton1.setVisibility(View.INVISIBLE);
        clubLogosImg.setVisibility(View.INVISIBLE);


        aboutTextView.setText("");
        timerTextView.setText("");
        gameStarted = false;
        isGameEnded = false;

        usedIndexes.clear();
        numberOfQuestions =0;
        score = 0;

        timer.stop();
        timer.setVisibility(View.INVISIBLE);

    }


    public void backToMainMenu (View view){

        appOpened();
        timer.stop();

        timer.setVisibility(View.INVISIBLE);
        clubLogosImg.setVisibility(View.INVISIBLE);
        backButton1.setVisibility(View.INVISIBLE);
        backButton2.setVisibility(View.INVISIBLE);
        gameFinalScore.setVisibility(View.INVISIBLE);
        gameFinalScore.setText("");
        gameFinalTime.setVisibility(View.INVISIBLE);
        gameFinalTime.setText("");

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


    public void about(View view) {


        if (gameStarted == false) {
            backButton1.setVisibility(View.VISIBLE);
            aboutTextView.setText("Twórca: Filip Żelaskowski " +
                    "\n               gd40629");
            preGameButton.setVisibility(View.INVISIBLE);
        }

    }

    public void backAbout(View view) {

        if (gameStarted == false) {
            backButton1.setVisibility(View.INVISIBLE);
            preGameButton.setVisibility(View.VISIBLE);
            aboutTextView.setText("");
        }


        backButton2.setVisibility(View.INVISIBLE);
        preGameButton.setVisibility(View.VISIBLE);
        aboutButton.setVisibility(View.VISIBLE);
        startImageView.setVisibility(View.VISIBLE);
        backButton1.setVisibility(View.INVISIBLE);
        preGameText.setText("");
        preGameImageView.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);



    }

    public void startTimer() {

        timer.setText("00:00:00");
        timer.setBase(SystemClock.elapsedRealtime());
        tStart = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
        timer.start();
    }

    public void preGame(View view) {

        preGameImageView.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.VISIBLE);
        backButton1.setVisibility(View.VISIBLE);
        backButton2.setVisibility(View.INVISIBLE);
        preGameButton.setVisibility(View.INVISIBLE);
        aboutButton.setVisibility(View.INVISIBLE);
        preGameText.setText

                ("       Witaj w quizie piłkarskim! " +
                        "\n\n\n\n        Twoim zadaniem będzie " +
                        "\n  odgadnięcie 10 herbów klubów " +
                        "\n   piłkarskich w jak najkrótszym" +
                        "\n                       czasie!");


    }

    public void preGameTimer(View view) {


        new CountDownTimer(mTimeLeftInMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                long seconds = (int) (mTimeLeftInMillis / 1000);
                timerTextView.setText(String.valueOf(seconds));
                startButton.setVisibility(View.INVISIBLE);
                preGameText.setText("");
                backButton1.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onFinish() {
                timerTextView.setText("");
                gameStarted = true;
                startGame();


            }
        }.start();

    }


    public void startGame() {

        preGameText.setText("");
        startImageView.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        preGameButton.setVisibility(View.INVISIBLE);
        preGameImageView.setVisibility(View.INVISIBLE);
        gameImageView.setVisibility(View.VISIBLE);
        clubLogosImg.setVisibility(View.VISIBLE);
        answersLayout.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        gameOverImageView.setVisibility(View.INVISIBLE);
        gameFinalScore.setText("");
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

        if (Arrays.asList(answers).contains(wrongAnswerClubName) == true) {
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

    public void checkEndgameConditions() {


        isGameEnded = usedIndexes.size() == clubLogosTable.length || numberOfQuestions == 10;

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
            timer.setVisibility(View.INVISIBLE);
            backButton2.setVisibility(View.VISIBLE);
            gameFinalScore.setVisibility(View.VISIBLE);
            gameFinalScore.setText("    KONIEC GRY!" +
                    "\n\n\n Twój wynik: " + scoreTextView.getText());
            gameFinalTime.setVisibility(View.VISIBLE);
            gameFinalTime.setText(" Twój czas: " + czas);



        }
    }

}


























