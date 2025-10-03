/*
GenericWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class InputGenericWhitespace {

    List<String>items; // violation ''>' is followed by an illegal character.'

    List<String>    withTab; // violation ''>' is followed by an illegal character.'

    // violation below ''>' is followed by an illegal character.'
    List<String>      attributes = new ArrayList<String>();

    // violation below ''>' is followed by an illegal character.'
    Hashtable<String, Object>  ret = new Hashtable<>();

    final Map<String, String>
        comeMapWithLongName = new HashMap
            <String, String>();

    // violation below ''>' is followed by an illegal character.'
    public void fieldMappers(List<List<?>>  fieldMappers) {
        for (List<?> mapper : fieldMappers) {
            System.out.println(mapper);
        }
    }

    static class InputIndentationFromGuava<K, V> extends AbstractMap<K, V>
        implements ConcurrentMap<K, V> {

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return false;
    }

    @Override
    public V replace(K key, V value) {
        return null;
    }

    static class ValueReference<T1, T2> {

    }

    static class ReferenceEntry<T1, T2> {

    }

    static class Segment<T1, T2> {

        protected Object valueReferenceQueue;

    }

    static class StrongAccessEntry<T1, T2> {

        public StrongAccessEntry(T1 key, int hash, ReferenceEntry<T1, T2> next) {

        }

    }

    static class StrongValueReference<T1, T2> {

        public StrongValueReference(int value) {

        }

    }

    static class WeightedStrongValueReference<T1, T2> {

        public WeightedStrongValueReference(int value, int weight) {
        }

    }

    static class SoftValueReference<T1, T2> {

        public SoftValueReference(Object valueReferenceQueue, int value,
                                  ReferenceEntry<Integer, Integer> entry) {
        }

    }

    static class WeightedSoftValueReference<T1, T2> {
    }

    static class WeakValueReference<T1, T2> {
    }

    static class WeightedWeakValueReference<T1, T2> {
    }

}
}
