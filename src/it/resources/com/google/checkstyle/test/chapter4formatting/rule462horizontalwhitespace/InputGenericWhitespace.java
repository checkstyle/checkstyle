package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.Collections;
class InputGenericWhitespace implements Comparable<InputGenericWhitespace>, Serializable
{
    void meth()
    {
        List<Integer> x = new ArrayList<Integer>();
        List<List<Integer>> y = new ArrayList<List<Integer>>();
        List < Integer > a = new ArrayList < Integer > (); // warn
        List < List < Integer > > b = new ArrayList < List < Integer > > (); // warn
    }

    public int compareTo(InputGenericWhitespace aObject)
    {
        return 0;
    }

    public static <T> Callable<T> callable(Runnable task, T result)
    {
        return null;
    }

    public static<T>Callable<T> callable2(Runnable task, T result) // warn
    {
        Map<Class<?>, Integer> x = new HashMap<Class<?>, Integer>();
        for (final Map.Entry<Class<?>, Integer> entry : x.entrySet()) {
            entry.getValue();
        }
        Class<?>[] parameterClasses = new Class<?>[0];
        return null;
    }
    public int getConstructor(Class<?>... parameterTypes)
    {
        Collections.<Object>emptySet();
        Collections. <Object> emptySet(); // warn
        return 666;
    }
    
    <T> InputGenericWhitespace(List<T> things, int i)
    {
    }

    public <T> InputGenericWhitespace(List<T> things)
    {
    }

    public interface IntEnum {
    }

    public static class IntEnumValueType<E extends Enum<E> & IntEnum> {
    }

    public static class IntEnumValueType2<E extends Enum<E>& IntEnum> { // warn
    }

    public static class IntEnumValueType3<E extends Enum<E>  & IntEnum> { // warn
    }
}
