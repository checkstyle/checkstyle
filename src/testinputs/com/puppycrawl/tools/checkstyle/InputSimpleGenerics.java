package com.puppycrawl.tools.checkstyle;

import java.util.ArrayList;
import java.util.List;

class InputSimpleGenerics
{
    void meth()
    {
        List<Integer> x = new ArrayList<Integer>();
        List<List<Integer>> y = new ArrayList<List<Integer>>();
    }
}
