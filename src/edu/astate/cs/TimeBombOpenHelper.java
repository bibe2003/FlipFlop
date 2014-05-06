package edu.astate.cs;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TimeBombOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "TBdb";
    private static final String TB_TABLE_NAME = "TimeBomb";
    public static final String KEY_SCORE = "score";
    private SQLiteDatabase db = this.getWritableDatabase(); //get actual instance of DB
    
	
    
	TimeBombOpenHelper (Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION); 
		//constructor creates database
		
		//not actually a database in memory until getWritableDatabase() 
		//or getReadableDatabase() called
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String TB_TABLE_CREATE =
                "CREATE TABLE if not exists TimeBomb (" +
                 "score INTEGER );"; //create tbTable if not there already
		
		db.execSQL(TB_TABLE_CREATE); 

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		    db.execSQL("DROP TABLE IF EXISTS " + TB_TABLE_NAME);
		    onCreate(db);

	}
	
	//add new score to DB
	public void addScore (int newScore)
	{
		
		
		ContentValues vals = new ContentValues();
		vals.put(KEY_SCORE, newScore); //put score into value container
		
		//Insert Row into Database
		db.insert(TB_TABLE_NAME, null, vals); //insert into table
		
		
	}
	
	//use to get list of all scores in database, sorted in string format
	public List<String> getAllScores ()
	{
		
		List<Integer> actualScores = new ArrayList<Integer>();
		//Select All Query, Sort by Score
		String selectQuery = "Select * From " + TB_TABLE_NAME + 
							 " ORDER BY " + KEY_SCORE + " DESC";
		
	
		Cursor cursor = db.rawQuery(selectQuery, null);
		//use cursor object to store queried results
		
		//loop through each row and them to list
		
		if (cursor.moveToFirst())
		{
			do{
				actualScores.add(Integer.parseInt(cursor.getString(0)));
				
			} while (cursor.moveToNext()); //while more rows to parse
		}
		
		List<String> stringList = new ArrayList<String>();
		for (Integer i: actualScores)
		{
			stringList.add(i.toString());
		} //convert integer values to string, used in listview
		

		return stringList; //return list of strings
	}
		

}
