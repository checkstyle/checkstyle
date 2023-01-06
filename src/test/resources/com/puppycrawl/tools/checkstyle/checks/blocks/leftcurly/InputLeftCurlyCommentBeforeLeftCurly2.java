/*
LeftCurly
option = eol
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

import org.junit.jupiter.api.function.ThrowingConsumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class InputLeftCurlyCommentBeforeLeftCurly2 {

    private long[] countList;

    void method1() /* comment
    comment */ { // ok
    }

    void method2() /********
    comment */ { // ok
        if (!Arrays.equals(this.countList, countList)
            /* || !Arrays.equals(this.whereCreated, other.whereCreated) */ ) { // ok
        }
    }

    void method3() /*****
    ********/ { } // ok


    InputLeftCurlyCommentBeforeLeftCurly2() /**************/ { } // ok

    InputLeftCurlyCommentBeforeLeftCurly2(int data) /*
     ****** comment *********/ { } // ok
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
        /*🧐 🧐 🧐*/  /* comment */ if ("🧐".isEmpty()) { // ok
        }
    }

    List<ThrowingConsumer<InetSocketAddress>> targets = List.of(
            /* 0 */  (a) -> {Socket s = new Socket();},
            // violation above ''{' at column 29 should have line break after'
            /* 0 */  (b) -> { // ok
                Socket s = new Socket();
            }
    );
}

class Nothing {
    void method() {
        int a = 9;int b = 9;
        if(a == 0 && b == 0
         /* || a==3 */) {} //ok
    }

    public void test2(String
                      /* CHECKSTYLE */ ...para) { // ok
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

    private static Integer[] getLinesWithWarnAndCheckComments(String aFileName,
                                                              final int tabWidth)
            throws IOException {
        final List<Integer> result = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(
                Paths.get(aFileName), StandardCharsets.UTF_8)) {
            int lineNumber = 1;
        }
        return result.toArray(new Integer[0]);
    }

    void MethodTest() /* comment */
    /* comment */ { // violation '{' at column 19 should be on the previous line'
    }

    void unknownMethod() // comment
    { // violation ''{' at column 5 should be on the previous line'
    }
}
