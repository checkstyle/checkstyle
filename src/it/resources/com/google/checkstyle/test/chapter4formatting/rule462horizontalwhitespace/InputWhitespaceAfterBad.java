package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

public class InputWhitespaceAfterBad {
    public void check1(int x,int y){ // violation '',' is not followed by whitespace.'
        for(int a=1,b=2;a<5;a++,b--);
        // 5 violations above:
        //                    ''for' is not followed by whitespace.'
        //                    '',' is not followed by whitespace.'
        //                    '';' is not followed by whitespace.'
        //                    '';' is not followed by whitespace.'
        //                    '',' is not followed by whitespace.'
        while(x==0){ // violation ''while' is not followed by whitespace.'
            int a=0,b=1; // violation '',' is not followed by whitespace.'
        }
        do{ // violation ''do' is not followed by whitespace.'
            System.out.println("Testing");
        }while(x==0||y==2); // violation ''while' is not followed by whitespace.'
    }
    public void check2(final int a,final int b){ // violation '',' is not followed by whitespace.'
        if((float)a==0.0){
        // 2 violations above:
        //                    ''if' is not followed by whitespace.'
        //                    ''typecast' is not followed by whitespace.'
            System.out.println("true");
        }
        else{ // violation ''else' is not followed by whitespace.'
            System.out.println("false");
        }
    }

    public void check3 (int...a) { // violation ''...' is not followed by whitespace.'
        Runnable r2 = () ->String.valueOf("Hello world two!");
        // violation above ''->' is not followed by whitespace.'
        switch(a[0]) { // violation ''switch' is not followed by whitespace.'
            default:
                break;
        }
    }

    public void check4() throws java.io.IOException {
        try(java.io.InputStream ignored = System.in;) { }
        // violation above ''try' is not followed by whitespace.'
    }

    public void check5() {
        try {} finally{} // violation ''finally' is not followed by whitespace.'
        try {} catch (Exception e){} finally{}
        // violation above ''finally' is not followed by whitespace.'
    }

    public void check6() {
        try {} catch(Exception e){} // violation ''catch' is not followed by whitespace.'
    }

    public void check7() {
        synchronized(this) { } // violation ''synchronized' is not followed by whitespace.'
        synchronized (this) { }
    }

    public String check8() {
        return("a" + "b"); // violation ''return' is not followed by whitespace.'
    }
}
