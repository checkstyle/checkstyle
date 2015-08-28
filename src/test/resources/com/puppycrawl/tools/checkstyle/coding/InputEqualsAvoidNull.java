package com.puppycrawl.tools.checkstyle.coding;
import java.util.Map;
public class InputEqualsAvoidNull {

    public boolean equals(Object o) {
    return false;
    }
    // antoher comment
    /**
     * methods that should get flagged
     * @return
     */
    public void flagForEquals() {

        Object o = new Object();
        String s = "pizza";

        o.equals("hot pizza")/*comment test*/;

        o.equals(s = "cold pizza");

        o.equals(((s = "cold pizza")));

        o.equals("cheese" + "ham" + "sauce");

        o.equals(("cheese" + "ham") + "sauce");

        o.equals((("cheese" + "ham")) + "sauce");
    }

    /**
     * methods that should get flagged
     */
    public void flagForEqualsIgnoreCase() {
        String s = "pizza";

        s.equalsIgnoreCase("hot pizza");

        s.equalsIgnoreCase(s = "cold pizza");

        s.equalsIgnoreCase(((s = "cold pizza")));

        s.equalsIgnoreCase("cheese" + "ham" + "sauce");

        s.equalsIgnoreCase(("cheese" + "ham") + "sauce");

        s.equalsIgnoreCase((("cheese" + "ham")) + "sauce");
    }

    /**
     * methods that should get flagged
     */
    public void flagForBoth() {
        Object o = new Object();
        String s = "pizza";

        o.equals("hot pizza");

        o.equals(s = "cold pizza");

        o.equals(((s = "cold pizza")));

        o.equals("cheese" + "ham" + "sauce");

        o.equals(("cheese" + "ham") + "sauce");

        o.equals((("cheese" + "ham")) + "sauce");

        s.equalsIgnoreCase("hot pizza");

        s.equalsIgnoreCase(s = "cold pizza");

        s.equalsIgnoreCase(((s = "cold pizza")));

        s.equalsIgnoreCase("cheese" + "ham" + "sauce");

        s.equalsIgnoreCase(("cheese" + "ham") + "sauce");

        s.equalsIgnoreCase((("cheese" + "ham")) + "sauce");
    }


    /**
     * methods that should not get flagged
     *
     * @return
     */
    public void noFlagForEquals() {
        Object o = new Object();
        String s = "peperoni";

        o.equals(s += "mushrooms");

        (s = "thin crust").equals("thick crust");

        (s += "garlic").equals("basil");

        ("Chicago Style" + "NY Style").equals("California Style" + "Any Style");

        equals("peppers");

        "onions".equals(o);

        o.equals(new Object());

        o.equals(equals(o));

        equals("yummy");

        new Object().equals("more cheese");

        InputEqualsAvoidNullOutter outter = new InputEqualsAvoidNullOutter();

        outter.new InputEqualsAvoidNullInner().equals("eat pizza and enjoy inner classes");
    }

    /**
     * methods that should not get flagged
     */
    public void noFlagForEqualsIgnoreCase() {
        String s = "peperoni";
        String s1 = "tasty";

        s.equalsIgnoreCase(s += "mushrooms");

        s1.equalsIgnoreCase(s += "mushrooms");

        (s = "thin crust").equalsIgnoreCase("thick crust");

        (s += "garlic").equalsIgnoreCase("basil");

        ("Chicago Style" + "NY Style").equalsIgnoreCase("California Style" + "Any Style");

        "onions".equalsIgnoreCase(s);

        s.equalsIgnoreCase(new String());

        s.equals(s1);

        new String().equalsIgnoreCase("more cheese");

    }

    public void noFlagForBoth() {
        Object o = new Object();
        String s = "peperoni";
        String s1 = "tasty";

        o.equals(s += "mushrooms");

        (s = "thin crust").equals("thick crust");

        (s += "garlic").equals("basil");

        ("Chicago Style" + "NY Style").equals("California Style" + "Any Style");

        equals("peppers");

        "onions".equals(o);

        o.equals(new Object());

        o.equals(equals(o));

        equals("yummy");

        new Object().equals("more cheese");

        InputEqualsAvoidNullOutter outter = new InputEqualsAvoidNullOutter();

        outter.new InputEqualsAvoidNullInner().equals("eat pizza and enjoy inner classes");

        s.equalsIgnoreCase(s += "mushrooms");

        s1.equalsIgnoreCase(s += "mushrooms");

        (s = "thin crust").equalsIgnoreCase("thick crust");

        (s += "garlic").equalsIgnoreCase("basil");

        ("Chicago Style" + "NY Style").equalsIgnoreCase("California Style" + "Any Style");

        "onions".equalsIgnoreCase(s);

        s.equalsIgnoreCase(new String());

        s.equals(s1);

        new String().equalsIgnoreCase("more cheese");

    }

}

class InputEqualsAvoidNullOutter {
    public class InputEqualsAvoidNullInner {
    	public boolean equals(Object o) {
    	    return true;
    	}
    }
}

class MyString {
    public boolean equals() {
        return true;
    }
    
    public boolean equals(String s1) {
        return true;
    }
    
    public boolean equalsIgnoreCase() {
        return true;
    }
    
    public boolean equalsIgnoreCase(String s1) {
        return true;
    }
    
    private String pizza;
    
    public void main() {
        MyString myString = new MyString();
        myString.equals();
        myString.equals("what");
        myString.equalsIgnoreCase();
        myString.equalsIgnoreCase("what");
        myString.equals(this.pizza = "cold pizza");
    }
}

class NewTest {
    static String classVar;
    String instanceVar;
    NewTest testObj = new NewTest("");

    NewTest(String param) {
        param.equals("");
    }

    public void method(String param) {
        final String localVar = "";

        localVar.equals("");
        param.equals("");

        classVar.equals("");
        instanceVar.equals("");
        NewTest.classVar.equals("");
        this.classVar.equals("");
        this.instanceVar.equals("");

        NewTest testObj = new NewTest("");
        this.testObj.instanceVar.equals(""); // not violated, too confusing
        testObj.classVar.equals(""); // not violated

        for (Nested instanceVar = new Nested(); instanceVar != null; ) {
            instanceVar.equals(1);
            if (instanceVar.equals("")) {
                instanceVar.equals("");
            }
        }

        class Inner {
            String instanceVarInner;

            public void main() {
                classVar.equals("");
                instanceVar.equals("");
                NewTest.classVar.equals("");

                instanceVarInner.equals("");
                this.instanceVarInner.equals("");
                localVar.equals("");

                NewTest testObj = new NewTest("");
                testObj.instanceVar.equals(""); // not violated
                testObj.classVar.equals(""); // not violated

                Inner testInnerObj = new Inner();
                testInnerObj.instanceVarInner.equals(""); // not violated
            }
        }

        Inner testInnerObj = new Inner();
        testInnerObj.instanceVarInner.equals(""); // not violated

        Nested.nestedClassVar.equals(""); // not violated, because the equals call is not
        Nested Nested = new Nested(); // embedded in class Nested, what can lead to really
        Nested.nestedInstanceVar.equals(""); // confusing constructions. But could be improved.
        Nested.nestedClassVar.equals("");
    }
    static {
        final String s = "";
        s.equals("");
        {
            final String x = "";
            class A {
                void foo() {
                    s.equals("");
                    x.equals("");
                }
            }
        }
    }
    void foo(String param) {
        try {
            param.equals("");
            do {
                String s = "";
                s.equals("");
            } while (param.equals(""));
        } catch (Exception e) {
            while (param.equals("")) {
                for (String s = ""; s.equals(""); ){
                    if (s.equals("")) {
                        synchronized (this) {
                            switch (s) {
                                case "1": String str = ""; str.equals("");
                                case "2": s.equals(""); str = ""; str.equals("");
                                case "3": param.equals("");
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    static class Nested {
        static String nestedClassVar;
        String nestedInstanceVar;
        public void method() {
            classVar.equals("");
            NewTest.classVar.equals("");
            this.nestedInstanceVar.equals("");
            nestedClassVar.equals("");
            nestedInstanceVar.equals("");

            class Inner {
                public void method() {
                    classVar.equals("");
                    NewTest.classVar.equals("");
                    nestedClassVar.equals("");
                    nestedInstanceVar.equals("");
                }
            }
        }
    }
    enum EmbeddedEnum {
        A(129),
        B(283),
        C(1212) {
            String constDefVar;
            public void doSomething() {
                constDefVar.equals("");
            }
        };

        Map.Entry<String,Long> enumInstance;

        EmbeddedEnum(int i) {
            enumInstance.equals("");
        }

        public static void doSomethingStatic() {
            enumStatic.equals("");
            enumStatic.equals(null);
        }
        static String enumStatic;
    }

    private String foo() {return "";}
    private Object foo(int i) {return i;}

    Object o2 = new Object();
    Object o3 = new Object();
    private void bar() {
        foo().equals(""); // methods are not checked
        foo(0).equals(""); 
        this.foo().equals("");
        Object o1 = new Object(); o1.equals("");
        o2.equals(""); String o2 = "";
        o3.equals(""); 
String o3 = "";
    }
}
class Anonymous {
    public static void main(String[] args) {
        Runnable anonym = new Runnable() {
            String nullableStr = null;
            public void run() {
                nullableStr.equals("Null");
            };
        };
        Object nullableStr = new Object();
        nullableStr.equals("");
    }
    {}
}
