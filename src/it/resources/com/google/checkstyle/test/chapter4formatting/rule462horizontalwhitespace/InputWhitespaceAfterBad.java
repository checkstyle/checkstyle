package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

public class InputWhitespaceAfterBad {
    public void check1(int x,int y) { // violation '',' is not followed by whitespace.'
        for(int a = 1,b = 2;a < 5;a++,b--);
        // 6 violations above:
        //  ''for' is not followed by whitespace.'
        //  ''for' is not followed by whitespace.'
        //  '',' is not followed by whitespace.'
        //  '';' is not followed by whitespace.'
        //  '';' is not followed by whitespace.'
        //  '',' is not followed by whitespace.'
        while(x == 0) {
        // 2 violations above:
        //  ''while' is not followed by whitespace.'
        //  ''while' is not followed by whitespace.'
            int a = 0,b = 1; // violation '',' is not followed by whitespace.'
        }
        do{
        // 3 violations above:
        //  ''do' is not followed by whitespace.'
        //  ''do' is not followed by whitespace.'
        //  ''{' is not preceded with whitespace.'
            System.out.println("Testing");
        } while(x == 0 || y == 2);
        // 2 violations above:
        //  ''while' is not followed by whitespace.'
        //  ''while' is not followed by whitespace.'
    }
    public void check2(final int a,final int b) { // violation '',' is not followed by whitespace.'
        if((float)a == 0.0) {
        // 3 violations above:
        //  ''if' is not followed by whitespace.'
        //  ''if' is not followed by whitespace.'
        //  ''typecast' is not followed by whitespace.'
            System.out.println("true");
        }
        else{
        // 3 violations above:
        //  ''else' is not followed by whitespace.'
        //  ''else' is not followed by whitespace.'
        //  ''{' is not preceded with whitespace.'
            System.out.println("false");
        }
    }

    public void check3(int...a) { // violation ''...' is not followed by whitespace.'
        Runnable r2 = () ->String.valueOf("Hello world two!");
        // 2 violations above:
        //  ''->' is not followed by whitespace.'
        //  ''->' is not followed by whitespace.'
        switch(a[0]) {
        // 2 violations above:
        //  ''switch' is not followed by whitespace.'
        //  ''switch' is not followed by whitespace.'
            default:
                break;
        }
    }

    public void check4() throws java.io.IOException {
        try(java.io.InputStream ignored = System.in;) { }
        // 2 violations above:
        //  ''try' is not followed by whitespace.'
        //  ''try' is not followed by whitespace.'
    }

    public void check5() {
        try { } finally{ }
        // 3 violations above:
        //  ''finally' is not followed by whitespace.'
        //  ''finally' is not followed by whitespace.'
        //  ''{' is not preceded with whitespace.'
        try { } catch (Exception e) { } finally{ }
        // 3 violations above:
        //  ''finally' is not followed by whitespace.'
        //  ''finally' is not followed by whitespace.'
        //  ''{' is not preceded with whitespace.'
    }

    public void check6() {
        try { } catch(Exception e) { }
        // 2 violations above:
        //  ''catch' is not followed by whitespace.'
        //  ''catch' is not followed by whitespace.'
    }

    public void check7() {
        synchronized(this) { }
        // 2 violations above:
        //  ''synchronized' is not followed by whitespace.'
        //  ''synchronized' is not followed by whitespace.'
        synchronized (this) { }
    }

    public String check8() {
        return("a" + "b");
        // 2 violations above:
        //  ''return' is not followed by whitespace.'
        //  ''return' is not followed by whitespace.'
    }
}
