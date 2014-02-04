package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
	
	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";
	private static final String KEY_IS_CHEATER = "is_cheater";

	private Button mTrueButton;
	private Button mFalseButton;
	
	private ImageButton mNextButton;
	private ImageButton mPrevButton;
	private Button mCheatButton;
	
	private TextView mQuestionTextView;
	
	private boolean mIsCheater;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
		new TrueFalse(R.string.question_oceans, true, false),
		new TrueFalse(R.string.question_mideast, false, false),
		new TrueFalse(R.string.question_africa, false, false),
		new TrueFalse(R.string.question_americas, true, false),
		new TrueFalse(R.string.question_asia, true, false)
	};
	
	private int mCurrentIndex = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        
        // Check to see if we are actually just redrawing after a state change
        if(savedInstanceState != null) {
        	mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        	mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER, false);
        	
        	//Set Cheater status forever on this question
        	mQuestionBank[mCurrentIndex].setIsCheater(mIsCheater);
        }
        
        // Link question from bank to view
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        updateQuestion();
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();
		        
		        // New question means we need to reset cheater status, unless they've already cheated
		        mIsCheater = mQuestionBank[mCurrentIndex].isCheater();
			}
		});
        
        // Link to layout True Button and listen for click
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});
        
        // Link to layout False Button and listen for click
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v){
        		checkAnswer(false);
        	}
        });
        
        // Link Next Button to view and listen for click
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();

				// New question means we need to reset cheater status, unless they've already cheated
		        mIsCheater = mQuestionBank[mCurrentIndex].isCheater();
			}
		});
        
        // Link Previous Button to view and listen for click
        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length;
				updateQuestion();
		        
				// New question means we need to reset cheater status, unless they've already cheated
		        mIsCheater = mQuestionBank[mCurrentIndex].isCheater();
			}
		});
        
        // Link Cheat Button to view and listen for click
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				
				startActivityForResult(i, 0);
			}
		});
        
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	super.onSaveInstanceState(savedInstanceState);
    	Log.i(TAG, "onSaveInstanceState");
    	savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    	savedInstanceState.putBoolean(KEY_IS_CHEATER, mIsCheater);
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	//Log.d(TAG, "onStart() called");
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	//Log.d(TAG, "onPause() called");
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	//Log.d(TAG, "onResume() called");
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	//Log.d(TAG, "onStop() called");
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	//Log.d(TAG, "onDestroy() called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }
    
    
    // Check if they cheated
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if( data == null) {
    		return;
    	}
    	mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }
    
    // Update Next Question
    private void updateQuestion() {
    	//Log.d(TAG, "Updating question text for quetion #" + mCurrentIndex, new Exception());
    	int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }
    
    // Check answer
    private void checkAnswer(boolean userPressedTrue){
    	
    	int messageResId = 0;
    	boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
    	
    	if(mIsCheater) {
    		messageResId = R.string.judgement_toast;
    	} else {
	    	if(userPressedTrue == answerIsTrue){
	    		messageResId = R.string.correct_toast;
	    	}
	    	else {
	    		messageResId = R.string.incorrect_toast;
	    	}
    	}
    	
    	Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

}
