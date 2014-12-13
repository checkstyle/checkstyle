package com.puppycrawl.tools.checkstyle.annotation;

import java.util.List;
import java.util.ArrayList;

public class SuppressWarningsConstants
{
    public static final String UNCHECKED = "unchecked";

    public static void test() {
        @SuppressWarnings(UNCHECKED)
        final List<String> dummyOne = (List<String>) new ArrayList();
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        final List<String> dummyTwo = (List<String>) new ArrayList();
    }
}
