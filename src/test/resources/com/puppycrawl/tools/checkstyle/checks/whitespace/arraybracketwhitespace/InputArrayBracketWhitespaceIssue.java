/*
ArrayBracketWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketwhitespace;
public class InputArrayBracketWhitespaceIssue {
    static class Rect { Rect(int a, int b, int c, int d) {} }
    static class V { int getWidth() { return 1; } int getHeight() { return 1; } }

    public void test() {
        int[] pos = new int[2];
        V v = new V();
        Object r = new Rect(
                pos[0], pos[1], pos[0]+v.getWidth(), pos[1]+v.getHeight()); // 2 violations
    }

    public void test2() {
        Object[] rcs = new Object[1];
        int i = 0;
        Object resources;
        resources = grabResources(
                new Object[] {(Object) rcs[i]}); // violation, ''\]' is not followed by whitespace.'
    }

    Object grabResources(Object[] a) { return null; }
}
