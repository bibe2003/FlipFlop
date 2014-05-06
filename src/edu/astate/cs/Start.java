package edu.astate.cs;

import edu.astate.cs.TimeBomb.MyCountDownTimer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class Start extends Activity {
	private boolean continueMusic = false;
	// private boolean flag = true;
	AnimationDrawable hourglassAnimation;
	private CountDownTimer timeBombStartCountDown;
	public TextView goText;
	private final long startTime = 15 * 1000; // measure flips for 15 seconds
	private final long interval = 1 * 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		/*ImageView hourglassImage = (ImageView) findViewById(R.id.animation);
		hourglassImage.setBackgroundResource(R.drawable.hourglass);
		hourglassAnimation = (AnimationDrawable)hourglassImage.getBackground();
		hourglassAnimation.start();*/

		// use to set up countdown timer
		goText = (TextView) this.findViewById(R.id.goText);
		timeBombStartCountDown = new MyCountDownTimer(startTime,interval);
		goText.setText("GO!");

		timeBombStartCountDown.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		if(Home.mIsBound == true)
		{
			continueMusic = false;
	    	Home.mServ.resumeMusic();
		}

	}

	@Override
    public void onPause ()
    {
		super.onPause();
    	if(!continueMusic)
    	{
    		Home.mServ.pauseMusic();
    	}
    }
    
    @Override
    protected void onStop ()
    {
    	super.onStop();
    	if(!continueMusic)
    	{
    		Home.mServ.pauseMusic();
    	}
    }
    
    @Override
    protected void onRestart () 
    {
    	super.onRestart();
    	
    	if(Home.mIsBound == true)
    	{
    		continueMusic = false;
        	Home.mServ.resumeMusic();
    	}
    	
    }
    
	/*public boolean onTouchEvent(MotionEvent event) {
		  if (event.getAction() == MotionEvent.ACTION_DOWN) {
		    hourglassAnimation.start();
		    return true;
		  }
		  return super.onTouchEvent(event);
		}*/
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {

	            Intent Act2Intent = new Intent(this, PlayOptions.class);
	            if (Home.mIsBound)
	    			continueMusic = true;
	            else
	            	continueMusic = false;
	            Act2Intent.putExtra("boolean", continueMusic);
	            startActivity(Act2Intent);  

	            finish();
	            return true;
	    }
	    return false;
	}


        public class MyCountDownTimer extends CountDownTimer //use for actual timer
	    {
	        public MyCountDownTimer (long startTime, long interval)
	        {   
	            super(startTime, interval); //pass to superclasses' constructor
	        }


	        @Override
	        //Alert user to start game upon timer finish
	        public void onFinish(){

	            new Handler().postDelayed(new Runnable() {
	    	        @Override
	    	        public void run() {

	    	        	setContentView(R.layout.activity_time_bomb_game);

	    	            }
	    	        }, 1000); 


	        	//use handler to start new activity to capture flips!
	        }

	        @Override
	        //used to update timer value
	        public void onTick (long millisUntilFinished)
	        {
	        	if(millisUntilFinished/1000 > 12)
	        	{
	        		goText.setText("GO!"); // display "GO!" in the first 3 seconds
	        	}
	        	else
	        	{
	        		goText.setText(" "); // get rid of "GO!"
	        	}

	        }
	    } 
}
