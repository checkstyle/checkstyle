package com.puppycrawl.tools.checkstyle.metrics;

import javax.naming.*;
import java.util.*;

public class ClassCouplingCheckTestInput {
    private class InnerClass { //singleline comment
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

enum InnerEnum {
    VALUE1;

    private InnerEnum()
    {
        map2 = new HashMap();
    }
    private Set map1 = new HashSet();
    private Map map2;
}
