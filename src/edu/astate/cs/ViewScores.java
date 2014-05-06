package edu.astate.cs;

import java.util.ArrayList;
import java.util.List;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ViewScores extends Activity  {

	//private FreePlayOpenHelper fpDB; 
	private boolean continueMusic = false; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		continueMusic = getIntent().getExtras().getBoolean("boolean");
		getActionBar().setDisplayHomeAsUpEnabled(false); //get rid of back button in action bar
		
		setContentView(R.layout.activity_view_scores);	

	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_scores, menu);
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
    
    @Override 
    protected void onDestroy()
    {
    	super.onDestroy();
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

    //button handlers
    public void freeplay (View view)
	{
		this.setContentView(R.layout.activity_freeplayscores);
		
		final ListView listview = (ListView) findViewById(android.R.id.list); //get listview
		
		final List<String> fpList = new ArrayList<String>(Home.getFPScores()); //get scores from db
		
		
		listview.setAdapter(new ArrayAdapter<String>(this, 
				R.layout.list_item, fpList)); //bind scores to listview
		
	}
    
    public void timebomb (View view) /////////////////////// TEST
	{
		this.setContentView(R.layout.activity_timebombscores);
		
		final ListView listview = (ListView) findViewById(android.R.id.list); //get listview
		
		final List<String> tbList = new ArrayList<String>(Home.getTBScores()); //get scores from db
		
		
		listview.setAdapter(new ArrayAdapter<String>(this, 
				R.layout.list_item, tbList)); //bind scores to listview
		
	}
    
    public void challenge (View view) /////////////////////// TEST
	{
		this.setContentView(R.layout.activity_challengescores);
		
		final ListView listview = (ListView) findViewById(android.R.id.list); //get listview
		
		final List<String> chList = new ArrayList<String>(Home.getCHScores()); //get scores from db
		
		
		listview.setAdapter(new ArrayAdapter<String>(this, 
				R.layout.list_item, chList)); //bind scores to listview
		
	}
    
    
    
}
