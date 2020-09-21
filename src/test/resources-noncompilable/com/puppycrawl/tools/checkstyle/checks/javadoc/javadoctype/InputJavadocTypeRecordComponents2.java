//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/* Config:
 *
 * scope = private
 * allowMissingParamTags = false
 * allowUnknownTags = false
 */

import java.util.HashMap;
import java.util.List;

/**
 * My new record.
 *
 * @param value Sponge Bob rules the world!
 */
public record InputJavadocTypeRecordComponents2(String value) { // ok
}

/**
 * @param myString my string
 * @param myInt my int
 */
record MyRecord1(String myString, Integer myInt){}

/**
 * @author Nick Mancuso
 * @param myHashMap my hash map!
 */
record MyRecord2(HashMap<String, String> myHashMap){}

/**
 *
 */
record MyRecord3<X>(){} // violation

/**
 *
 * @param x // violation
 */
record MyRecord4(){}

/**
 * @author X
 * @param <X>
 */
record MyRecord5<X>(){}

/**
 * @param notMyString // violation
 * @param <X>
 */
record MyRecord6<X>(String myString, int myInt){} // violation x2

/**
 *
 * @param x // violation
 */
record MyRecord7(List<String>myList){} // violation

/**
 * @author X
 * @param <X>
 * @param <T>
 */
record MyRecord8<X, T>(String X){} // violation

/**
 * @param notMyString // violation
 * @param <X>
 */
record MyRecord9<X, T>(String myString, int myInt){} // violation x3
