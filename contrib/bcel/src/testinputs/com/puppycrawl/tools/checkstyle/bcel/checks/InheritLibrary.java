package com.puppycrawl.tools.checkstyle.bcel.checks;

import org.apache.bcel.classfile.AccessFlags ;
/**
 * Test data for checking that fields with the same name as fields in
 * superclasses work, when the superclass is inside an external library.
 */
public abstract class InheritLibrary extends AccessFlags
{
    protected int access_flags;
}
