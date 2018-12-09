package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class InputNoWhitespaceAfterArrayDeclarations2
{

    public class A {
        public int[][] create(int i, int j) {
            return new int[3] [3];//incorrect, 12:31
        }
    }

    public class B {
        public int create(int i, int j) [][] {//incorrect, 17:41
            return new int     [3][i + j] ;//incorrect,18:32
        }
    }

    public class C {
        public int[][] create(int i, int j) {
            return new int[i + j][i + j];//correct
        }
    }

    public class D {
        public int[][]   [] create(int i, int j) {//incorrect, 29:26
            return new int  [ i + j ]    [ i + j ]               [ 0 ]     ;//incorrect 30:29,42,66
        }
    }

    public class E {
        public int create(int i, int j, int   [][] k)[] [][] {//incorrect, 35:47,57
            int e [][] [] = new int[i + j] [2][i + j];//incorrect, 36:19,24,44
            e [0] [1][2] = 0; e[1][1][1] = 0;//incorrect, 37:15,19
            return e;
        }
    }
    public static class F {
        public static Integer [][][] create(int i, int j) {//incorrect, 42:31
            int[][] [] f= new int[   0][1    ][    2    ]   ;//incorrect, 43:21
            return new Integer[i + j][i + j][0];
        }
    }
    public class G {
        public List<String> [] [] [] create(int i, int j) {//incorrect, 48:29,32,35
            //cannot build with check - generic array creation error, but whitespaces still caught
            //List<String> g[][] [] = new List<String> [0][1][2];//incorrect 49:33,55
            //return new List<String>[i + j][i + j][0];//correct
            int g[][][] = new int [0][1][2];//incorrect 52:35
            g[  0][0   ][   0   ]=0;
            g [0][0][0]=0;//incorrect 54:15
            g[0] [0][0]=0;//incorrect 55:18
            g [0] [0] [0]        =0;//incorrect 56:15,19,23
            return null;
        }

    }
    public class H {
        public List<Integer> create(int i, int j)     []      [][] {//incorrect, 62:55,63
            return null;
        }
    }

    Object someStuff4 = boolean [].class;//incorrect, 67:33
    String[][] someStuff5 = new String[4][9];
    String[][] someStuff6 = (java.lang.String  []  []) someStuff5;//incorrect, 69:48,52
    String[][] someStuff7 = (String [][]) someStuff5;//incorrect, 70:37

    //this is legal until allowLineBreaks is set to false
    int someStuff8
    [];

    //this is legal until allowLineBreaks is set to false
    int[]
    someStuff81;


    Integer someStuff9[][][] = (Integer [][][]) F.create(1,2);//incorrect 81:41

    //type arguments
    List<char[]> someStuff10;//correct
    List<char [][]> someStuff11;//incorrect 85:15
    List<InputNoWhitespaceAfterArrayDeclarations2.A []> someStuff12;//incorrect 86:53
    void foo(List<? extends String[]> bar, Comparable<? super Object []> baz) { }//incorrect 87:70

    Integer someStuff13 = F.create(1,1)[0][0][0];
    Integer someStuff131 = F.create(1,1)  [0][0]   [0];//incorrect 90:43,52
    Object[] someStuff14 = (Object[]) null;
    Object[] someStuff15 = (Object  []  ) null;//incorrect 92:37

    byte someStuff16 = ((byte[]) someStuff4) [0];//incorrect 94:46

    public void bar() {
        if(someStuff15 instanceof Object  []) {//incorrect 97:43

        }
        if(someStuff15 instanceof Object[]  []) {//incorrect 100:45

        }
        if(someStuff15 instanceof Object[][]) {

        }
        Object[] a = null;

        if(a instanceof Object  []) {//incorrect 108:33

        }
        if(a instanceof Object[][]) {

        }
    }

}
