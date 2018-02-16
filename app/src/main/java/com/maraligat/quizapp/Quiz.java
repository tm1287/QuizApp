package com.maraligat.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Quiz extends AppCompatActivity {
    private static ImageView iv_picture;
    private static RadioGroup rg_choices;
    private static RadioButton rb_selected;

    private static RadioButton rb_choiceA;
    private static RadioButton rb_choiceB;
    private static RadioButton rb_choiceC;
    private static TextView tv_question;

    private static Button b_submit;

    private int currentQuestionIndex;
    private ArrayList<Question> questions;



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

        currentQuestionIndex = 0;
        questions = new ArrayList<Question>();

        questions.add(new Question(0, "Which president is shown here seated next to Churchill?",
                "Eisenhower", "F.D. Roosevelt", "Nixon", "b"));
        questions.add(new Question(0, "Who is the Watergate president?",
                "Carter", "Reagan", "Nixon", "c"));
        questions.add(new Question(0, "Which president shown here help normalize relations with China?",
                "Carter", "F.D. Roosevelt", "Nixon", "a"));
        questions.add(new Question(0, "Which president approved the use of the A-bomb?",
                "Truman", "F.D. Roosevelt", "Kennedy", "a"));

        this.displayQuestion(currentQuestionIndex);

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
                    Toast.makeText(getApplicationContext(), "Right!", Toast.LENGTH_SHORT).show();
                    advance();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void displayQuestion(int index){
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
