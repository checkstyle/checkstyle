/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullConcatenationsIgnoreCase {
    String s = null;

    void foo() {
        s.equals(s + s); // violation 'left .* of .* equals'
        s.equals("a" + "b"); // violation 'left .* of .* equals'
        s.equals(getInt() + s); // violation 'left .* of .* equals'
        s.equals(getInt() + getInt());
        s.endsWith("a");
        String s = "";
        // violation below 'left.*of.*equals'
        if (!s.equals("Hi[EOL]"+System.getProperty("line.separator")))
            foo();
    }

    int getInt() {
        return (Integer) null;
    }
}

class MyString1 {
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
        MyString1 myString = new MyString1();
        myString.equals();
        myString.equals("what");
        myString.equalsIgnoreCase();
        myString.equalsIgnoreCase("what");
        myString.equals(this.pizza = "cold pizza");
    }
}

class Anonymous1 {
    public static void main(String[] args) {
        Runnable anonym = new Runnable() {
            String nullableStr = null;
            public void run() {
                nullableStr.equals("Null"); // violation 'left .* of .* equals'
            };
        };
        Object nullableStr = new Object();
        nullableStr.equals("");
    }
    {}
}

enum TestEnum1 {
    ONE;
    public void foo() {
        TestEnum1.ONE.equals(this);
        this.ONE.equals(this);
    }
}

class TestThisWithNotStringInstance1 {

    MyString1 notString;

    void foo() {
        this.notString.equals("");
    }

}
