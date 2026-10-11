/*
AbbreviationAsWordInName
allowedAbbreviationLength = (default)3
allowedAbbreviations = III
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

abstract class InputAbbreviationAsWordInNameType2 {
}

abstract class NonAAAAbstractClassName2 {
}

// violation below 'Abbreviation in name 'FactoryWithHARDName2''
abstract class FactoryWithHARDName2 {
}

// violation below 'Abbreviation in name 'AbstractCLASSName2''
abstract class AbstractCLASSName2 {
    abstract class NonAbstractInnerClass {
    }
}

abstract class ClassFactory12 {
    abstract class WellNamedFactory {
    }
}

class NonAbstractClass12 {
}

class AbstractClass12 {
}

class Class1Factory12 {
}

abstract class AbstractClassName32 {
    // violation below 'Abbreviation in name 'AbstractINNERSClass''
    class AbstractINNERSClass {
    }
}

abstract class Class3Factory2 {
    // violation below 'Abbreviation in name 'WellNamedFACTORY''
    class WellNamedFACTORY {
        public void systematicMETHODName() {
            int systematicVARIABLEName = 2;
            int SYSTEMATICVariableName = 1;
        }
    }
}

interface Directions2 {
  int RIGHT=1;
  int LEFT=2;
  int UP=3;
  int DOWN=4;
}

interface BadNameForInterface2
{
   void interfaceMethod();
}

abstract class NonAAAAbstractClassName22 {
    public int serialNUMBER = 6;
    public final int s1erialNUMBER = 6;
    private static int s2erialNUMBER = 6;
    private static final int s3erialNUMBER = 6;
}

interface Interface12 {

    String VALUELONG = "value"; // in interface this is final/static

}

interface Interface22 {

    static String VALUELONG = "value"; // in interface this is final/static

}

interface Interface32 {

    final String VALUELONG = "value"; // in interface this is final/static

}

interface Interface42 {

    final static String VALUELONG = "value"; // in interface this is final/static

}

class FIleNameFormatException2 extends Exception {

    private static final long serialVersionUID = 1L;

    public FIleNameFormatException2(Exception e) {
        super(e);
    }
}
