package edu.astate.cs;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

public class Instructions extends Activity {

	private boolean continueMusic = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructions);
		
		Intent i = getIntent();
		i.getBooleanExtra("boolean", continueMusic);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.instructions, menu);
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
	
	public void firstNext (View view)
	{
		this.setContentView(R.layout.activity_instructions2);
	}
	
	public void secondNext (View view)
	{
		this.setContentView(R.layout.activity_instructions3);
	}
	
	public void thirdNext (View view)
	{
		this.setContentView(R.layout.activity_instructions4);
	}
	
	public void lastNext (View view)
	{
		this.setContentView(R.layout.activity_instructions5);
	}
	
	public void finish (View view)
	{
		Intent Act2Intent = new Intent(this, Home.class);
		if (Home.mIsBound)
			continueMusic = true;
		else
			continueMusic = false;
        Act2Intent.putExtra("boolean", continueMusic);
        startActivity(Act2Intent);  
	}
	
	public void firstBack (View view)
	{
		Intent Act2Intent = new Intent(this, Home.class);
		if (Home.mIsBound)
			continueMusic = true;
		else
			continueMusic = false;
        Act2Intent.putExtra("boolean", continueMusic);
        startActivity(Act2Intent);  
	}
	
	public void secondBack (View view)
	{
		this.setContentView(R.layout.activity_instructions);
	}
	
	public void thirdBack (View view)
	{
		this.setContentView(R.layout.activity_instructions2);
	}
	
	public void fourthBack (View view)
	{
		this.setContentView(R.layout.activity_instructions3);
	}
	
	public void fifthBack (View view)
	{
		this.setContentView(R.layout.activity_instructions4);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	    			
		    	Intent Act2Intent = new Intent(this, Home.class);
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
	
}
