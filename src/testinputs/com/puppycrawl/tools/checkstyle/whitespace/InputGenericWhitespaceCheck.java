package com.puppycrawl.tools.checkstyle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class InputSimpleGenerics implements Comparable<InputSimpleGenerics>, Serializable
{
    void meth()
    {
        List<Integer> x = new ArrayList<Integer>();
        List<List<Integer>> y = new ArrayList<List<Integer>>();
        List < Integer > a = new ArrayList < Integer > ();
        List < List < Integer > > b = new ArrayList < List < Integer > > ();
    }

    public int compareTo(InputSimpleGenerics aObject)
    {
        return 0;
    }
}
