/*
AbbreviationAsWordInName
allowedAbbreviationLength = 5
allowedAbbreviations = CLASS
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

abstract class InputAbbreviationAsWordInNameType4 {
}

abstract class NonAAAAbstractClassName4 {
}

abstract class FactoryWithBADNAme4 {
}

abstract class AbstractCLASSName4 {
    abstract class NonAbstractInnerClass {
    }
}

abstract class ClassFactory14 {
    abstract class WellNamedFactory {
    }
}

class NonAbstractClass14 {
}

class AbstractClass14 {
}

class Class1Factory14 {
}

abstract class AbstractClassName34 {
    class AbstractINNERRClass { // violation
    }
}

abstract class Class3Factory4 {
    class WellNamedFACTORY { // violation
        public void marazmaticMETHODName() {
            int marazmaticVARIABLEName = 2;
            int MARAZMATICVariableName = 1;
        }
    }
}

interface Directions4 {
  int RIGHT=1;
  int LEFT=2;
  int UP=3;
  int DOWN=4;
}

interface BadNameForInterface4
{
   void interfaceMethod();
}

abstract class NonAAAAbstractClassName24 {
    public int serialNUMBER = 6;
    public final int s1erialNUMBER = 6;
    private static int s2erialNUMBER = 6;
    private static final int s3erialNUMBER = 6;
}

interface Interface14 {

    String VALUEEEE = "value"; // in interface this is final/static

}

interface Interface24 {

    static String VALUEEEE = "value"; // in interface this is final/static

}

interface Interface34 {

    final String VALUEEEE = "value"; // in interface this is final/static

}

interface Interface44 {

    final static String VALUEEEE = "value"; // in interface this is final/static

}

class FIleNameFormatException4 extends Exception {

    private static final long serialVersionUID = 1L;

    public FIleNameFormatException4(Exception e) {
        super(e);
    }
}

class StateX4 {
    int userID;
    int scaleX, scaleY, scaleZ;

    int getScaleX() {
        return this.scaleX;
    }
}

@interface Annotation14 {
    String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation24 {
    static String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation34 {
    final String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation44 {
    final static String VALUE = "value"; // in @interface this is final/static
}
