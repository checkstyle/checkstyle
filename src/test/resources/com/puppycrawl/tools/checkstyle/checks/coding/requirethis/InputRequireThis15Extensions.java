/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

// someexamples of 1.5 extensions
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

@interface MyAnnotation1 {
    String name();
    int version();
}

@MyAnnotation1(name = "ABC", version = 1)
public class InputRequireThis15Extensions
{

}

enum Enum2
{
    A, B, C;
    Enum2() {}
    public String toString() {
        return ""; //some custom implementation
    }
}

interface TestRequireThisEnum
{
    enum DAY_OF_WEEK
    {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY
    }
}
class NestedClass {
    protected RuntimeException exception = new RuntimeException() {};

    public void anonEx2() {
        RuntimeException exception = new RuntimeException();
        try {
            //some code
            String re = "lol";
        } catch (Exception e) {
            throw exception;
        }
    }
}
class Basic {
    abstract class Awaiter extends Thread {
        private volatile Throwable result = null;
        protected void result(Throwable result) { this.result = result; }
    }

    private Awaiter awaiter() {
        return new Awaiter() {
            public void run() {
                try {}
                catch (Throwable result) { result(result); }
            }
        };
    }
}
