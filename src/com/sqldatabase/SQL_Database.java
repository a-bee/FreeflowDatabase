package com.sqldatabase;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


import com.sqldatabase.MySQLiteHelper.DatabaseHelper;
import com.sqldatabase.model.Measurement;
import com.sqldatabase.MySQLiteHelper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;
 
public class SQL_Database extends Activity {
 
	final Activity thisActivity = this;
	List<Measurement> measurements = new LinkedList<Measurement>();
	MySQLiteHelper db = new MySQLiteHelper(this);
	DatabaseHelper mDbHelper;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql__database);
        db.open();
        
//        
// 
        db.addMeasurement(new Measurement((float) 14.0, new Date()));
//        db.addMeasurement(new Measurement((float) 11.0, new Date()));
//        db.addMeasurement(new Measurement((float) 10.0, new Date()));
//        measurements = db.getAllMeasurements();

        displayButton();
    }
    
//    protected void onPause(){
//    	db.close();
//    }
    

  //Button that starts a new activity that displays data
    private void displayButton(){
        final Button disp = (Button) findViewById(R.id.disp);
        disp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	displayData();	
            }
        });
    }
    
    //starts a new activity that displays data
    public void displayData(){
    	Intent intent = new Intent(this, displayDataActivity.class);
    	startActivity(intent);
    }
 
}
