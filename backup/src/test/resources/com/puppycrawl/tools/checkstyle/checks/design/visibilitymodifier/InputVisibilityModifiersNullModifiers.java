/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = true
immutableClassCanonicalNames = (default)java.io.File, java.lang.Boolean, java.lang.Byte, \
                               java.lang.Character, java.lang.Double, java.lang.Float, \
                               java.lang.Integer, java.lang.Long, java.lang.Short, \
                               java.lang.StackTraceElement, java.lang.String, \
                               java.math.BigDecimal, java.math.BigInteger, \
                               java.net.Inet4Address, java.net.Inet6Address, \
                               java.net.InetSocketAddress, java.net.URI, java.net.URL, \
                               java.util.Locale, java.util.UUID
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import java.io.*;
import java.util.*;

class InputVisibilityModifiersNullModifiers {
    private final Set<Map.Entry<Integer,Integer>> s;
    public InputVisibilityModifiersNullModifiers(Set<Map.Entry<Integer,Integer>> s) {this.s = s;}
    public Iterator<Map.Entry<String,String>> iterator() {
        return new Iterator<Map.Entry<String,String>>() {
            Iterator<Map.Entry<Integer,Integer>> i = s.iterator(); // violation
            public boolean hasNext() {return i.hasNext();}
            public Map.Entry<String,String> next() {
                return null;
            }
            public void remove() {i.remove();}
        };
    }
}


