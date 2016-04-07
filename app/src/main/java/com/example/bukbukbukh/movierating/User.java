package com.example.bukbukbukh.movierating;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bukbukbukh on 1/24/16.
 */
public class User {
    /**
     * Constant term for string literals
     */
    private static final String BLANK = "";
    /**
     * Instance variables for class user
     */
    private String firstName;
    /**
     * last name
     */
    private String lastName;
    /**
     * username
     */
    private String userName;
    /**
     * password
     */
    private String password;
    /**
     * bio
     */
    private String bio;
    /**
     * major
     */
    private String major;
    /**
     * number of users
     */
    private static int numOfUsers = 2;

    /**
     * for Junit
     */
    private static List<User> myList;

    /**
     * for Junit
     */
    private Map<String, Integer> movieList;

    /**
     * the long constructor
     * @param fname the firstname
     * @param lname the lastname
     * @param uname the username
     * @param newPassword the password
     */
    public User(String fname, String lname, String uname, String newPassword) {
        firstName = fname;
        lastName = lname;
        userName = uname;
        password = newPassword;
        numOfUsers++;
        myList = new ArrayList<User>();
    }

    /**
     * the no argument constructor
     */
    public User() {
        firstName = BLANK;
        lastName = BLANK;
        userName = BLANK;
        this.password = BLANK;
        myList = new ArrayList<User>();
    }

    /**
     * only for Junit
     * @return a list of users
     */
    public static List<User> getMyList() {
        return myList;
    }

    /**
     * Only for Junit
     * @param user the user
     */
    public static void addToList(User user) {
        myList.add(user);
    }

    /**
     * rate movie function for Junit
     * @param movie the movie
     * @param rating the rating
     */
    public void rateMovie(String movie, int rating) {
        movieList.put(movie, rating);
    }

    /**
     * gets the movie list for Junit
     * @return the movie list
     */
    public Map<String, Integer> getMovieList() {
        return movieList;
    }

    /**
     * get first name
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * get last name
     * @return last name
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * get username
     * @return the username
     */

    public String getUserName() {
        return userName;
    }

    /**
     * get number of users
     * @return number of users
     */

    public int getNumOfUsers() {
        return numOfUsers;
    }

    /**
     * get password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * get bio
     * @return the bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * set bio value
     * @param str bio
     */
    public void setBio(String str) {
        bio = str;
    }

    /**
     * get major
     * @return the major
     */
    public String getMajor() {
        return major;
    }

    /**
     * set major value
     * @param str major
     */
    public void setMajor(String str) {
        major = str;
    }

    /**
     * set first name value
     * @param fname first name
     */
    public void setFirstName(String fname) {
        firstName = fname;
    }

    /**
     * set last name
     * @param lname last name
     */
    public void setLastName(String lname) {
        lastName = lname;
    }

    /**
     * set user name
     * @param uname username
     */
    public void setUserName(String uname) {
        userName = uname;
    }

    /**
     * set password
     * @param newPassword the password value
     */
    public void setPassword(String newPassword) {
        password = newPassword;
    }
}
