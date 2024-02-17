/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public class InputFinalClassPrivateCtor {

    private class Some1 {
    }

    private class Some2 extends Some1 { // violation 'Class Some2 should be declared as final'

    }
}

class Some { // violation 'Class Some should be declared as final'
    private Some(){}
}

class Nothing {
    private class Some3 { // violation 'Class Some3 should be declared as final'
    }
    private class Some4 { } // violation 'Class Some4 should be declared as final'

}

class Exam {
    private class PaperSetter { // violation 'Class PaperSetter should be declared as final'
        int marks;
        String sub;
    }

    private class Paper { // violation 'Class Paper should be declared as final'
        void method() {
        }
    }

}

class Tree {
    private class Node { // violation 'Class Node should be declared as final'
        int data;
        Node next;
    }
}

class SomeOne {
    private  class Some1 { // violation 'Class Some1 should be declared as final'
    }
}

class Some2 { // violation 'Class Some2 should be declared as final'
    private Some2(){}
}

class Nothing2 {
    private class Some1 {
        public Some1() {}
    }
    private class Some3 {
        protected Some3() {}
    }
}

class Exam2 {

    private final class PaperSetter {
        int marks;
        String sub;
        PaperSetter(){}

    }

    private final class Paper {
        void method() {
        }

        Paper(){}
    }

}

class Tree2 {
    private final class Node {
        int data;
        Node next;

        private Node(){}
    }
}

class IfAbstract {
    private abstract static class Class extends IfAbstract {

        public abstract void method();

    }
}

class Suppression {

    @SuppressWarnings("uncheck") // violation 'Class NewCheck should be declared as final'
    private static class NewCheck {}

    @SuppressWarnings("uncheck") // violation 'Class NewCheck2 should be declared as final'
    private class NewCheck2 {}


    @SuppressWarnings("uncheck") // violation 'Class OldCheck should be declared as final'
    private class OldCheck {
        private OldCheck(){}
    }
}
