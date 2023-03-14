/*
AbbreviationAsWordInName
allowedAbbreviationLength = 4
allowedAbbreviations = CLASS, FACTORY
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

abstract class InputAbbreviationAsWordInNameType3 {
}

abstract class NonAAAAbstractClassName3 {
}

abstract class FactoryWithBADNAme3 {
}

abstract class AbstractCLASSName3 {
    abstract class NonAbstractInnerClass {
    }
}

abstract class ClassFactory13 {
    abstract class WellNamedFactory {
    }
}

class NonAbstractClass13 {
}

class AbstractClass13 {
}

class Class1Factory13 {
}

abstract class AbstractClassName33 {
    class AbstractINNERRClass { // violation
    }
}

abstract class Class3Factory3 {
    class WellNamedFACTORY {
        public void marazmaticMETHODName() {
            int marazmaticVARIABLEName = 2;
            int MARAZMATICVariableName = 1;
        }
    }
}

interface Directions3 {
  int RIGHT=1;
  int LEFT=2;
  int UP=3;
  int DOWN=4;
}

interface BadNameForInterface3
{
   void interfaceMethod();
}

abstract class NonAAAAbstractClassName23 {
    public int serialNUMBER = 6;
    public final int s1erialNUMBER = 6;
    private static int s2erialNUMBER = 6;
    private static final int s3erialNUMBER = 6;
}

interface Interface13 {

    String VALUEEEE = "value"; // in interface this is final/static

}

interface Interface23 {

    static String VALUEEEE = "value"; // in interface this is final/static

}

interface Interface33 {

    final String VALUEEEE = "value"; // in interface this is final/static

}

interface Interface43 {

    final static String VALUEEEE = "value"; // in interface this is final/static

}

class FIleNameFormatException3 extends Exception {

    private static final long serialVersionUID = 1L;

    public FIleNameFormatException3(Exception e) {
        super(e);
    }
}

class StateX3 {
    int userID;
    int scaleX, scaleY, scaleZ;

    int getScaleX() {
        return this.scaleX;
    }
}

@interface Annotation13 {
    String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation23 {
    static String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation33 {
    final String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation43 {
    final static String VALUE = "value"; // in @interface this is final/static
}
