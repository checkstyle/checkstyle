package com.puppycrawl.tools.checkstyle.annotation;

import java.util.ArrayList;
import java.util.List;

public class SuppressWarningsValuePair
{
    public static final String UNCHECKED = "unchecked";

    public static void test() {
        @SuppressWarnings(value = UNCHECKED)
        final List<String> dummyOne = (List<String>) new ArrayList();
    }
}
