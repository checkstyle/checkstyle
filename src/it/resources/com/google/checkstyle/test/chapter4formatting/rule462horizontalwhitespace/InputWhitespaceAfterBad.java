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

    public void check4() throws java.io.IOException {
        try(java.io.InputStream ignored = System.in;) { } // warn
    }

    public void check5() {
        try {} finally{} // warn
        try {} catch (Exception e){} finally{} // warn
    }

    public void check6() {
        try {} catch(Exception e){} // warn
    }

    public void check7() {
        synchronized(this) { } // warn
        synchronized (this) { }
    }

    public String check8() {
        return("a" + "b"); // warn
    }
}
