package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

public class InputWhitespaceAfterGood {
    public void check1(int x, int y){
        for (int a=1, b=2; a<5; a++, b--);
        while (x==0){
            int a=0, b=1;
        }
        do {
            System.out.println("Testing");
        }while (x==0||y==2);
    }
    public void check2(final int a, final int b){
        if ((float) a==0.0){
            System.out.println("true");
        }
        else {
            System.out.println("false");
        }
    }

    public void check3(int... a) {
        Runnable r2 = () -> String.valueOf("Hello world two!");
        switch (a[0]) {
            default:
                break;
        }
    }
}
