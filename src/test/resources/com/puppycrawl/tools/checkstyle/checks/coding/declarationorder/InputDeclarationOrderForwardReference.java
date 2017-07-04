package com.puppycrawl.tools.checkstyle.checks.coding.declarationorder;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class InputDeclarationOrderForwardReference {

    public static final int TAB_LENGTH = 4;
    private static final ArrayList<String> EMPTY_ARRAY_LIST = new ArrayList<String>();
    public static final int MAX_INT = Integer.MAX_VALUE; // violation
    public static final int MAX_BYTE = Byte.MIN_VALUE; // violation
    public static final int ROWS = 18; // violation
    public static final int COLUMNS = 18; // violation
    public static final int TYPE_SIZE = 12; // violation
    public static final int TABLE_SIZE = 184; // violation
    public static final int INFRASTRUCTURE_SIZE = TYPE_SIZE
        + MAX_BYTE
        + TABLE_SIZE;
    public  static final int MAX_LINE_LENGTH = 96 + TAB_LENGTH;

    public static final double MIN_MATCH = 0.60; // violation
    public static final double EXACT_CLASS_NAME_MATCH = MIN_MATCH + 0.1;

    private static final String COMMON_PART = "common_part";
    public static final String FIRST = COMMON_PART + "2";

    private static final String SECOND = String.valueOf(TokenTypes.ELIST) + FIRST;
    public static final String THIDRD = FIRST;

    private static int ID = 5;
    public static final String FOURTH = "1"
        + ID
        + COMMON_PART;

    private int a = 1;
    public int b = a + 2;

    public static void foo1() {}
    public static final double MAX = 0.60; // violation
    public static void foo2() {}

    void foo3() {
        int i = 5;
    }
}
