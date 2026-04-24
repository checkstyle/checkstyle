package com.openjdk.checkstyle.test.chapter3formatting.rule331wildcardimports;

import java.io.*;
import java.util.*; // violation 'Using the '.*' form of import should be avoided - java.util.*.'
import static java.lang.Math.*;
// violation above 'Using the '.*' form of import should be avoided - java.lang.Math.*.'

public class InputWildcardImports {

    private int value;

    public int getValue() {
        return value;
    }
}
