package org.checkstyle.suppressionxpathfilter.redundantmodifier;

interface InputXpathRedundantModifierFieldsInInterface {
     public static final int CONSTANT = 42; //warn
     public final int CONSTANT2 = 42; //warn
     static final int CONSTANT3 = 42; //warn
     final int CONSTANT4 = 42; //warn
     int CONSTANT5 = 42;
}
