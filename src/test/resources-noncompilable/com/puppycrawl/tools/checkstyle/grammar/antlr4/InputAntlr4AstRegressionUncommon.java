// non-compiled with eclipse: Type arguments are not allowed
// non-compiled with javac: contains specially crafted set of imports that requires classpath
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.IntFunction;

import com.sun.tools.javac.Main;

public class InputAntlr4AstRegressionUncommon
        implements MyInterface {
    int x[], y[], z[][];

    public Double method1()[] {
        return null;
    }

    protected <B, Z, A> OtherClass.Q<A,B,Z> m() {
        return null;
    }
}

interface MyInterface {
    Double method1()[];
}

class OtherClass extends InputAntlr4AstRegressionUncommon {
    OtherClass (int z){
        super();
    }
    class Q<A, B, Z>{}

    ;
    public <Z, B, A> Q<A, Z, B> m() { return null;}

    OtherClass (int z, String s){
        new OtherClass(2);
    }

    class OtherInner<E> {
        OtherInner(int w){}
        <T extends Serializable> OtherInner(T element) {

        }
        OtherInner() {
            <E>this(2);
        }
    }

    private void killAll() {
          boolean oneAlive = false;
              if (oneAlive) {
                  Thread.yield();
              }

      }
}

record TestRecord(int[] x, int[]... y) {
    void foo() {
        Runnable r11 = Main::<String>new;
        IntFunction<int[]> r13 = int[]::<String>new; // produces the same results
    }
}

;;;
