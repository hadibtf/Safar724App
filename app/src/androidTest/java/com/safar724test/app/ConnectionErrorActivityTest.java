package com.safar724test.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectionErrorActivityTest {

    @Test
    public void onCreate() {
    }

    @Test
    public void goBack() throws Exception{
        ConnectionErrorActivity connectionErrorActivity = new ConnectionErrorActivity();
        assertNotNull(connectionErrorActivity);
    }
}