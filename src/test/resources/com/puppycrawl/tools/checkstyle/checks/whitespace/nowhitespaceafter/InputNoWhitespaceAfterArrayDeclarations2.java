/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = ARRAY_DECLARATOR,INDEX_OP


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.util.List;



public class InputNoWhitespaceAfterArrayDeclarations2
{

    public class A {
        public int[][] create(int i, int j) {
            return new int[3] [3]; // violation
        }
    }

    public class B {
        public int create(int i, int j) [][] { // violation
            return new int     [3][i + j] ; // violation
        }
    }

    public class C {
        public int[][] create(int i, int j) {
            return new int[i + j][i + j];//correct
        }
    }

    public class D {
        public int[][]   [] create(int i, int j) { // violation
            return new int  [ i + j ]    [ i + j ]               [ 0 ]     ; // 3 violations
        }
    }

    public class E {
        public int create(int i, int j, int   [][] k)[] [][] { // 2 violations
            int e [][] [] = new int[i + j] [2][i + j]; // 3 violations
            e [0] [1][2] = 0; e[1][1][1] = 0; // 2 violations
            return e;
        }
    }
    public static class F {
        public static Integer [][][] create(int i, int j) { // violation
            int[][] [] f= new int[   0][1    ][    2    ]   ; // violation
            return new Integer[i + j][i + j][0];
        }
    }
    public class G {
        public List<String> [] [] [] create(int i, int j) { // 3 violations
            //cannot build with check - generic array creation error, but whitespaces still caught
            //List<String> g[][] [] = new List<String> [0][1][2];//incorrect 49:33,55
            //return new List<String>[i + j][i + j][0];//correct
            int g[][][] = new int [0][1][2]; // violation
            g[  0][0   ][   0   ]=0;
            g [0][0][0]=0; // violation
            g[0] [0][0]=0; // violation
            g [0] [0] [0]        =0; // 3 violations
            return null;
        }

    }
    public class H {
        public List<Integer> create(int i, int j)     []      [][] { // 2 violations
            return null;
        }
    }

    Object someStuff4 = boolean [].class; // violation
    String[][] someStuff5 = new String[4][9];
    String[][] someStuff6 = (java.lang.String  []  []) someStuff5; // 2 violations
    String[][] someStuff7 = (String [][]) someStuff5; // violation

    //this is legal until allowLineBreaks is set to false
    int someStuff8
    [];

    //this is legal until allowLineBreaks is set to false
    int[]
    someStuff81;


    Integer someStuff9[][][] = (Integer [][][]) F.create(1,2); // violation

    //type arguments
    List<char[]> someStuff10;//correct
    List<char [][]> someStuff11; // violation
    List<InputNoWhitespaceAfterArrayDeclarations2.A []> someStuff12; // violation
    void foo(List<? extends String[]> bar, Comparable<? super Object []> baz) { } // violation

    Integer someStuff13 = F.create(1,1)[0][0][0];
    Integer someStuff131 = F.create(1,1)  [0][0]   [0]; // 2 violations
    Object[] someStuff14 = (Object[]) null;
    Object[] someStuff15 = (Object  []  ) null; // violation

    byte someStuff16 = ((byte[]) someStuff4) [0]; // violation

    public void bar() {
        if(someStuff15 instanceof Object  []) { // violation

        }
        if(someStuff15 instanceof Object[]  []) { // violation

        }
        if(someStuff15 instanceof Object[][]) {

        }
        Object[] a = null;

        if(a instanceof Object  []) { // violation

        }
        if(a instanceof Object[][]) {

        }
    }

}
