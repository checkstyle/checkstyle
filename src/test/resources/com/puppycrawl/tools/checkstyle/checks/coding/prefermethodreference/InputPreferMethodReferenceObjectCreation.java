package com.puppycrawl.tools.checkstyle.checks.coding.prefermethodreference;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Config = default
 * Object/Array creation cases
 */
public class InputPreferMethodReferenceObjectCreation {
    int field;
    // object creation
    Supplier<Object> s = () -> new Object(); // violation
    Function<Integer, Object> oc1 = a -> new int[a]; // violation
    Function<Integer, Object> oc2 = a -> new int[a][]; // violation
    Function<String, Example> oc3 = a -> new Example(a); // violation
    BiFunction<String, String, Example> oc4 = (a, b) -> new Example(a, b); // violation
    Supplier<ArrayList<Integer>> f20 = () -> new ArrayList<>();
    Supplier<ArrayList<Integer>> f21 = () -> new ArrayList<Integer>();

    // not violations
    Function<String, Object> noArg = a -> new Object();
    Function<String, Example> anon = a -> new Example(a){};
    Function<String, Example> methodCall = a -> new Example(a.trim());
    Function<String, Object> noArg2 = a -> new ArrayList(1);
    Function<String, Object> moreArgs = a -> new Example("", a);
    Function<String, Object> moreArgs2 = a -> new Example(a, a);
    Function<Integer, Object> arr1 = a -> new int[a][1];
    Function<Integer, Object> arr2 = a -> new int[1][];
    Function<Integer, Object> arr3 = a -> new int[]{};
    Function<Integer, Object> arr4 = a -> new int[field][];
    BiFunction<Integer, Integer, Object> twoDim = (a,b) -> new int[a][b];
    BiFunction<Integer, Integer, Object> twoDim2 = (a,b) -> new int[a][];

    private class Example {
        public Example(String b, String... varargs) {
        }
    }
}
