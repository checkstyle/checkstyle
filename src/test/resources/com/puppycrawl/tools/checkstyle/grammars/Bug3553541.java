package com.puppycrawl.tools.checkstyle.grammars;

import java.util.List;
import java.util.ArrayList;

// Demonstrates the bug #3553541
class Bug3553541
{
    List<? super long[]> a;
    {
        a = new ArrayList<long[]>();
    }
    List<? super Integer[]> b;
    {
        b = new ArrayList<Integer[]>();
    }
}
