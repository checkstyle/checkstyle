/* Config:                                                                    //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 * lineWrappingIndentation = 8                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;       //indent:0 exp:0
import java.io.BufferedReader; //indent:0 exp:0
import java.io.IOException; //indent:0 exp:0
import java.io.InputStreamReader; //indent:0 exp:0

public class InputIndentationNewWithForceStrictCondition {                    //indent:0 exp:0
    private java.util.List < ? > [                                            //indent:4 exp:4
           ] arrayOfLists;                                                    //indent:11 exp:12 warn
    public Object[]                                                           //indent:4 exp:4
            variable1;                                                        //indent:12 exp:12
    int variable2                                                             //indent:4 exp:4
    [];                                                                       //indent:4 exp:12 warn
    int[]                                                                     //indent:4 exp:4
    		variable3;                                                        //indent:12 exp:12

	void test() throws IOException  {                                         //indent:4 exp:4
        BufferedReader bf =                                                   //indent:8 exp:8
                new BufferedReader(                                           //indent:16 exp:16
                new InputStreamReader(System.in) {                            //indent:16 exp:24 warn
                    int a = 0;                                                //indent:20 exp:28,32,36 warn
                });                                                           //indent:16 exp:24,28,32 warn

        String[] tmp1 = new String[42                                         //indent:8 exp:8
                                   + bf.toString().length()];                 //indent:35 exp:16 warn
        String[] tmp2 = new String[42                                         //indent:8 exp:8
                + bf.toString().length()];                                    //indent:16 exp:16
        String[] tmp3 = new String[42                                         //indent:8 exp:8
                                  ];                                          //indent:34 exp:16 warn
        String[] tmp4 = new String[42                                         //indent:8 exp:8
                ];                                                            //indent:16 exp:16
        String[] tmp5 = new String[                                           //indent:8 exp:8
                                   42];                                       //indent:35 exp:16 warn
        String[] tmp6 = new String[                                           //indent:8 exp:8
                42];                                                          //indent:16 exp:16
        String[] tmp7 = new String[14                                         //indent:8 exp:8
                                   + 14                                       //indent:35 exp:16 warn
                                   + 14];                                     //indent:35 exp:16 warn
        String[] tmp8 = new String[14                                         //indent:8 exp:8
                + 14                                                          //indent:16 exp:16
                + 14];                                                        //indent:16 exp:16
        int[] tmp9 = fun2(1, 1,                                               //indent:8 exp:8
                    1);                                                       //indent:20 exp:16 warn
        int[] tmp10 = fun2(2, 2,                                              //indent:8 exp:8
                2);                                                           //indent:16 exp:16
        final int[] tmp11 =                                                   //indent:8 exp:8
            fun2(3, 3, 3);                                                    //indent:12 exp:16 warn
        final int[] tmp12 =                                                   //indent:8 exp:8
                fun2(4, 4, 4);                                                //indent:16 exp:16
        int tmp13 = new String[42]                                            //indent:8 exp:8
                .length;                                                      //indent:16 exp:16
    }                                                                         //indent:4 exp:4

    char[] bar(String a, String b) {                                          //indent:4 exp:4
        char[] array1 = a != null ?                                           //indent:8 exp:8
                a.toCharArray() : null;                                       //indent:16 exp:16

        char[] array2 = bar(b,                                                //indent:8 exp:8
                a);                                                           //indent:16 exp:16

        return array1;                                                        //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    public void fun(String param1,                                            //indent:4 exp:4
    		String param2,                                                    //indent:12 exp:12
          Object[] param3) {                                                  //indent:10 exp:12 warn
    }                                                                         //indent:4 exp:4

    protected int[] arrayDeclarationWithGoodWrapping = new int[               //indent:4 exp:4
               ] {1, 2};                                                      //indent:15 exp:12 warn

    public int[] fun2(int a, int b, int c) {                                  //indent:4 exp:4
        return new int[0];                                                    //indent:8 exp:8
    }                                                                         //indent:4 exp:4
}                                                                             //indent:0 exp:0
