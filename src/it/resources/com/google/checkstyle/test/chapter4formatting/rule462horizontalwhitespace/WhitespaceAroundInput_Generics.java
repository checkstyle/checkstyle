package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.util.Collection;
import java.util.Map;

public class WhitespaceAroundInput_Generics
{
    
}

//No whitespace after commas
class BadCommas < A,B,C extends Map < A,String > > //warn
{
    private java.util.Hashtable < Integer, D > p = //warn
        new java.util.Hashtable < Integer, D > (); //warn
}

class Wildcard
{
    public static void foo(Collection < ? extends Wildcard[] > collection) { //warn
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
