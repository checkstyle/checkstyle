/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public class InputFinalClassPrivateCtor {

    private  class some1 { // violation 'Class some1 should be declared as final'
    }

    private class some2 extends some1 { // violation 'Class some2 should be declared as final'

    }
}

class some { // violation 'Class some should be declared as final'
    private some(){}
}

class nothing {
    private class some1 { // violation 'Class some1 should be declared as final'
    }
    private class some3 { } // violation 'Class some3 should be declared as final'

}

class exam {
    private class paperSetter { // violation 'Class paperSetter should be declared as final'
        int marks;
        String sub;
    }

    private class paper { // violation 'Class paper should be declared as final'
        void method() {
        }
    }

}

class tree {
    private class Node { // violation 'Class Node should be declared as final'
        int data;
        Node next;
    }
}

class ok {
    private  class some1 { // violation 'Class some1 should be declared as final'
    }
}

class some2 { // violation 'Class some2 should be declared as final'
    private some2(){}
}

class nothing2 {
    private class some1 {
        public some1() {}
    }
    private class some3 { // ok
        protected some3() {}
    }
}

class exam2 {

    private final class paperSetter {
        int marks;
        String sub;
        paperSetter(){}

    }

    private final class paper { // ok
        void method() {
        }

        paper(){}
    }

}

class tree2 {
    private final class Node { // ok
        int data;
        Node next;

        private Node(){}
    }
}

class ifAbstract {
    private abstract static class Class extends ifAbstract {

        public abstract void method();

    }
}
