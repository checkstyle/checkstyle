package com.puppycrawl.tools.checkstyle.checks.coding;

public class InputFinalLocalVariable
{
    private int m_ClassVariable = 0;
    //static block
    static
    {
        int i, j = 0;
        Runnable runnable = new Runnable()
        {
            public void run()
            {
            }
        };
    }
    /** constructor */
    public InputFinalLocalVariable()
    {
        int i = 0;
        // final variable
        final int j = 2;

        int z;

        Object obj = new Object();

        int k = 0;

        String x = obj.toString();

        k++;

        k = 2;

        Runnable runnable = new Runnable()
        {
            public void run()
            {
                int q = 0;
            }
        };
    }

    public void method(int aArg, final int aFinal, int aArg2)
    {
        int z = 0;

        z++;

        aArg2++;
    }

    public void aMethod()
    {
        int i = 0;

        final int j = 2;

        int z;

        Object obj = new Object();

        int k = 0;

        String x = obj.toString();

        k++;

        final class Inner
        {
            public Inner()
            {
                int w = 0;
                Runnable runnable = new Runnable()
                {
                    public void run()
                    {
                    }
                };
            }
        }
    }

    public void anotherMethod()
    {
        boolean aBool = true;
        for (int i = 0, j = 1, k = 1; j < 10 ; j++)
        {
            k++;
            aBool = false;
        }

        int l = 0;
        {
            int weird = 0;
            int j = 0;
            int k = 0;
            {
               l++;
            }
        }

        int weird = 0;
        weird++;

        final InnerClass ic = new InnerClass();

        ic.mInner2 = 1;
    }

    class InnerClass
    {
        private int mInner = 0;

        public int mInner2 = 0;
    }
}

interface Inter
{
    void method(int aParam);
}

abstract class AbstractClass
{
    public abstract void abstractMethod(int aParam);
}

class Blah
{
    static
    {
        for(int a : getInts())
        {

        }
    }

    static int[] getInts() {
        return null;
    }
}

class test_1241722
{
    private Object o_;

    public void doSomething(Object _o)
    {
        System.out.println(_o);
    }

    public void doSomething2(Object _o1)
    {
        o_ = _o1;
    }
}

class class1
{
    public class1(final int x){

    }
}

class A {
    {
        int y = 0;
        y = 9;
    }
}

enum Enum1 {
    ;

    {
        int var = 0;
        var = 1;
    }
}

class class2 {
    public void method1(){
        int x;
        x = 3;
    }
    public void method2() {
        for(int i=0;i<5;i++){
            int x;
            x = 3;
        }
        int y;
        for(int i=0;i<5;i++) {
            y = 3;
        }
        for(int i=0;i<5;i++) {
            int z;
            for(int j=0;j<5;j++) {
                z = 3;
            }
        }
    }
    public void method3() {
        int m;
        do {
           m = 0;
        } while (false);
        do {
            int n;
           n = 0;
        } while (true);        
    }

    private void foo() {
        int q;
        int w;
        int e;
        q = 1;
        w = 1;
        e = 1;
        e = 2;
        class Local {
            void bar() {
                int q;
                int w;
                int e;
                q = 1;
                q = 2;
                w = 1;
                e = 1;
            }
        }

        int i;
        for (;; i = 1) { }
    }

    public void method4() {
        int m;
        int i = 5;
        while (i > 1) {
            m = 0;
            i++;
        }
        while (true) {
            int n;
            n = 0;
        }
    }

    int[] array = new int[10];
    public void method5() {
        int r;
        for (int a: array) {
            r = 0;
        }
        for (int a: array) {
            int t;
            t = 0;
        }
    }
}