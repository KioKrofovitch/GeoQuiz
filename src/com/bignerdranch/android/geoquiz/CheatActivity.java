package com.bignerdranch.android.geoquiz;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

	private static final String TAG = "CheatActivity";
	
	public static final String EXTRA_ANSWER_IS_TRUE = 
			"com.bignerdranch.android.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN =
			"com.bignerdranch.android.geoquiz.answer_shown";
	
	private static final String KEY_IS_CHEATER = "is_cheater";
	
	private boolean mAnswerIsTrue;
	private boolean mIsCheater;
	
	private TextView mAnswerTextView;
	private Button mShowAnswer;
	private TextView mApiLevel;
	
	private void setAnswerShownResult(boolean isAnswerShown){
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		Log.d(TAG, "setAnswerShownResult: isAnswerShown:" + isAnswerShown);
		setResult(RESULT_OK, data);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		// Set API Level for Ch. 6 Challenge
		mApiLevel = (TextView) findViewById(R.id.apiLevelTextView);
		mApiLevel.setText( getString(R.string.api_level) + Build.VERSION.SDK_INT);
	
		mIsCheater = false;
		
        // Check to see if we are actually just redrawing after a state change
        if(savedInstanceState != null) {
        	mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER);
        	setAnswerShownResult(true);
        } else {
        	// Answer won't be shown until user clicks the button
     		setAnswerShownResult(false);
        }
		
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		
		// Link Text View
		mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
		
		
		
		// Link Show Answer Button and listen for click
		mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
		mShowAnswer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mAnswerIsTrue){
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}
				setAnswerShownResult(true);
				mIsCheater = true;
			}
		});
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		super.onSaveInstanceState(savedInstanceState);
		Log.i(TAG, "onSaveInstanceState");
		
		savedInstanceState.putBoolean(KEY_IS_CHEATER, mIsCheater);
	}
	
}
