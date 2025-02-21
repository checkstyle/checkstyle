/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, DOT, TYPECAST, \
         ARRAY_DECLARATOR, INDEX_OP, LITERAL_SYNCHRONIZED, METHOD_REF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterWithEmoji {

    String txt = new String ("sd🤩🎄😂 " );
    public String foo() {
        String []   s =  { "🎄😂", // 2 violations
                        "🎄😂12wq"
        };

        for (int i = 0; i < s.length; i++) {
            char[]c = "🤩🎄".toCharArray();

        /* 👉🏻😆*/ char  []c2= "🤩🎄".toCharArray(); // violation ''char' is followed by whitespace.'
        }
        return "😅🧐 dsad ";
    }
    public String foo2() {
        String str = (@ MyAnnotation String) "🤩dsa😂adsad"; // 2 violations
        String str3 = str + "😂" + "sadsa" +"😅🧐" +    " " ;
        return("  🎄😂  ");
    }

    public String foo3() {

        return  ! "🎄". isEmpty() ?"dsa😂a":  "😂..😅" ; // 2 violations
    }

    public String[] foo4 () {
        return new String[] {
          "sd😂"+  "😅🧐",
                "👉🏻"
        };
    }

}
