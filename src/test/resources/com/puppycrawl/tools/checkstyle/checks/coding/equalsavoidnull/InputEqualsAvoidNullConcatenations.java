/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullConcatenations {
    String s = null;

    void foo() {
        s.equals(s + s); // violation 'left .* of .* equals'
        s.equals("a" + "b"); // violation 'left .* of .* equals'
        s.equals(getInt() + s); // violation 'left .* of .* equals'
        s.equals(getInt() + getInt());
        s.endsWith("a");
        String s = "";
        if (!s.equals("Hi[EOL]"+System.getProperty("line.separator")))
            // violation above 'left.*of.*equals'
            foo();
    }

    int getInt() {
        return (Integer) null;
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

class Anonymous {
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

enum TestEnum {
    ONE;
    public void foo() {
        TestEnum.ONE.equals(this);
        this.ONE.equals(this);
    }
}

class TestThisWithNotStringInstance {

    MyString notString;

    void foo() {
        this.notString.equals("");
    }

}
