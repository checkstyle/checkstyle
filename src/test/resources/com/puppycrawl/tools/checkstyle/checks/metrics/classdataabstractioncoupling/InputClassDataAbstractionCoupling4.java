/*
ClassDataAbstractionCoupling
max = 0
excludedClasses = InnerClass
excludeClassesRegexps =
excludedPackages = (default)


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;

public class InputClassDataAbstractionCoupling4 { // violation
    private class InnerClass { // violation
        public List _list = new ArrayList();
    }

    private class AnotherInnerClass {
        public String _string = "";
    }

    public Set _set = /*block comment*/new HashSet();
    public Map _map = new HashMap();
    public String _string = "";
    public int[] _intArray = new int[0];
    public InnerClass _innerClass = new InnerClass();
    public AnotherInnerClass _anotherInnerClass = new AnotherInnerClass();

    public void foo() throws NamingException {
    }

}

enum InnerEnum4 { // violation
    VALUE1;

    private InnerEnum4()
    {
        map2 = new HashMap();
    }
    private Set map1 = new HashSet();
    private Map map2;
}

class InputThrows4 {

    public void get() throws NamingException, IllegalArgumentException {
        new java.lang.ref.ReferenceQueue<Integer>();
    }
}
