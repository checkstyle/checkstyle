package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.Map;

public class InputAntlr4AstRegressionNewTypeTree3 {
    public TypeVariable<Class<String>>[] getTypeParameters() {
        ClassRepository info = getGenericInfo();
        if (info != null)
            return (TypeVariable<Class<String>>[])info.getTypeParameters();
        else
            return (TypeVariable<Class<String>>[])new TypeVariable<?>[0];
    }
    class ClassRepository {

        public Object getTypeParameters() {
            return null;
        }
    }
    class TypeVariable<E> {

    }

    private ClassRepository getGenericInfo() {
        return null;
    }
    String[] c, d[]; // ok
    String [] e, f []; // violation

    public enum Stooge {
        MOE, CURLY, LARRY,
    }
    static void checkArrayTypes1(ArrayTypes at, AnnotatedElement e) {
        if (!(at.cls()[0]  == Map.class    &&
              at.e()[0]    == Stooge.MOE  )) {

        }
    }

    static class ArrayTypes {
        Object[] cls;

        public Stooge[] e() {
            return null;
        }



        Object[] cls() {
            return cls;
        }

        public int[] a() {
            return null;
        }
    }
    int[][
    ]
     w
     [] // violation
            [] ; // violation
    int[][
     ]
     z
    [] // violation
            [] ; // violation

    static class P {
        private int x;

        public void set3(P p) {
              synchronized (this) { // violation
                  p.x = get()[0];
              }
          }

        public void set4(P p) {
              synchronized(this) { // ok
                  p.x = get()[0];
              }
          }

        private int[] get() {
            return null;
        }
    }

    private @Nullable int array2 @Nullable [] @Nullable [];
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface Nullable{}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface I {}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface J {}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface K {}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface L {}
