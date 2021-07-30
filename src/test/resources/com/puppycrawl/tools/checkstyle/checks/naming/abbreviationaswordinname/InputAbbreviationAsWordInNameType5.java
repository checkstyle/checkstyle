/*
AbbreviationAsWordInName
allowedAbbreviationLength = 5
allowedAbbreviations = CLASS
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = CLASS_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, PARAMETER_DEF, \
         INTERFACE_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

abstract class InputAbbreviationAsWordInNameType5 {
}

abstract class NonAAAAbstractClassName5 {
}

abstract class FactoryWithBADNAme5 {
}

abstract class AbstractCLASSName5 {
    abstract class NonAbstractInnerClass {
    }
}

abstract class ClassFactory15 {
    abstract class WellNamedFactory {
    }
}

class NonAbstractClass15 {
}

class AbstractClass15 {
}

class Class1Factory15 {
}

abstract class AbstractClassName35 {
    class AbstractINNERRClass { // violation
    }
}

abstract class Class3Factory5 {
    class WellNamedFACTORY { // violation
        public void marazmaticMETHODName() { // violation
            int marazmaticVARIABLEName = 2; // violation
            int MARAZMATICVariableName = 1; // violation
        }
    }
}

interface Directions5 {
  int RIGHT=1;
  int LEFT=2;
  int UP=3;
  int DOWN=4;
}

interface BadNameForInterface5
{
   void interfaceMethod();
}

abstract class NonAAAAbstractClassName25 {
    public int serialNUMBER = 6;
    public final int s1erialNUMBER = 6;
    private static int s2erialNUMBER = 6;
    private static final int s3erialNUMBER = 6;
}

interface Interface15 {

    String VALUEEEE = "value"; // in interface this is final/static

}

interface Interface25 {

    static String VALUEEEE = "value"; // in interface this is final/static

}

interface Interface35 {

    final String VALUEEEE = "value"; // in interface this is final/static

}

interface Interface45 {

    final static String VALUEEEE = "value"; // in interface this is final/static

}

class FIleNameFormatException5 extends Exception {

    private static final long serialVersionUID = 1L;

    public FIleNameFormatException5(Exception e) {
        super(e);
    }
}

class StateX5 {
    int userID;
    int scaleX, scaleY, scaleZ;

    int getScaleX() {
        return this.scaleX;
    }
}

@interface Annotation15 {
    String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation25 {
    static String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation35 {
    final String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation45 {
    final static String VALUE = "value"; // in @interface this is final/static
}
