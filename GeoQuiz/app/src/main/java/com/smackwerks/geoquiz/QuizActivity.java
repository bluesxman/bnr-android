package com.smackwerks.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = QuizActivity.class.getSimpleName();
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEATS = "cheats";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button trueButton;
    private Button falseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private boolean[] mIsCheater;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),};

    private boolean[] answered = new boolean[mQuestionBank.length];
    private float score = 0;

    private int mCurrentIndex = 0;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState()");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBooleanArray(KEY_CHEATS, mIsCheater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mIsCheater = savedInstanceState.getBooleanArray(KEY_CHEATS);
        }
        else {
            mIsCheater = new boolean[mQuestionBank.length];
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        trueButton = (Button) findViewById(R.id.true_button);
        falseButton = (Button) findViewById(R.id.false_button);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFeedback(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFeedback(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(CheatActivity.newIntent(QuizActivity.this, mQuestionBank[mCurrentIndex].isAnswerTrue()), REQUEST_CODE_CHEAT);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater[mCurrentIndex] = CheatActivity.wasAnswerShown(data);
        }
    }


    private void showFeedback(boolean answer) {
        Toast feedback = Toast.makeText(QuizActivity.this, getFeedback(answer), Toast.LENGTH_SHORT);
        feedback.setGravity(Gravity.TOP, 0, 16);
        feedback.show();
        setAnswered(true);
        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextId();
        mQuestionTextView.setText(question);

        if (answered[mCurrentIndex]) {
            trueButton.setEnabled(false);
            falseButton.setEnabled(false);
        } else {
            trueButton.setEnabled(true);
            falseButton.setEnabled(true);
        }
    }

    private void setAnswered(boolean isAnswered) {
        answered[mCurrentIndex] = isAnswered;

        boolean allAnswered = true;
        for (int i = 0; allAnswered && i < answered.length; i++) {
            allAnswered = allAnswered && answered[i];
        }

        if (allAnswered) {
            displayScore();
        }
    }

    private void displayScore() {
        Toast feedback = Toast.makeText(
                QuizActivity.this,
                getString(R.string.score_toast, 100 * score / mQuestionBank.length),
                Toast.LENGTH_LONG);
        feedback.setGravity(Gravity.TOP, 0, 16);
        feedback.show();
    }

    private int getFeedback(boolean answer) {
        if(mIsCheater[mCurrentIndex]) {
            return R.string.judgment_toast;
        }
        else {
            if (answer == mQuestionBank[mCurrentIndex].isAnswerTrue()) {
                score++; // TODO: avoid side effect nightmare
                return R.string.correct_toast;
            } else {
                return R.string.wrong_toast;
            }
        }
    }
}
