/*
LeftCurly
option = (default)eol
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class InputLeftCurlyCommentBeforeLeftCurly {

    void method1 ()
    /* violation reported */ { }
    // violation above ''{' at column 30 should be on the previous line'

    // violation 2 lines below ''{' at column 24 should be on the previous line'
    InputLeftCurlyCommentBeforeLeftCurly()
    /* I am comment */ {
        System.out.println("Hello CheckStyle");
    }

    InputLeftCurlyCommentBeforeLeftCurly(String name)
    { // violation ''{' at column 5 should be on the previous line'
        System.out.println("I am int" + name);
    }

    private void some() throws Exception {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException ex)
         /* warn */ {
        } // violation above ''{' at column 21 should be on the previous line'
    }

    // violation 2 lines below ''{' at column 36 should be on the previous line'
    public void multipleBlock()
    /*CheckStyle*/ /* Checkstyle*/ {
    }
    private class Node {
        int value;
        Node next;

        public
            /**** comment **********/ Node(int value) /*comment*/ {
            this.value=value;
        }
        // violation 2 lines below ''{' at column 36 should be on the previous line'
        public Node(int value, Node next)
        /*********** comment ****/ {
            this.value = value;
            this.next = next;
        }
    }

    public void array() {
        Integer[] array = null;
        /*50*/ for/*51*/ (/*52*/Integer/*53*/ i/*54*/:/*55*/ array/*56*/)/*57*/ {/*58*/
            /*59*/
        }/*60*/
    }
}

class checks implements check
        /* CheckStyle */ {
   // violation above ''{' at column 26 should be on the previous line'
    public static Set<String> processJavadocTag() {
        final Set<String> references = new HashSet<>();
        final String identifier = references.toString();
        for (Pattern pattern : new Pattern[]
                {Pattern.compile(".*"), Pattern.compile(".*")}) {

        }
        return references;
    }

    class C {
        void method1 ()
            /* 😂🥳 */ { }
        // violation above ''{' at column 22 should be on the previous line'

        void method2 ()
        /* 🥳🥳🥳🥳 */ /* 🥳🥳🥳🥳🥳 */ { }
        }
    // violation 2 lines above ''{' at column 32 should be on the previous line'
    private void method() {
        String b = "🧐🧐🧐ccvb";
        if (b.equals("🧐🧐")) {

        }
        if (b.equals("s🧐d🧐a")) {
        }
        while (b == "😂🥳") { /* ok */ }
    }
}

interface check {
}

/*15*/ class /*16*/InputAstTreeStringPrinterFullOfBlockComments /*49*/{/*17*/
    /*1*/public/*2*/ static/**/ String/*2*/ main/*2*/(/*4*/String/*2*/[/*2*/]/*2*/ args/*2*/)/**/ {
        /*31*/String /*32*/line /*33*/= /*34*/"/*I'm NOT comment*/blabla"/*35*/;/*36*/
        /*3*/String/*8*/.CASE_INSENSITIVE_ORDER/*0*/./*1*/equals/*2*/(/*3*/line/*4*/)/*4*/;/*6*/
        Integer[] array = null;
        /*50*/for/*51*/ (/*52*/Integer/*53*/ i/*54*/:/*55*/ array/*56*/)/*57*/ {/*58*/
        }/*60*/
        return line;
    }/*47*/
}/*48*/
