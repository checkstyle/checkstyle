package com.puppycrawl.tools.checkstyle.checks.coding.superclone;

import java.util.ArrayList;

/** Config = default */
public class InputSuperCloneMethodReference extends ArrayList {

    public interface CheckedSupplier<R, E extends Exception> {
        R get() throws E;
    }

    public Object clone() { // ok
        CheckedSupplier<Object, CloneNotSupportedException> r = super::clone;
        CheckedSupplier<Object, CloneNotSupportedException> r1 = super::getClass;
        try {
            return r.get();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
