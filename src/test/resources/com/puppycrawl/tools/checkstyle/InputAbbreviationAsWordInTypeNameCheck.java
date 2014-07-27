package com.puppycrawl.tools.checkstyle.naming;

abstract public class IIIInputAbstractClassName {
}

abstract class NonAAAAbstractClassName {
}

abstract class FactoryWithBADNAme {
}

abstract class AbstractCLASSName {
    abstract class NonAbstractInnerClass {
    }
}

abstract class ClassFactory {
    abstract class WellNamedFactory {
    }
}

class NonAbstractClass {
}

class AbstractClass {
}

class Class1Factory {
}

abstract class AbstractClassName2 {
    class AbstractINNERRClass {
    }
}

abstract class Class2Factory {
    class WellNamedFACTORY {
    	public void marazmaticMETHODName() {
    		int marazmaticVARIABLEName = 2;
    		int MARAZMATICVariableName = 1;
    	}
    }
}

public interface Directions {
  int RIGHT=1;
  int LEFT=2;
  int UP=3;
  int DOWN=4;
}

interface BadNameForInterfeis
{
   void interfaceMethod();
}

abstract class NonAAAAbstractClassName1 {
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

public class FIleNameFormatException extends Exception {

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

