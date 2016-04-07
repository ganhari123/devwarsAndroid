package com.example.bukbukbukh.movierating;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by bukbukbukh on 4/4/16.
 */
public class JunitTests {
    private User user;
    private String[] movies;
    private String[] moviesNotRated;

    @Before
    public void setUp() {
        //Sara Jacks Setup condtions
        user = new User("Sara", "Jacks", "sjacks3", "password");
        user.rateMovie("Toy Story", 4);
        user.rateMovie("The Princess Bride", 4);
        user.rateMovie("Die Hard", 5);
        user.rateMovie("Lethal Weapon", 5);
        movies = new String[4];
        movies[0] = "Toy Story";
        movies[1] = "The Princess Bride";
        movies[2] = "Die Hard";
        movies[3] = "Lethal Weapon";
        moviesNotRated = new String[4];
        moviesNotRated[0] = "Taken";
        moviesNotRated[1] = "The Butterfly Effect";
        moviesNotRated[2] = "Final Destination";
        moviesNotRated[3] = "Princess Bride";

        //Ganapathy Hari Narayan setup conditions

        //Ritika Ravichandra setup conditions
    }

    @Test(timeout = 200)
    public void testMovieIncluded() {
        for (int i = 0; i < movies.length; i++) {
            assertTrue(UserMod.hasRated(user, movies[i]) != 0);
        }
    }

    @Test(timeout = 200)
    public void testMovieNotIncluded() {
        for (int i = 0; i < moviesNotRated.length; i++) {
            assertTrue(UserMod.hasRated(user, moviesNotRated[i]) == 0);
        }
    }

}
