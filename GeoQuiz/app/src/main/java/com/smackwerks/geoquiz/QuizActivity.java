package com.smackwerks.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        trueButton = (Button) findViewById(R.id.true_button);
        falseButton = (Button) findViewById(R.id.false_button);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast feedback = Toast.makeText( QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT);
                feedback.setGravity(Gravity.TOP, 0, 16);
                feedback.show();
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast feedback = Toast.makeText( QuizActivity.this, R.string.wrong_toast, Toast.LENGTH_SHORT);
                feedback.setGravity(Gravity.TOP, 0, 16);
                feedback.show();
            }
        });
    }
}
