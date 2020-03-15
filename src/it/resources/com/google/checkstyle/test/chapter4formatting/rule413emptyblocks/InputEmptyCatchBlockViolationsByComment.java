package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import java.io.IOException;

public class InputEmptyCatchBlockViolationsByComment
{
    private void foo() {
        try {
            throw new RuntimeException();
        } catch (Exception expected) //ok
        {
            //Expected
        }
    }

    private void foo1() {
        try {
            throw new RuntimeException();
        } catch (Exception e)
        {} // warn

    }

    private void foo2() {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException ignore)
        /*warn*/ {
        }
    }

    private void foo3() { // comment
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException e) { //This is expected
        }
    }

    private void foo4() {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException e) { /* This is expected*/
        }
    }

    private void foo5() {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException e) { // Some singleline comment
        }
    }
    private void some() {
        try {
            throw new IOException();
        } catch (IOException e) //ok
        {
            /* ololo
             * blalba
             */
        }
    }
    private void some1() {
        try {
            throw new IOException();
        } catch (IOException e) //ok
        {
            /* lalala
             * This is expected
             */
        }
    }
    private void some2() {
        try {
            throw new IOException();
        } catch (IOException e) //ok
        {
            /*
             * This is expected
             * lalala
             */
        }
    }
    private void some3() {
        try {
            throw new IOException();
        } catch (IOException e) //ok
        {
            // some comment
            //This is expected
        }
    }
    private void some4() {
        try {
            throw new IOException();
        } catch (IOException e) //ok
        {
            //This is expected
            // some comment
        }
    }
    private void some5() {
        try {
            throw new IOException();
        } catch (IOException e) //ok
        {
            /* some comment */
            //This is expected
        }
    }
}
