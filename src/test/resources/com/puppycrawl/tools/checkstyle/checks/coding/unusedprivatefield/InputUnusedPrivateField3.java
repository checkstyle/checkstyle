/*
UnusedPrivateField

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateField3 {
    public int publicField; //ok, as the field is public
    private int usedField; // ok,private field is used
    private int copyfield;

    void setCopyfield(){
        int copy = copyfield;
    }

    private static final int CONSTANT = 10;
    void useField() {
        System.out.println(usedField);
    }

    void methodWithLocal() {
        int localVar = 10;
        System.out.println(localVar);
    }

    private final char[] quadrant = new char[10];

    private void fallbackSimpleSort(int[] fmap, int[] eclass, int lo, int hi) {
        System.out.println(eclass[lo] + fmap[hi]);
    }

    private void fallbackQSort3(int[] fmap, int[] eclass, int loSt, int hiSt) {
        fallbackSimpleSort(fmap, eclass, loSt, hiSt);
    }

    private int[] eclass;

    private int[] getEclass() {
        return eclass == null
            ? (eclass = new int[quadrant.length / 2]) : eclass;
    }

}
