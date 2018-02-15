package com.maraligat.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

        questions.add(new Question(R.mipmap.rooschurch, "Which president is shown here seated next to Churchill?",
                "Eisenhower", "F.D. Roosevelt", "Nixon", 'b'));
        questions.add(new Question(R.mipmap.nixon, "Who is the Watergate president?",
                "Carter", "Reagan", "Nixon", 'c'));
        questions.add(new Question(R.mipmap.carterchina, "Which president shown here help normalize relations with China?",
                "Carter", "F.D. Roosevelt", "Nixon", 'a'));
        questions.add(new Question(R.mipmap.atomic, "Which president approved the use of the A-bomb?",
                "Truman", "F.D. Roosevelt", "Kennedy", 'a'));

    }
}
