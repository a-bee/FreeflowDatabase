package com.sqldatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sqldatabase.R;
import com.sqldatabase.model.Measurement;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
	final MySQLiteHelper mDbHelper = new MySQLiteHelper(this);
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater li=getLayoutInflater();
        View rootView=li.inflate(R.layout.data_layout,null);
        setContentView(rootView);
        Measurement m = new Measurement((float) 12.0, new Date());
        setupTable(rootView, m);
        Measurement m2 = new Measurement((float) 13.0, new Date());
        setupTable(rootView, m2);
        Measurement m3 = new Measurement((float) 14.0, new Date());
        setupTable(rootView, m3);
        
    }
    
    @Override    
    protected void onDestroy() {        
        super.onDestroy();
        mDbHelper.close();
    }
    
    
    /**
     * Formats and displays the date
     * 
     * @param view
     * @param circumference
     */
    private void displayDate(final View view, final Date timestamp) {
        final String formattedDate = TIME_AND_DATE_FORMAT.format(timestamp);
        final TextView t2 = (TextView) view.findViewById(R.id.date);
        t2.setText(formattedDate);
    }
    
    

    /**
     * Converts, formats and displays the weight measure
     * 
     * @param view
     * @param circumference
     */
    private void displayMeasurement(final View view, final Measurement measurement) {
        final TextView t = (TextView) view.findViewById(R.id.measure);
        t.setText(measurement.getValue() + " kg");
    }
  
    
    TableLayout mainTable;
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