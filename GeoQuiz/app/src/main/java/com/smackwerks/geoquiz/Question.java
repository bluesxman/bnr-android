package com.smackwerks.geoquiz;

/**
 * Created by jonne_000 on 3/18/2017.
 */

public class Question {
    private int mTextId;
    private boolean mAnswerTrue;

    public Question(int textId, boolean answerTrue) {
        this.mTextId = textId;
        this.mAnswerTrue = answerTrue;
    }

    public int getTextId() {
        return mTextId;
    }

    public void setTextId(int textId) {
        mTextId = textId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
