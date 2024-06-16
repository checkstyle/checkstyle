package com.google.checkstyle.test.chapter3filestructure.rule332nolinewrap; //ok
import com.google.checkstyle.test.chapter3filestructure.toolongpackagetotestcoveragegooglesjavastylerule.*; //ok
import java.io.*; //ok

final class InputLineLength
{
    // Long line ---------------------------------------------------------------------------------------- //warn

    private int[] mInts = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, }; //warn
}
