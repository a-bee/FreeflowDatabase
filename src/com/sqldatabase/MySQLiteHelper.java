package com.sqldatabase;

import java.util.LinkedList;
import java.util.List;

import com.sqldatabase.model.Book;

import com.sqldatabase.model.Measurement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class MySQLiteHelper {
 
	
	
	private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "mDb";
 
    public static final String MEASUREMENT_TABLE = "measurements";
    private static final String MEASURE_CREATE = "create table measurements (" +
    		"_id integer primary key not null, " 
    		+ "value real not null, "
            + "measure_date datetime default current_timestamp);";
    //Measurement Table Columns names
    public static final String KEY_ID = "_id";
    public static final String KEY_VALUE = "value";
    public static final String KEY_DATE = "measure_date";
    private static final String[] COLUMNS = {KEY_ID,KEY_VALUE,KEY_DATE};
 
    //private SQLiteDatabase mDb;
    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;
    private boolean open = false;
    private Context context;
    
    
    public SQLiteDatabase get_mDb(){
    	return this.mDb;
    }
    
    public static class DatabaseHelper extends SQLiteOpenHelper {
	    
	    DatabaseHelper(final Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
	    }
	   
	    @Override
	    public void onCreate(SQLiteDatabase db) {  
	    	Log.e("OPEN", "create");
	    	db.execSQL(MEASURE_CREATE);
	    }	    
	 
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // Drop older table if existed
	    	Log.e("OPEN", "upgrade");
	        db.execSQL("DROP TABLE IF EXISTS measurements");
	        this.onCreate(db);
	    }	   
	    
    }
   
    
    /**
     * Constructor - takes the context to allow the database to be opened/created
     * 
     * @param ctx
     *            the Context within which to work
     */
    public MySQLiteHelper(final Context ctx) {
        this.context = ctx;
        open();
    }
    
    /**
     * Open the notes database. If it cannot be opened, try to create a new instance of the database. If it cannot be
     * created, throw an exception to signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an initialization call)
     * @throws SQLException
     *             if the database could be neither opened or created
     */
    public MySQLiteHelper open() throws SQLException {
        Log.d("OPEN", "open database");
        try {
            if (this.mDbHelper == null) {
                this.mDbHelper = new DatabaseHelper(this.context);
            }
            this.mDb = this.mDbHelper.getWritableDatabase();
            
            if (this.mDb.isReadOnly() || this.mDb.isDbLockedByOtherThreads() || this.mDb.isDbLockedByCurrentThread()) {
                this.mDb.close();
                Log.w("OPEN", "could not open database: locked or readonly");
            } else {
                this.open = true;
            }
        } catch (final Exception e) {
            Log.e("OPEN", "failed to open database", e);
        }
        return this;
    }
    
    
    
    public void close() {
        Log.d("TAG", "close");
        try {
            if (this.open) {
                this.mDb.close();
            }
            this.mDbHelper.close();
            this.open = false;
        } catch (final Exception e) {
            Log.e("TAG", "close failed", e);
        }
    }
    
      
    
    public long createMeasure(final Measurement measurement) {
        Log.d("TAG", "createMeasure " + measurement);
        final ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_VALUE, measurement.getValue());
//        initialValues.put(KEY_ID, measurement.getId());
//        initialValues.put(KEY_DATE, measurement.getTimestamp() != null ? measurement.getTimestamp().getTime() : System.currentTimeMillis());
        return this.mDb.insert(MEASUREMENT_TABLE, null, initialValues);
    }
    
    
    
    /**
     * Create a new note using the title and body provided. If the note is successfully created return the new rowId for
     * that note, otherwise return a -1 to indicate failure.
     * 
     * @param title
     *            the title of the note
     * @param body
     *            the body of the note
     * 
     * @return rowId or -1 if failed
     */
    public void addMeasurement(Measurement measure){
    	open();
        Log.d("addMeasurement", measure.toString());
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_VALUE, measure.getValue()); // get value
        values.put(KEY_DATE, measure.getTimestamp() != null ? measure.getTimestamp().getTime() : System.currentTimeMillis());
 
        // 3. insert
        this.mDb.insert(MEASUREMENT_TABLE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        // 4. close
        this.mDb.close(); 
    }

   
    
    
    
//  // Get All Measurements
//  public List<Measurement> getAllMeasurements() {
//	  
//      List<Measurement> measurements = new LinkedList<Measurement>();
//      // 1. build the query
//      String query = "SELECT rowid _id,* FROM " + MEASUREMENT_TABLE;
//      // 2. get reference to writable DB
//      Cursor cursor = this.mDb.rawQuery(query, null);
////      // 3. go over each row, build measurement and add it to list
//      if (cursor.moveToFirst()) {
//          do {
//        	  final Measurement measurement = Measurement.create(cursor);
//        	  Log.e("Blah1", "" + measurement.getTimestamp());
//              measurements.add(measurement);
//          } while (cursor.moveToNext());
//      }
//      
//      Log.e("getAllMeasurements()", measurements.toString());
//	      
//	      return measurements;
//	  }
//	    
//     
  
    
  
  
  
  
 
//    public Book getBook(int id){
// 
//        // 1. get reference to readable DB
//        SQLiteDatabase db = this.getReadableDatabase();
// 
//        // 2. build query
//        Cursor cursor = 
//                db.query(TABLE_BOOKS, // a. table
//                COLUMNS, // b. column names
//                " id = ?", // c. selections 
//                new String[] { String.valueOf(id) }, // d. selections args
//                null, // e. group by
//                null, // f. having
//                null, // g. order by
//                null); // h. limit
// 
//        // 3. if we got results get the first one
//        if (cursor != null)
//            cursor.moveToFirst();
// 
//        // 4. build book object
//        Book book = new Book();
//        book.setId(Integer.parseInt(cursor.getString(0)));
//        book.setTitle(cursor.getString(1));
//        book.setAuthor(cursor.getString(2));
// 
//        Log.d("getBook("+id+")", book.toString());
// 
//        // 5. return book
//        return book;
//    }
 
//    // Get All Books
//    public List<Book> getAllBooks() {
//        List<Book> books = new LinkedList<Book>();
// 
//        // 1. build the query
//        String query = "SELECT  * FROM " + TABLE_BOOKS;
// 
//        // 2. get reference to writable DB
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
// 
//        // 3. go over each row, build book and add it to list
//        Book book = null;
//        if (cursor.moveToFirst()) {
//            do {
//                book = new Book();
//                book.setId(Integer.parseInt(cursor.getString(0)));
//                book.setTitle(cursor.getString(1));
//                book.setAuthor(cursor.getString(2));
// 
//                // Add book to books
//                books.add(book);
//            } while (cursor.moveToNext());
//        }
// 
//        Log.d("getAllBooks()", books.toString());
// 
//        // return books
//        return books;
//    }
 
     // Updating single book
//    public int updateBook(Book book) {
// 
//        // 1. get reference to writable DB
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        // 2. create ContentValues to add key "column"/value
//        ContentValues values = new ContentValues();
//        values.put("title", book.getTitle()); // get title 
//        values.put("author", book.getAuthor()); // get author
// 
//        // 3. updating row
//        int i = db.update(TABLE_BOOKS, //table
//                values, // column/value
//                KEY_ID+" = ?", // selections
//                new String[] { String.valueOf(book.getId()) }); //selection args
// 
//        // 4. close
//        db.close();
// 
//        return i;
// 
//    }
 
//    // Deleting single book
//    public void deleteBook(Book book) {
// 
//        // 1. get reference to writable DB
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        // 2. delete
//        db.delete(TABLE_BOOKS,
//                KEY_ID+" = ?",
//                new String[] { String.valueOf(book.getId()) });
// 
//        // 3. close
//        db.close();
// 
//        Log.d("deleteBook", book.toString());
// 
//    }
}