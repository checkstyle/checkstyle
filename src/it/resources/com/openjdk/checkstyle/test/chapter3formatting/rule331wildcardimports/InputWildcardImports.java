package com.openjdk.checkstyle.test.chapter3formatting.rule331wildcardimports;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.*; // violation 'Using the '.*' form of import should be avoided'
import static java.lang.Math.*;
// violation above 'Using the '.*' form of import should be avoided'

public class InputWildcardImports {

    private int value;

    private LocalDate date;

    private List<String> values;

    public int getValue() {
        return value;
    }
}
