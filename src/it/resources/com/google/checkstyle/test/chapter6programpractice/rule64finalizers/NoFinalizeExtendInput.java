package com.google.checkstyle.test.chapter6programpractice.rule64finalizers;

class NormalFinalizer {

    public static void doStuff() {
        // This method do some stuff
    }

    protected void finalize() throws Throwable { //warn
        try {
            doStuff();
        } finally {
            super.finalize();
        }
    }
}

// negates effect of superclass finalizer
class EmptyFinalizer {

    protected void finalize() throws Throwable { //warn
        // empty finalize ()
    }
}

// fails to call superclass finalize method
class WithoutTryCatchFinalizer {

    public static void doStuff() {
        // This method do some stuff
    }

    protected void finalize() throws Throwable { //warn
        doStuff();
    }
}

// public finalizer
class PublicFinalizer {

    public static void doStuff() {
        // This method do some stuff
    }

    public void finalize() throws Throwable { //warn
        try {
            doStuff();
        } finally {
            super.finalize();
        }
    }
}

// unless (or worse) finalizer
class SuperFinalizer {

    protected void finalize() throws Throwable { //warn
        super.finalize();
    }
}

// public finalizer
class StaticFinalizer {

    public static void doStuff() {
        // This method do some stuff
    }

    protected void finalize() { //warn
        try {
            doStuff();
        } finally {

        }
    }

    class InnerFinalizer {

        protected void finalize() { //warn
            try {
                doStuff();
            } finally {

            }
        }
    }
}

class WithoutFinalize {
    public void doStuff() {
        // This method do some stuff
    }

    public void finalizeMe() {
        // This method do some stuff
    }

    public void doFinalize() {
        // This method do some stuff
    }
}

class WithoutMethods {}

class WithAnonymousClass {
    
    public static void doStuff() {
        // This method do some stuff
    }
    
    public void foo() {
        
        Ball b = new Ball() {
            
            public void hit() {
                System.out.println("You hit it!");
            }

            protected void finalize() { //warn
                try {
                    doStuff();
                } finally {

                }
            }
        };
        b.hit();
    }

    interface Ball {
        void hit();
    }
}

interface WithFinalizer {
    void finalize(); //warn
}
