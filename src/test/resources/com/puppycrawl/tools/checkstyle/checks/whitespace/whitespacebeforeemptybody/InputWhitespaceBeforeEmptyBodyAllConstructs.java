/*
WhitespaceBeforeEmptyBody
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF, \
         CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF, \
         LITERAL_WHILE, LITERAL_FOR, LITERAL_DO, \
         STATIC_INIT, INSTANCE_INIT, \
         LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_SYNCHRONIZED, LITERAL_SWITCH, \
         LAMBDA, LITERAL_NEW


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodyAllConstructs {
    // violation below 'Whitespace is not present before the empty body'
    InputWhitespaceBeforeEmptyBodyAllConstructs(){}

    void method(){} // violation 'Whitespace is not present before the empty body of 'method''

    class Inner{} // violation 'Whitespace is not present before the empty body of 'Inner''

    interface InnerInterface{}
    // violation above, 'Whitespace is not present before the empty body of 'InnerInterface''

    enum InnerEnum{} // violation 'Whitespace is not present before the empty body of 'InnerEnum''

    @interface InnerAnnot{}
    // violation above, 'Whitespace is not present before the empty body of 'InnerAnnot''

    static{} // violation 'Whitespace is not present before the empty body of 'static''

    Runnable r = () ->{}; // violation 'Whitespace is not present before the empty body of 'lambda''

    void loops() {
        while (true){} // violation 'Whitespace is not present before the empty body of 'while''
    }

    void loop1() {
        do{} while (true); // violation 'Whitespace is not present before the empty body of 'do''
    }

    void loop2() {
        for (int i = 0; i < 1; i++){}
        // violation above, 'Whitespace is not present before the empty body of 'for''
    }

    void testTryCatchFinally() {
        try{} // violation 'Whitespace is not present before the empty body of 'try''
        catch (Exception e){}
        // violation above, 'Whitespace is not present before the empty body of 'catch''
        finally{} // violation 'Whitespace is not present before the empty body of 'finally''
    }

    void testSynchronized() {
        synchronized (this){}
        // violation above, 'Whitespace is not present before the empty body of 'synchronized''
    }

    void testSwitch(int x) {
        switch (x){} // violation 'Whitespace is not present before the empty body of 'switch''
    }

    record InnerRecord(){}
    // violation above, 'Whitespace is not present before the empty body of 'InnerRecord''

    record InnerRecordWithCompact(int x) {
        // violation below 'Whitespace is not present before the empty body'
        InnerRecordWithCompact{}
    }

    Object anon = new Object(){};
    // violation above, 'Whitespace is not present before the empty body of 'anonymous class''
}
