package com.bignerdranch.android.geoquiz;

public class TrueFalse {
	
	private int mQuestion;
	private boolean mTrueQuestion;
	private boolean mIsCheater;
	
	public TrueFalse(int question, boolean trueQuestion, boolean isCheater){
		mQuestion = question;
		mTrueQuestion = trueQuestion;
	}

	public int getQuestion() {
		return mQuestion;
	}

	public void setQuestion(int question) {
		mQuestion = question;
	}

	public boolean isTrueQuestion() {
		return mTrueQuestion;
	}

	public void setTrueQuestion(boolean trueQuestion) {
		mTrueQuestion = trueQuestion;
	}
	
}
