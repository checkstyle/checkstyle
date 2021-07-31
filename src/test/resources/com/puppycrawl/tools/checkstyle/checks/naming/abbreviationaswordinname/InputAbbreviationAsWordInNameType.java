/*
AbbreviationAsWordInName
allowedAbbreviationLength = (default)3
allowedAbbreviations = (default)
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

abstract class InputAbbreviationAsWordInNameType {
}

abstract class NonAAAAbstractClassName {
}

abstract class FactoryWithBADNAme { // violation
}

abstract class AbstractCLASSName1 { // violation
    abstract class NonAbstractInnerClass {
    }
}

abstract class ClassFactory1 {
    abstract class WellNamedFactory {
    }
}

class NonAbstractClass1 {
}

class AbstractClass1 {
}

class Class1Factory1 {
}

abstract class AbstractClassName31 {
    class AbstractINNERRClass { // violation
    }
}

abstract class Class3Factory {
    class WellNamedFACTORY { // violation
    	public void marazmaticMETHODName() { // violation
    		int marazmaticVARIABLEName = 2; // violation
    		int MARAZMATICVariableName = 1; // violation
    	}
    }
}

interface Directions {
  int RIGHT=1;
  int LEFT=2;
  int UP=3;
  int DOWN=4;
}

interface BadNameForInterface
{
   void interfaceMethod();
}

abstract class NonAAAAbstractClassName21 {
	public int serialNUMBER = 6; // violation
	public final int s1erialNUMBER = 6;
	private static int s2erialNUMBER = 6;
	private static final int s3erialNUMBER = 6;
}

interface Interface1 {

	String VALUEEEE = "value"; // in interface this is final/static

}

interface Interface2 {

	static String VALUEEEE = "value"; // in interface this is final/static

}

interface Interface3 {

	final String VALUEEEE = "value"; // in interface this is final/static

}

interface Interface4 {

	final static String VALUEEEE = "value"; // in interface this is final/static

}

class FIleNameFormatException extends Exception {

    private static final long serialVersionUID = 1L;

    public FIleNameFormatException(Exception e) {
        super(e);
    }
}

class StateX {
    int userID;
    int scaleX, scaleY, scaleZ;

    int getScaleX() {
        return this.scaleX;
    }
}

@interface Annotation1 {
    String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation2 {
    static String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation3 {
    final String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation4 {
    final static String VALUE = "value"; // in @interface this is final/static
}
