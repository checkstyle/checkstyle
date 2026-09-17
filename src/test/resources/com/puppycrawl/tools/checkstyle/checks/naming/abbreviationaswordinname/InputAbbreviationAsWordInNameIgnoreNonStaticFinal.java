/*
AbbreviationAsWordInName
allowedAbbreviationLength = 4
allowedAbbreviations = SYSTEMATIC, VARIABLE
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = false
ignoreOverriddenMethods = (default)true
tokens = CLASS_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, PARAMETER_DEF, \
         INTERFACE_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public class InputAbbreviationAsWordInNameIgnoreNonStaticFinal {

    abstract class InputAbbreviationAsWordInNameType {}

    abstract class NonAAAAbstractClassName {}

    abstract class FactoryWithHARDName {}

    // violation below 'Abbreviation in name 'AbstractCLASSName''
    abstract class AbstractCLASSName {
        abstract class NonAbstractInnerClass {
        }
    }

    abstract class ClassFactory1 {
        abstract class WellNamedFactory {
        }
    }

    class NonAbstractClass1 {}

    class AbstractClass1 {}

    class Class1Factory1 {}

    abstract class AbstractClassName3 {
        // violation below 'Abbreviation in name 'AbstractINNERSClass''
        class AbstractINNERSClass {
        }
    }

    abstract class Class3Factory {
        // violation below 'Abbreviation in name 'WellNamedFACTORY''
        class WellNamedFACTORY {
            // violation below 'Abbreviation in name 'systematicMETHODName''
            public void systematicMETHODName() {
                int systematicVARIABLEName = 2;
                int SYSTEMATICVariableName = 1;
            }
        }
    }

    interface Directions {
      int RIGHT=1;
      int LEFT=2;
      int UP=3;
      int DOWN=4;
    }

    interface BadNameForInterface {
       void interfaceMethod();
    }

    abstract static class NonAAAAbstractClassName2 {
        // violation below 'Abbreviation in name 'serialNUMBER''
        public int serialNUMBER = 6;
        public final int s1erialNUMBER = 6;
        private static int s2erialNUMBER = 6;
        // violation below 'Abbreviation in name 's3erialNUMBER''
        private static final int s3erialNUMBER = 6;
    }

    interface Interface1 {
        // violation below 'Abbreviation in name 'VALUELONG''
        String VALUELONG = "value";
    }

    interface Interface2 {
        // violation below 'Abbreviation in name 'VALUELONG''
        static String VALUELONG = "value";
    }

    interface Interface3 {
        // violation below 'Abbreviation in name 'VALUELONG''
        final String VALUELONG = "value";
    }

    interface Interface4 {
        // violation below 'Abbreviation in name 'VALUELONG''
        final static String VALUELONG = "value";
    }

    class FIleNameFormatException extends Exception {

        private static final long serialVersionUID = 1L;

        public FIleNameFormatException(Exception e) {
            super(e);
        }
    }
}
