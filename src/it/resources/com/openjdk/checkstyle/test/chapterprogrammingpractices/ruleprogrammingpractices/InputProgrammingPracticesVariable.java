package com.openjdk.checkstyle.test.chapterprogrammingpractices.ruleprogrammingpractices;

// violation first line 'Header is missing*'

public class InputProgrammingPracticesVariable {

    public static final int CONSTANT = 0;

    public static int a = 0; // violation 'Public static variable must be final.'

    private static int c = 0;

    class Temp {
        public static int cons = 10; // violation 'Public static variable must be final.'
    }
}

