package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.Collections;
class InputGenericWhitespaceDefault implements Comparable<Object>, Serializable
{
    void meth()
    {
        List<Integer> x = new ArrayList<Integer>();
        List<List<Integer>> y = new ArrayList<List<Integer>>();
        List < Integer > a = new ArrayList < Integer > ();
        List < List < Integer > > b = new ArrayList < List < Integer > > ();
    }
    //always 0
    public int compareTo(Object aObject)
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
        Class<?>[] parameterClasses = new Class<?>[0];
        return null;
    }
    public int getConstructor(Class<?>... parameterTypes)
    {
        Collections.<Object>emptySet();
        Collections. <Object> emptySet();
        return 666;
    }

    <T> InputGenericWhitespaceDefault(List<T> things, int i)
    {
    }

    public <T> InputGenericWhitespaceDefault(List<T> things)
    {
    }

    public interface IntEnum { /*inner enum*/
    }

    public static class IntEnumValueType<E extends Enum<E> & IntEnum> {
    }

    public static class IntEnumValueType2<E extends Enum<E>& IntEnum> {
    }

    public static class IntEnumValueType3<E extends Enum<E>  & IntEnum> {
    }

    public static class IntEnumValueType4<T extends Comparable<List<T>> & IntEnum> {
    }

    public void beforeAndAfter() {
        List
<
Integer> x = new ArrayList<Integer
>();
        List
        <Integer> y = new ArrayList<Integer
        >();
        Map<Class<?>, Integer> a = new HashMap<Class<?>, Integer>();
        Map<Class<?>, Integer> b = (Map<Class<?>, Integer>) a;
    }
}
interface SupplierFunction<T> extends Map<List<T>, T> {}
