package com.sqldatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.sqldatabase.R;
import com.sqldatabase.model.Measurement;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
 
public class displayDataActivity extends Activity {
	
    public static final String HOUR_MINUTE = "HH:mm:ss";
    public static final String DAY_MONTH_YEAR = "dd/MM/yyyy";
    
	 // only ok because this app is not multithreaded!
    public static final SimpleDateFormat TIME_AND_DATE_FORMAT = new SimpleDateFormat(String.format("%s, %s", HOUR_MINUTE, DAY_MONTH_YEAR));
    View view;
 
	final Activity thisActivity = this;
	private final Context context = this;
	MySQLiteHelper db;
	TableLayout mainTable;
	 
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater li=getLayoutInflater();
        View rootView=li.inflate(R.layout.data_layout, null);
        setContentView(rootView);
        List<Measurement> measurements = new LinkedList<Measurement>();
        db = new MySQLiteHelper(this);
        measurements = getAllMeasurements(db);
        TabulateDatabase(rootView, measurements);
    }
    
    
    
    @Override    
    protected void onDestroy() {        
        super.onDestroy();
        db.close();
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	db.close();
  }
    

    
    // Get All Measurements
    public List<Measurement> getAllMeasurements(MySQLiteHelper dbHelper) {
    	dbHelper.open();
        List<Measurement> measurements = new LinkedList<Measurement>();
        // 1. build the query
        String query = "SELECT rowid _id,* FROM " + dbHelper.MEASUREMENT_TABLE;
        // 2. get reference to writable DB
        Cursor cursor = dbHelper.get_mDb().rawQuery(query, null);
//        // 3. go over each row, build measurement and add it to list
        if (cursor.moveToFirst()) {
            do {
          	  final Measurement measurement = Measurement.create(cursor);
          	  Log.e("Blah1", "" + measurement.getTimestamp());
                measurements.add(measurement);
            } while (cursor.moveToNext());
        }
        
        Log.e("getAllMeasurements()", measurements.toString());
  	      
  	      return measurements;
  	  }
    
    

    
    private void TabulateDatabase(View v, List<Measurement> measurements){
    	
        mainTable = (TableLayout) v.findViewById(R.id.maintable);
    	
    	for (int i = 0; i < measurements.size(); i++){
            
            // Create a TableRow and give it an ID
            TableRow tr = new TableRow(this);
            tr.setId(100+i);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
  
            // Create a TextView for column 1
            TextView col1 = new TextView(this);
            col1.setId(200+i);
            col1.setText("" + measurements.get(i).getTimestampString());
            col1.setPadding(0,0,60,0);    
            col1.setGravity(Gravity.LEFT);
            col1.setTextColor(Color.BLACK);
            col1.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(col1);
  
            // Create a TextView for column 2
            TextView col2 = new TextView(this);
            col2.setId(300 + i);
            col2.setText(measurements.get(i).getValue() + " kg");
            col2.setPadding(0,0,20,0);   
            col2.setGravity(Gravity.CENTER_HORIZONTAL);
            col2.setTextColor(Color.BLACK);
            col2.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1f));
      
            col2.setHorizontallyScrolling(false);
            col2.setMaxLines(100);
            col2.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tr.addView(col2);
            
            // Add the TableRow to the TableLayout
            mainTable.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
    
    
    
    static int i = 0;
    private void setupTable(View v, Measurement value1){
    	
        mainTable = (TableLayout) v.findViewById(R.id.maintable);
    	
    	for (int i=0; i<50 ; i++){
            
            // Create a TableRow and give it an ID
            TableRow tr = new TableRow(this);
            tr.setId(100+i);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
  
            // Create a TextView for column 1
            TextView col1 = new TextView(this);
            col1.setId(200+i);
            col1.setText("" + value1.getTimestampString());
            col1.setPadding(0,0,60,0);    
            col1.setGravity(Gravity.LEFT);
            col1.setTextColor(Color.BLACK);
            col1.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(col1);
  
            // Create a TextView for column 2
            TextView col2 = new TextView(this);
            col2.setId(300 + i);
            col2.setText(value1.getValue() + " kg");
            col2.setPadding(0,0,20,0);   
            col2.setGravity(Gravity.CENTER_HORIZONTAL);
            col2.setTextColor(Color.BLACK);
            col2.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1f));
      
            if (i%2 == 0)
            {
                col1.setBackgroundColor(Color.WHITE);
                col2.setBackgroundColor(Color.WHITE);
                tr.setBackgroundColor(Color.WHITE);
            }
            else
            {
                tr.setBackgroundColor(Color.LTGRAY);
                col1.setBackgroundColor(Color.LTGRAY);
                col2.setBackgroundColor(Color.LTGRAY);
            }
            col2.setHorizontallyScrolling(false);
            col2.setMaxLines(100);
            col2.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tr.addView(col2);
//  
            // Add the TableRow to the TableLayout
            mainTable.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            i++;
        }
    }
   

}