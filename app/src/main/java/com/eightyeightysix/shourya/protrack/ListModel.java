package com.eightyeightysix.shourya.protrack;

/**
 * Created by shourya on 16/5/17.
 */

public class ListModel {
    private String date = "";
    private String content = "";

    public void setDate( String date ) {
        this.date = date;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
}
