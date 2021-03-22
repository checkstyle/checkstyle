package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Config:
 * allowMultipleEmptyLinesInsideClassMembers = false
 */
public class InputEmptyLineSeparatorNewMethodDef {
    HashMap<String, String> myMap =
            new HashMap<>();

    void processOne() { // ok

        int a;

        int b;

        new Map() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public Object get(Object key) {
                return null;
            }

            @Override
            public Object put(Object key, Object value) {
                return null;
            }

            @Override
            public Object remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<Object> keySet() {
                return null;
            }

            @Override
            public Collection<Object> values() {
                return null;
            }

            @Override
            public Set<Entry<Object, Object>> entrySet() {
                return null;
            // violation



            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }
        }.putAll(myMap);
    }
}
