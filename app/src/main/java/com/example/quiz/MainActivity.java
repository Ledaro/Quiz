package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quiz.R;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView startImageView;
    ImageView gameImageView;
    ImageView clubLogosImg;
    Button startButton;
    Button losujklub;

    int currentImage;
    int [] clubLogosTable = {R.drawable.ajax, R.drawable.atalanta,R.drawable.atletico,R.drawable.barcelona,R.drawable.bayer,
            R.drawable.bayern,R.drawable.benfica,R.drawable.borussia,R.drawable.chelsea,R.drawable.clubbrugge,R.drawable.crvenazvezda,
            R.drawable.dinamozagreb,R.drawable.galatasaray,R.drawable.genk,R.drawable.inter,R.drawable.juventus,R.drawable.liverpool,
            R.drawable.lokomotiv,R.drawable.machestercity,R.drawable.napoli,R.drawable.ol,R.drawable.olympiacos,R.drawable.osclille,
            R.drawable.psg,R.drawable.rblipsk,R.drawable.rbsalzburg,R.drawable.realmadryt,R.drawable.shakhtar,R.drawable.slavia,
            R.drawable.tottenham,R.drawable.valencia, R.drawable.zenit};

    String currentName;
    String [] clubNamesTable = {"Ajax Amsterdam" ,"Atalanta Bergamo","Atletico Madryt", "FC Barcelona","Bayer Leverkusen",
            "Bayern Monachium","Benfica Lizbona","Borussia Dortmund","Chelsea Londyn","Club Brugge","Crvena Zvezda",
            "Dinamo Zagreb", "Galatasaray", "Genk", "Inter Mediolan", "Juventus Turyn", "Liverpool FC",
            "Lokomotiv Moskwa","Manchester City","Napoli","Olympic Lyon", "Olympiacos Pireus","OSC Lille",
            "PSG","RB Lipsk",  "RB Salzburg","Real Madryt","Shakhtar Donieck","Slavia Praga","Tottenham Spurs","Valencia","Zenit"};

    androidx.gridlayout.widget.GridLayout answersLayout;

    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;
    ArrayList<String> answers = new ArrayList<>();
    Button button0;
    Button button1;
    Button button2;
    Button button3;

    TextView resultTextView;
    TextView scoreTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startImageView = findViewById(R.id.startImageView);
        gameImageView = findViewById(R.id.gameImageView);
        startButton = findViewById(R.id.startButton);
        clubLogosImg = findViewById(R.id.clubLogosImg);
        losujklub = findViewById(R.id.losujklub);
        answersLayout = findViewById(R.id.answersLayout);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        scoreTextView = findViewById(R.id.scoreTextView);
       // resultTextView = findViewById(R.id.resultTextView);




        startImageView.setVisibility(View.VISIBLE);
        gameImageView.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);
        losujklub.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);
        //pickClub();


        answersLayout.setVisibility(View.INVISIBLE);



    }


    public void startGame (View view) {

        startImageView.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        gameImageView.setVisibility(View.VISIBLE);
     //   losujklub.setVisibility(View.VISIBLE);
        answersLayout.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        scoreTextView.setText("0/0");
        newQuestion();



    }


    public void newQuestion() {

        Random random = new Random();
        int randomClub = random.nextInt(31);


        currentImage = randomClub;
        clubLogosImg.setImageResource(clubLogosTable[currentImage]);
        currentName = clubNamesTable[currentImage];
        Log.i("Aktualny klub: ", currentName);
        Log.i("Aktualna liczba: ", String.valueOf(currentImage));




        locationOfCorrectAnswer = random.nextInt(4);
        answers.clear();


        for (int i = 0; i<4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(currentName);
            } else {
                int wrongAnswer = random.nextInt(31);
                String wrongAnswerClubName = clubNamesTable[wrongAnswer];
                while (wrongAnswer == randomClub) {
                    wrongAnswer = random.nextInt(31);
                    wrongAnswerClubName = clubNamesTable[wrongAnswer];
                }
                answers.add(wrongAnswerClubName);
            }

        }

        button0.setText((answers.get(0)));
        button1.setText((answers.get(1)));
        button2.setText((answers.get(2)));
        button3.setText((answers.get(3)));
    }

    public void chooseAnswer(View view) {
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            //resultTextView.setText("Dobry wybór");
            score++;
        } else {
            //resultTextView.setText("Zły wybór");
        }
        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        newQuestion();
    }







}

























