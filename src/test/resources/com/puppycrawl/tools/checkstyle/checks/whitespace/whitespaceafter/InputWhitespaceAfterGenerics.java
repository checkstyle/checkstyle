/*
WhitespaceAfter
tokens = (default)COMMA, SEMI, TYPECAST, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, \
         LITERAL_DO, LITERAL_FOR, DO_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.util.Collection;
import java.util.Map;

public class InputWhitespaceAfterGenerics<A, B extends Collection<?>,
       C extends D&E, F extends Collection<? extends InputWhitespaceAfterGenerics[]>>
{
}

//No whitespace after commas
class BadCommas < A,B,C extends Map < A,String > > // 3 violations
{
    private java.util.Hashtable < Integer, D > p =
        new java.util.Hashtable < Integer, D > ();
}

class Wildcard
{
    public static void foo(Collection < ? extends Wildcard[] > collection) {
        // A statement is important in this method to flush out any
        // issues with parsing the wildcard in the signature
        collection.size();
    }
}

// we need these interfaces for generics
interface D {
}
interface E {
}
