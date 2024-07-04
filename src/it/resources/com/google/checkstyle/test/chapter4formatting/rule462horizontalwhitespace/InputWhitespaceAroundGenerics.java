package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.util.Collection;
import java.util.Map;

public class InputWhitespaceAroundGenerics
{

}

//No whitespace after commas
class BadCommas < A, B, C extends Map < A, String > >
// 7 violations above:
//                    ''\<' is followed by whitespace.'
//                    ''\<' is preceded with whitespace.'
//                    ''\<' is followed by whitespace.'
//                    ''\<' is preceded with whitespace.'
//                    ''\>' is followed by whitespace.'
//                    ''\>' is preceded with whitespace.'
//                    ''\>' is preceded with whitespace.'
{
    private java.util.Hashtable < Integer, D > p =
    // 3 violations above:
    //                    ''\<' is followed by whitespace.'
    //                    ''\<' is preceded with whitespace.'
    //                    ''\>' is preceded with whitespace.'
        new java.util.Hashtable < Integer, D >();
    // 3 violations above:
    //                    ''\<' is followed by whitespace.'
    //                    ''\<' is preceded with whitespace.'
    //                    ''\>' is preceded with whitespace.'
}

class Wildcard
{
    public static void foo(Collection < ? extends Wildcard[] > collection) {
        // 3 violations above:
        //                    ''\<' is followed by whitespace.'
        //                    ''\<' is preceded with whitespace.'
        //                    ''\>' is preceded with whitespace.'
        // A statement is important in this method to flush out any
        // issues with parsing the wildcard in the signature
        collection.size();
    }

    public static void foo2(Collection<?extends Wildcard[]> collection) {
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
