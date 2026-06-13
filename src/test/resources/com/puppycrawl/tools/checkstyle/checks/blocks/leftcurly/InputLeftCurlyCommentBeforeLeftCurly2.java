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

import org.junit.jupiter.api.function.ThrowingConsumer;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;
import java.util.List;

public class InputLeftCurlyCommentBeforeLeftCurly2 {

    private long[] countList;

    void method1() /* comment
    comment */ {
    }
    // violation 2 lines above ''{' at column 16 should be on the previous line'

    // violation 2 lines below ''{' at column 16 should be on the previous line'
    void method2() /********
    comment */ {
        if (!Arrays.equals(this.countList, countList)
            /* || !Arrays.equals(this.whereCreated, other.whereCreated) */ ) {
        }
    }

    void method3() /*****
    ********/ { }
    // violation above ''{' at column 15 should be on the previous line'

    InputLeftCurlyCommentBeforeLeftCurly2() /**************/ { }

    InputLeftCurlyCommentBeforeLeftCurly2(int data) /*
     ****** comment *********/ { }
    // violation above ''{' at column 32 should be on the previous line'
}

class Class {
    private
    class Node /************/{
        int data;
        Node next;
        Node(int data) /* comment */
        { // violation ''{' at column 9 should be on the previous line'
            this.data = data;
        }
    }

    String s = "🧐  🧐";
    private void foo3(String s) {
        /*🧐 🧐 🧐*/  /* comment */ if ("🧐".isEmpty()) {
        }
    }

    List<ThrowingConsumer<InetSocketAddress>> targets = List.of(
            /* 0 */  (a) -> {Socket s = new Socket();},
            // violation above ''{' at column 29 should have line break after'
            /* 0 */  (b) -> {
                Socket s = new Socket();
            }
    );
}

class Nothing {
    void method() {
        int a = 9;int b = 9;
        if(a == 0 && b == 0
         /* || a==3 */) {}
    }

    public void test2(String
                      /* CHECKSTYLE */ ...para) {
    }

    public void test3(String line) {

        int index=0;
        if (line.regionMatches(index, "/**", 0, "/**".length())) {
            index += 2;
        }
        else if (line.regionMatches(index, "*/", 0, 2)) {
            index++;
        }
    }
}
