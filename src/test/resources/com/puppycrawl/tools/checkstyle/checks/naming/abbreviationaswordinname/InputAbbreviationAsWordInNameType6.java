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

// violation below 'Abbreviation in name 'NonAAAAbstractClassName6''
abstract class NonAAAAbstractClassName6 {}

// violation below 'Abbreviation in name 'FactoryWithHARDName66''
abstract class FactoryWithHARDName66 {}

// violation below 'Abbreviation in name 'AbstractCLASSName6''
abstract class AbstractCLASSName6 {
    abstract class NonAbstractInnerClass {
    }
}

abstract class ClassFactory16 {
    abstract class WellNamedFactory {
    }
}

class NonAbstractClass16 {}

class AbstractClass16 {}

class Class1Factory16 {}

abstract class AbstractClassName36 {
    // violation below 'Abbreviation in name 'AbstractINNERSClass''
    class AbstractINNERSClass {
    }
}

abstract class Class3Factory6 {
    // violation below 'Abbreviation in name 'WellNamedFACTORY''
    class WellNamedFACTORY {
        // violation below 'Abbreviation in name 'systematicMETHODName''
        public void systematicMETHODName() {
            // violation below 'Abbreviation in name 'systematicVARIABLEName''
            int systematicVARIABLEName = 2;
            // violation below 'Abbreviation in name 'SYSTEMATICVariableName''
            int SYSTEMATICVariableName = 1;
        }
    }
}

interface Directions6 {
  // violation below 'Abbreviation in name 'RIGHT''
  int RIGHT=1;
  // violation below 'Abbreviation in name 'LEFT''
  int LEFT=2;
  // violation below 'Abbreviation in name 'UP''
  int UP=3;
  // violation below 'Abbreviation in name 'DOWN''
  int DOWN=4;
}

interface BadNameForInterface6 {
   void interfaceMethod();
}

// violation below 'Abbreviation in name 'NonAAAAbstractClassName26''
abstract class NonAAAAbstractClassName26 {
    // violation below 'Abbreviation in name 'serialNUMBER''
    public int serialNUMBER = 6;
    // violation below 'Abbreviation in name 's1erialNUMBER''
    public final int s1erialNUMBER = 6;
    // violation below 'Abbreviation in name 's2erialNUMBER''
    private static int s2erialNUMBER = 6;
    // violation below 'Abbreviation in name 's3erialNUMBER''
    private static final int s3erialNUMBER = 6;
}

interface Interface16 {
    // violation below 'Abbreviation in name 'VALUELONG''
    String VALUELONG = "value";
}

interface Interface26 {
    // violation below 'Abbreviation in name 'VALUELONG''
    static String VALUELONG = "value";
}

interface Interface36 {
    // violation below 'Abbreviation in name 'VALUELONG''
    final String VALUELONG = "value";
}

interface Interface46 {
    // violation below 'Abbreviation in name 'VALUELONG''
    final static String VALUELONG = "value";
}

// violation below 'Abbreviation in name 'FIleNameFormatException6''
class FIleNameFormatException6 extends Exception {
    // violation below 'Abbreviation in name 'serialVersionUID''
    private static final long serialVersionUID = 1L;
    public FIleNameFormatException6(Exception e) {
        super(e);
    }
}
