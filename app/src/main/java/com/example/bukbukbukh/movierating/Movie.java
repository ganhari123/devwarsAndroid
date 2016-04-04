package com.example.bukbukbukh.movierating;

import java.io.Serializable;

/**
 * Created by bukbukbukh on 2/16/16.
 */
public class Movie implements Serializable{
    /**
     * the title of movie
     */
    private String title;
    /**
     * The year of movie
     */
    private int year;
    /**
     * The runtime of the movie
     */
    private int runtime;
    /**
     * the synopsis of movie
     */
    private String synopsis;

    /**
     * The empty Constructor For movie
     */
    public Movie() {
        title = "";
        year = 0;
        runtime = 0;
        synopsis = "";
    }

    /**
     * The other constructor of movie
     * @param newTitle the title of movie
     * @param newYear the year of movie
     */
    public Movie(String newTitle, int newYear) {
        this.title = newTitle;
        this.year = newYear;
    }


    /**
     * Returns the title
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * returns the year
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * returns the runtime of the movie
     * @return the runtime
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * returns the synopsis
     * @return the synopsis
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * sets the title to the string
     * @param str the string
     */
    public void setTitle(String str) {
        title = str;
    }

    /**
     * sets the year to the year input
     * @param y the year
     */
    public void setYear(int y) {
        year = y;
    }

    /**
     * sets the runtime to the runtime input
     * @param y the runtime
     */
    public void setRuntime(int y) {
        runtime = y;
    }

    /**
     * the synopsis of the movie set to input
     * @param str the synopsis
     */
    public void setSynopsis(String str) {
        synopsis = str;
    }

    /**
     * returns the title of the movie
     * @return the title
     */
    public String toString() {
        return title;
    }
}
