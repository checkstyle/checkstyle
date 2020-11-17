package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import javax.naming.*;
import java.util.*;
import java.util.stream.*;

public class InputClassFanOutComplexity {
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

class InputThrows {

    public void get() throws NamingException, IllegalArgumentException {
        new java.lang.ref.ReferenceQueue<Integer>();
    }
}

class InputMultiDimensionalArray {
    public  Object[][] get() {
        return new Object[][]{};
    }
}

class InputCollectionsExt {
    private Collection col; // ok
    private EnumSet enumSet; // ok
    private LinkedHashMap map; // ok
    private LinkedHashSet set; // ok
}

class InputOptionals {
    private Optional<Long> op1; // ok
    private OptionalInt op2; // ok
    private OptionalLong op3; // ok
    private OptionalDouble op4; // ok
}

class InputStreams {
    private Stream s1; // ok
    private IntStream s2; // ok
    private LongStream s3; // ok
    private DoubleStream s4; // ok
}
