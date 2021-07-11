package com.linkedin.test;

import org.testng.annotations.Test;
import utils.KeywordEngine;

import java.io.FileNotFoundException;

public class LoginTest {
    public KeywordEngine keywordEngine;

    @Test
    public void Login() throws FileNotFoundException {
        keywordEngine = new KeywordEngine();
        keywordEngine.startExecution("Sheet1");
    }






}
