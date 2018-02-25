package com.maraligat.quizapp;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class Quiz extends AppCompatActivity {
    private static ImageView iv_picture;
    private static RadioGroup rg_choices;
    private static RadioButton rb_selected;

    private static RadioButton rb_choiceA;
    private static RadioButton rb_choiceB;
    private static RadioButton rb_choiceC;

    private static TextView tv_question;
    private static TextView tv_score;

    private static Button b_submit;
    private static Button b_search;

    private static EditText et_search;

    private int currentQuestionIndex;
    private ArrayList<Question> questions;

    public int score = 0;

    private static TextToSpeech TTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        this.initialize();
    }

    private void initialize(){
        iv_picture = (ImageView)findViewById(R.id.picture_iv);
        rg_choices = (RadioGroup)findViewById(R.id.choices_rg);
        rb_choiceA = (RadioButton)findViewById(R.id.a_rb);
        rb_choiceB = (RadioButton)findViewById(R.id.b_rb);
        rb_choiceC = (RadioButton)findViewById(R.id.c_rb);
        tv_question = (TextView)findViewById(R.id.question_tv);
        b_submit = (Button)findViewById(R.id.submit_b);
        b_search = (Button)findViewById(R.id.search_b);
        tv_score = (TextView)findViewById(R.id.score_tv);
        et_search = (EditText)findViewById(R.id.search_et);

        currentQuestionIndex = 0;
        questions = new ArrayList<Question>();

        questions.add(new Question(R.mipmap.roos, "Which president is shown here seated next to Churchill?",
                "Eisenhower", "F.D. Roosevelt", "Nixon", "b"));
        questions.add(new Question(R.mipmap.nixon, "Who is the Watergate president?",
                "Carter", "Reagan", "Nixon", "c"));
        questions.add(new Question(R.mipmap.carter, "Which president shown here help normalize relations with China?",
                "Carter", "F.D. Roosevelt", "Nixon", "a"));
        questions.add(new Question(R.mipmap.atomic, "Which president approved the use of the A-bomb?",
                "Truman", "F.D. Roosevelt", "Kennedy", "a"));

        this.displayQuestion(currentQuestionIndex);

        tv_score.setText("Score: " + score);

        b_submit.setOnClickListener(new View.OnClickListener() {

            public boolean answerIsRight(){
                String answer = "x";
                int id = rg_choices.getCheckedRadioButtonId();
                rb_selected = (RadioButton)findViewById(id);
                if(rb_selected == rb_choiceA) answer = "a";
                if(rb_selected == rb_choiceB) answer = "b";
                if(rb_selected == rb_choiceC) answer = "c";
                return questions.get(currentQuestionIndex).isCorrectAnswer(answer);
            }

            @Override
            public void onClick(View view) {
                if(this.answerIsRight()){
                    TTS.speak("Right", TextToSpeech.QUEUE_FLUSH,null);
                    Toast.makeText(getApplicationContext(), "Right!", Toast.LENGTH_SHORT).show();
                    advance();
                    if (!questions.get(currentQuestionIndex).isCreditAlreadyGiven()){
                        score += 1;
                        questions.get(currentQuestionIndex).setCreditAlreadyGiven(true);
                        tv_score.setText("Score: " + score);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                    TTS.speak("Wrong", TextToSpeech.QUEUE_FLUSH,null);
                }
            }
        });

        b_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        TTS = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    TTS.setLanguage(Locale.ENGLISH);
                }
            }
        });

    }


    private void search(){
        String search_text = et_search.getText().toString();
        for(int i = 0; i < questions.size(); ++i){
            System.out.println(search_text);
            if(questions.get(i).getQuestionText().toString().toLowerCase().contains(search_text.toLowerCase())){
                currentQuestionIndex = i;
                break;
            }
            else if(questions.get(i).getChoiceA().toString().toLowerCase().contains(search_text.toLowerCase())){
                currentQuestionIndex = i;
                break;
            }
            else if(questions.get(i).getChoiceB().toString().toLowerCase().contains(search_text.toLowerCase())){
                currentQuestionIndex = i;
                break;
            }
            else if(questions.get(i).getChoiceC().toString().toLowerCase().contains(search_text.toLowerCase())){
                currentQuestionIndex = i;
                break;
            }
        }
        displayQuestion(currentQuestionIndex);
    }
    private void displayQuestion(int index){
        iv_picture.setImageResource(questions.get(currentQuestionIndex).getPictureID());
        tv_question.setText(questions.get(currentQuestionIndex).getQuestionText());
        rb_choiceA.setText(questions.get(currentQuestionIndex).getChoiceA());
        rb_choiceB.setText(questions.get(currentQuestionIndex).getChoiceB());
        rb_choiceC.setText(questions.get(currentQuestionIndex).getChoiceC());
        rg_choices.clearCheck();
    }

    private void advance(){
        currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();
        displayQuestion(currentQuestionIndex);
    }

}
