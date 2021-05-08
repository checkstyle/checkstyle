package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

/*
 * Config: default
 */
public class InputIllegalTypeSimilarClassName {
        private TreeSet example;

        private static class TreeSet { // ok
        }
}
