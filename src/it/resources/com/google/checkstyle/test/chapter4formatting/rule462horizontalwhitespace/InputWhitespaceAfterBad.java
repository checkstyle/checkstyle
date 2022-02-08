package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

public class InputWhitespaceAfterBad {
    public void check1(int x,int y){ //warn
        for(int a=1,b=2;a<5;a++,b--); // warn
        while(x==0){ // warn
            int a=0,b=1; // warn
        }
        do{ // warn
            System.out.println("Testing");
        }while(x==0||y==2); // warn
    }
    public void check2(final int a,final int b){ // warn
        if((float)a==0.0){ // warn
            System.out.println("true");
        }
        else{ //warn
            System.out.println("false");
        }
    }

    public void check3 (int...a) { // warn
        Runnable r2 = () ->String.valueOf("Hello world two!"); // warn
        switch(a[0]) { // warn
            default:
                break;
        }
    }
}
