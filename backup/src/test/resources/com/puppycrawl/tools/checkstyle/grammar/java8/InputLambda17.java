/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;

import java.util.function.Supplier;

public class InputLambda17{

    void initPartialTraversalState() {
            SpinedBuffer<P_OUT> b = new SpinedBuffer<>();

        P_OUT spliterator = new P_OUT();
        Supplier pusher = () -> spliterator.tryAdvance(b);
        }

    private class P_OUT
    {

        public Object tryAdvance(SpinedBuffer<P_OUT> b)
        {
            // comment
            return null;
        }

    }

    class SpinedBuffer<T>
    {

    }

}
