package com.safar724test.app;

import com.safar724test.app.activities.MainActivity;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Test
    public void onCreate() {
        MainActivity mainActivity = new MainActivity();
        assertNotNull(mainActivity);
    }

    @Test
    public void goToUrl() {
    }

    @Test
    public void onBackPressed() {
    }
}