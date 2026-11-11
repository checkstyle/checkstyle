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
            return new int[3] [3]; // violation '']' is followed by whitespace.'
        }
    }

    public class B {
        public int create(int i, int j) [][] { // violation ''create' is followed by whitespace.'
            return new int     [3][i + j] ; // violation ''int' is followed by whitespace.'
        }
    }

    public class C {
        public int[][] create(int i, int j) {
            return new int[i + j][i + j];//correct
        }
    }

    public class D {
        public int[][]   [] create(int i, int j) { // violation '']' is followed by whitespace.'
            return new int  [ i + j ]    [ i + j ]               [ 0 ]     ;
            // 3 violations above:
            //          ''int' is followed by whitespace.'
            //          '']' is followed by whitespace.'
            //          '']' is followed by whitespace.'
        }
    }

    public class E {
        public int create(int i, int j, int   [][] k)[] [][] {
            // 2 violations above:
            //          ''int' is followed by whitespace.'
            //          '']' is followed by whitespace.'
            int e [][] [] = new int[i + j] [2][i + j];
            // 3 violations above:
            //          ''e' is followed by whitespace.'
            //          '']' is followed by whitespace.'
            //          '']' is followed by whitespace.'
            e [0] [1][2] = 0; e[1][1][1] = 0;
            // 2 violations above:
            //          ''e' is followed by whitespace.'
            //          '']' is followed by whitespace.'
            return e;
        }
    }
    public class G {
        public List<String> [] [] [] create(int i, int j) {
            // 3 violations above:
            //          ''>' is followed by whitespace.'
            //          '']' is followed by whitespace.'
            //          '']' is followed by whitespace.'
            //cannot build with check - generic array creation error, but whitespaces still caught
            //List<String> g[][] [] = new List<String> [0][1][2];//incorrect 49:33,55
            //return new List<String>[i + j][i + j][0];//correct
            int g[][][] = new int [0][1][2]; // violation ''int' is followed by whitespace.'
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
            // 2 violations above:
            //          ''create' is followed by whitespace.'
            //          '']' is followed by whitespace.'
            return null;
        }
    }
}
