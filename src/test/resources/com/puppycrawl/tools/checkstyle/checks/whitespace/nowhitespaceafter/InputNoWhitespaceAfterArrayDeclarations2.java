/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = INDEX_OP


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.util.List;



public class InputNoWhitespaceAfterArrayDeclarations2
{

    public class A {
        public int[][] create(int i, int j) {
            return new int[3] [3];
        }
    }

    public class B {
        public int create(int i, int j) [][] {
            return new int     [3][i + j] ;
        }
    }

    public class C {
        public int[][] create(int i, int j) {
            return new int[i + j][i + j];//correct
        }
    }

    public class D {
        public int[][]   [] create(int i, int j) {
            return new int  [ i + j ]    [ i + j ]               [ 0 ]     ;
        }
    }

    public class E {
        public int create(int i, int j, int   [][] k)[] [][] {
            int e [][] [] = new int[i + j] [2][i + j];
            e [0] [1][2] = 0; e[1][1][1] = 0;
            // 2 violations above:
            //          ''e' is followed by whitespace.'
            //          '']' is followed by whitespace.'
            return e;
        }
    }
    public static class F {
        public static Integer [][][] create(int i, int j) {
            int[][] [] f= new int[   0][1    ][    2    ]   ;
            return new Integer[i + j][i + j][0];
        }
    }
    public class G {
        public List<String> [] [] [] create(int i, int j) {
            //cannot build with check - generic array creation error, but whitespaces still caught
            //List<String> g[][] [] = new List<String> [0][1][2];//incorrect 49:33,55
            //return new List<String>[i + j][i + j][0];//correct
            int g[][][] = new int [0][1][2];
            g[  0][0   ][   0   ]=0;
            g [0][0][0]=0; // violation ''g' is followed by whitespace.'
            g[0] [0][0]=0; // violation '']' is followed by whitespace.'
            g [0] [0] [0]        =0;
            // 3 violations above:
            //          ''g' is followed by whitespace.'
            //          '']' is followed by whitespace.'
            //          '']' is followed by whitespace.'
            return null;
        }

    }
    public class H {
        public List<Integer> create(int i, int j)     []      [][] {
            return null;
        }
    }

    Object someStuff4 = boolean [].class;
    String[][] someStuff5 = new String[4][9];
    String[][] someStuff6 = (java.lang.String  []  []) someStuff5;

    String[][] someStuff7 = (String [][]) someStuff5;

    //this is legal until allowLineBreaks is set to false
    int someStuff8
    [];

    //this is legal until allowLineBreaks is set to false
    int[]
    someStuff81;


    Integer someStuff9[][][] = (Integer [][][]) F.create(1,2);

    //type arguments
    List<char[]> someStuff10;//correct
    List<char [][]> someStuff11;
    List<InputNoWhitespaceAfterArrayDeclarations2.A []> someStuff12;

    void foo(List<? extends String[]> bar, Comparable<? super Object []> baz) { }

    Integer someStuff13 = F.create(1,1)[0][0][0];
    Integer someStuff131 = F.create(1,1)  [0][0]   [0];
            // 2 violations above:
            //          '')' is followed by whitespace.'
            //          '']' is followed by whitespace.'
    Object[] someStuff14 = (Object[]) null;
    Object[] someStuff15 = (Object  []  ) null;

    byte someStuff16 = ((byte[]) someStuff4) [0]; // violation '')' is followed by whitespace.'

    public void bar() {
        if(someStuff15 instanceof Object  []) {

        }
        if(someStuff15 instanceof Object[]  []) {

        }
        if(someStuff15 instanceof Object[][]) {

        }
        Object[] a = null;

        if(a instanceof Object  []) {

        }
        if(a instanceof Object[][]) {

        }
    }

}
