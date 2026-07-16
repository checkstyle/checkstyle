package com.openjdk.checkstyle.test.chapterprogrammingpractices.ruleprogrammingpractices;

public class InputProgrammingPracticesTodoComments {

    int i;
    int x;

    public void test() {
        // violation below 'matches to-do format'
        i++;   // TODO: do differently in future
        i++;   // todo: do differently in future
    }

    // violation below 'matches to-do format'
    // TODO: complete the method
    public void test1() {
    }

}
