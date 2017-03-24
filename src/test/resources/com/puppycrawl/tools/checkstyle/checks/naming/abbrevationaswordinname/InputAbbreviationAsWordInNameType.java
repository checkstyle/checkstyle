package com.puppycrawl.tools.checkstyle.checks.naming.abbrevationaswordinname;

abstract class IIIInputAbstractClassName {
}

abstract class NonAAAAbstractClassName {
}

abstract class FactoryWithBADNAme {
}

abstract class AbstractCLASSName {
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

abstract class AbstractClassName3 {
    class AbstractINNERRClass {
    }
}

abstract class Class3Factory {
    class WellNamedFACTORY {
    	public void marazmaticMETHODName() {
    		int marazmaticVARIABLEName = 2;
    		int MARAZMATICVariableName = 1;
    	}
    }
}

interface Directions {
  int RIGHT=1;
  int LEFT=2;
  int UP=3;
  int DOWN=4;
}

interface BadNameForInterfeis
{
   void interfaceMethod();
}

abstract class NonAAAAbstractClassName2 {
	public int serialNUMBER = 6;
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
