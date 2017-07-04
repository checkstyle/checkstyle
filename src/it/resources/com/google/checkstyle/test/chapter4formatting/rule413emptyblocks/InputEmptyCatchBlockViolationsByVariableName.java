package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import java.io.IOException;

public class InputEmptyCatchBlockViolationsByVariableName
{
    private void foo() {
        try {
            throw new RuntimeException();
        } catch (Exception expected) //ok
        {
            
        }
    }
    
    private void foo1() {
        try {
            throw new RuntimeException();
        } catch (Exception e) //warn
        {}
        
    }
    
    private void foo2() {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException expected) //ok 
        {
        }
    }
    
    private void foo3() { // comment
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException e) //warn
        {
        }
    }
    
    private void foo4() {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException expected) //ok
        {
        }
    }
    
    private void foo5() {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException e) //warn
        {
        }
    }
    private void some() {
        try {
            throw new IOException();
        } catch (IOException e) //warn
        {
            
        }
    }
}
