package com.sqldatabase.model;

import java.util.Calendar;
import java.util.Date;
 
public class Book {
 
    private int id;
    private String title;
    private String author;
    private Date timestamp = new Date();
 
    public Book(){}
 
    public Book(String title, String author) {
        super();
        this.title = title;
        this.author = author;
    }
 
    //getters & setters
 
    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author
                + "]";
    }
    
    public String getTitle(){
    	return title;
    }
    
    public void setTitle(String title){
    	this.title = title;
    }
    
    public String getAuthor(){
    	return author;
    }
    
    public void setAuthor(String author){
    	this.author = author;
    }
    
    public int getId(){
    	return id;
    }
    
    public void setId(int id){
    	this.id = id;
    }

    
    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public Date getTimestamp() {
        return this.timestamp;
    }
    

    
    public void updateTime(final int hourOfDay, final int minute) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(getTimestamp());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        setTimestamp(calendar.getTime());
    }

    public void updateDate(final int year, final int monthOfYear, final int dayOfMonth) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(getTimestamp());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setTimestamp(calendar.getTime());
    }

}