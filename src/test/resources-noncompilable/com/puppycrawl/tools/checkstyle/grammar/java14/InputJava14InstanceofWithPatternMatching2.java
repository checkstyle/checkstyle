// non-compiled with eclipse: s cannot be resolved
// non-compiled with javac: Compilable with Java21 individually
package com.puppycrawl.tools.checkstyle.grammar.java14;
import java.util.*;

/**
 * Input for Java 14 enhanced instanceof with pattern matching in conditions
 */
public class InputJava14InstanceofWithPatternMatching2 {

    interface VoidPredicate {
        public boolean get();
    }

    public void t(Object o1, Object o2) {
        Object b;
        Object c;
        if (!(o1 instanceof String s) && (o2 instanceof String s)) {} //ok
        if (o1 instanceof String s || !(o2 instanceof String s)) {} //ok
        b = ((VoidPredicate) () -> o1 instanceof String s).get();

        List<Integer> arrayList = new ArrayList<Integer>();
        if (arrayList instanceof ArrayList<Integer> ai) {
            System.out.println("Blah");
        }

        if (!(o1 instanceof String k)) {
            return ;
        }

        if (o1 instanceof String s2) {}


        if (!(o1 instanceof String s4)) {
            return ;
        }

        boolean result = (o1 instanceof String a1) ?
                (o1 instanceof String a2) : (!(o1 instanceof String a3));

        if (!(o1 instanceof String s) ? false : s.length()>0){
            System.out.println("done");
        }

        if ((o1 instanceof String s) ? s.length()>0 : false){
            System.out.println("done");
        }

        if (!(o1 instanceof String s) || !(o2 instanceof Integer i)){
        } else {
            s.length();
            i.intValue();
        }

        if (!(o1 instanceof String s) || !(o2 instanceof Integer i)){
        } else {
            s.length();
            i.intValue();
        }

        if (o1 instanceof String s && o2 instanceof Integer in) {
            s.length();
            in.intValue();
        }
    }
}
