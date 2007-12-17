package com.puppycrawl.tools.checkstyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

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

    public static <T> Callable<T> callable(Runnable task, T result)
    {
        return null;
    }

    public static<T>Callable<T> callable2(Runnable task, T result)
    {
        Map<Class<?>, Integer> x = new HashMap<Class<?>, Integer>();
        for (final Map.Entry<Class<?>, Integer> entry : x.entrySet()) {
            entry.getValue();
        }
        return null;
    }
}
