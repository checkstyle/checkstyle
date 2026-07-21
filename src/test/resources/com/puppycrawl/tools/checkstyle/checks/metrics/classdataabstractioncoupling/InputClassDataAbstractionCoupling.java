/*
ClassDataAbstractionCoupling
max = 0
excludedClasses = InnerClass
excludeClassesRegexps = (default)^$
excludedPackages = (default)


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import javax.naming.NamingException;
import java.util.*;

// violation below, 'Class Data Abstraction Coupling is 4 (max allowed is 0)'
public class InputClassDataAbstractionCoupling {
    // violation below, 'Class Data Abstraction Coupling is 1 (max allowed is 0)'
    private class InnerClass {
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

enum InnerEnum { // violation, 'Class Data Abstraction Coupling is 2 (max allowed is 0)'
    VALUE1;

    private InnerEnum()
    {
        map2 = new HashMap();
    }
    private Set map1 = new HashSet();
    private Map map2;
}

class InputThrows {

    public void get() throws NamingException, IllegalArgumentException {
        new java.lang.ref.ReferenceQueue<Integer>();
    }
}
