package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class InputNoWhitespaceAfterArrayDeclarations2
{

    public class A {
        public int[][] create(int i, int j) {
            return new int[3] [3];//incorrect, 12:30 
        }
    }
    
    public class B {
        public int create(int i, int j) [][] {//incorrect, 17:40
            return new int     [3][i + j] ;//incorrect,18:27
        }
    }
    
    public class C {
        public int[][] create(int i, int j) {
            return new int[i + j][i + j];//correct
        }
    }
    
    public class D {
        public int[][]   [] create(int i, int j) {//incorrect, 29:23
            return new int  [ i + j ]    [ i + j ]               [ 0 ]     ;//incorrect 30:27,38,51
        }
    }
    
    public class E {
        public int create(int i, int j, int   [][] k)[] [][] {//incorrect, 35:44,56
            int e [][] [] = new int[i + j] [2][i + j];//incorrect, 36:18,23,43
            e [0] [1][2] = 0; e[1][1][1] = 0;//incorrect, 37:14,18
            return e;
        }
    }
    public static class F {
        public static Integer [][][] create(int i, int j) {//incorrect, 42:23
            int[][] [] f= new int[   0][1    ][    2    ]   ;
            return new Integer[i + j][i + j][0];
        }
    }
    public class G {
        public List<String> [] [] [] create(int i, int j) {//incorrect, 48:28,31,34
            //cannot build with this check - generic array creation error, but whitespaces still catched
            //List<String> g[][] [] = new List<String> [0][1][2];//incorrect 49:33,55
            //return new List<String>[i + j][i + j][0];//correct
            int g[][][] = new int [0][1][2];
            g[  0][0   ][   0   ]=0;
            g [0][0][0]=0;//incorrect 54:14
            g[0] [0][0]=0;//incorrect 55:17
            g [0] [0] [0]        =0;//incorrect 56:14,18,22
            return null;
        }

    }
    public class H {
        public List<Integer> create(int i, int j)     []      [][] {//incorrect, 62:46,53
            return null;
        }
    }
    
    Object someStuff4 = boolean [].class;//incorrect, 67:32
    String[][] someStuff5 = new String[4][9];
    String[][] someStuff6 = (java.lang.String  []  []) someStuff5;//incorrect, 69:46,50
    String[][] someStuff7 = (String [][]) someStuff5;//incorrect, 70:36
    
    //this is legal until allowLineBreaks is set to false 
    int someStuff8
    [];
    
    //this is legal until allowLineBreaks is set to false
    int[]
    someStuff81;
    
    //incorrect 81:40
    Integer someStuff9[][][] = (Integer [][][]) InputNoWhitespaceAfterArrayDeclarations2.F.create(1,2);
    
    //type arguments
    List<char[]> someStuff10;//correct
    List<char [][]> someStuff11;//incorrect 85:14
    List<InputNoWhitespaceAfterArrayDeclarations2.A []> someStuff12;//incorrect 86:52
    public void foo(java.util.List<? extends String[]> bar, Comparable<? super Object []> baz) { }//incorrect 87:86
    
    Integer someStuff13 = F.create(1,1)[0][0][0];
    Integer someStuff131 = F.create(1,1)  [0][0]   [0];//incorrect 90:41,49
    Object[] someStuff14 = (Object[]) null;
    Object[] someStuff15 = (Object  []  ) null;//incorrect 92:35
    
    byte someStuff16 = ((byte[]) someStuff4) [0];//incorrect 94:45
    
    public void bar() {
        if(someStuff15 instanceof Object  []) {//incorrect 97:41
    
        }
        if(someStuff15 instanceof Object[]  []) {//incorrect 100:43
          
        }
        if(someStuff15 instanceof Object[][]) {
          
        }
        Object[] a = null;

        if(a instanceof Object  []) {//incorrect
          
        }
        if(a instanceof Object[][]) {
          
        }
    }

}
