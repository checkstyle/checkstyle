package com.puppycrawl.tools.checkstyle.checks.annotation;

import java.util.ArrayList;
import java.util.List;

public class InputSuppressWarningsValuePair
{
    public static final String UNCHECKED = "unchecked";

    public static void test() {
        @SuppressWarnings(value = UNCHECKED)
        final List<String> dummyOne = (List<String>) new ArrayList();
    }
}
