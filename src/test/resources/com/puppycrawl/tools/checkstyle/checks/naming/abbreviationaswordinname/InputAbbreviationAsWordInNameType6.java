/*
AbbreviationAsWordInName
allowedAbbreviationLength = 0
allowedAbbreviations = (default)
ignoreFinal = false
ignoreStatic = false
ignoreStaticFinal = false
ignoreOverriddenMethods = false
tokens = CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

abstract class InputAbbreviationAsWordInNameType6 {
}

abstract class NonAAAAbstractClassName6 { // violation
}

abstract class FactoryWithBADNAme66 { // violation
}

abstract class AbstractCLASSName6 { // violation
    abstract class NonAbstractInnerClass {
    }
}

abstract class ClassFactory16 {
    abstract class WellNamedFactory {
    }
}

class NonAbstractClass16 {
}

class AbstractClass16 {
}

class Class1Factory16 {
}

abstract class AbstractClassName36 {
    class AbstractINNERRClass { // violation
    }
}

abstract class Class3Factory6 {
    class WellNamedFACTORY { // violation
        public void marazmaticMETHODName() { // violation
            int marazmaticVARIABLEName = 2; // violation
            int MARAZMATICVariableName = 1; // violation
        }
    }
}

interface Directions6 {
  int RIGHT=1; // violation
  int LEFT=2; // violation
  int UP=3; // violation
  int DOWN=4; // violation
}

interface BadNameForInterface6
{
   void interfaceMethod();
}

abstract class NonAAAAbstractClassName26 { // violation
    public int serialNUMBER = 6; // violation
    public final int s1erialNUMBER = 6; // violation
    private static int s2erialNUMBER = 6; // violation
    private static final int s3erialNUMBER = 6; // violation
}

interface Interface16 {

    String VALUEEEE = "value"; // violation

}

interface Interface26 {

    static String VALUEEEE = "value"; // violation

}

interface Interface36 {

    final String VALUEEEE = "value"; // violation

}

interface Interface46 {

    final static String VALUEEEE = "value"; // violation

}

class FIleNameFormatException6 extends Exception { // violation

    private static final long serialVersionUID = 1L; // violation

    public FIleNameFormatException6(Exception e) {
        super(e);
    }
}

class StateX6 {
    int userID; // violation
    int scaleX, scaleY, scaleZ;

    int getScaleX() {
        return this.scaleX;
    }
}

@interface Annotation16 {
    String VALUE = "value"; // violation
}

@interface Annotation26 {
    static String VALUE = "value"; // violation
}

@interface Annotation36 {
    final String VALUE = "value"; // violation
}

@interface Annotation46 {
    final static String VALUE = "value"; // violation
}
