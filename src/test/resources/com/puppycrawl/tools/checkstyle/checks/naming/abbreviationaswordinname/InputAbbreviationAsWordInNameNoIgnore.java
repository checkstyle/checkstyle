/*
AbbreviationAsWordInName
allowedAbbreviationLength = 5
allowedAbbreviations = NUMBER, SYSTEMATIC, VARIABLE
ignoreFinal = false
ignoreStatic = false
ignoreStaticFinal = false
ignoreOverriddenMethods = (default)true
tokens = CLASS_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, PARAMETER_DEF, \
         INTERFACE_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public class InputAbbreviationAsWordInNameNoIgnore {

    abstract class InputAbbreviationAsWordInNameType {
    }

    abstract class NonAAAAbstractClassName {
    }

    abstract class FactoryWithHARDName {
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
        class AbstractINNERSClass { // violation
        }
    }

    abstract class Class3Factory {
        class WellNamedFACTORY { // violation
            public void systematicMETHODName() { // violation
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

    interface BadNameForInterface
    {
       void interfaceMethod();
    }

    abstract static class NonAAAAbstractClassName2 {
        public int serialNUMBER = 6;
        public final int s1erialNUMBER = 6;
        private static int s2erialNUMBER = 6;
        private static final int s3erialNUMBER = 6;
    }

    interface Interface1 {

        String VALUELONG = "value"; // violation

    }

    interface Interface2 {

        static String VALUELONG = "value"; // violation

    }

    interface Interface3 {

        final String VALUELONG = "value"; // violation

    }

    interface Interface4 {

        final static String VALUELONG = "value"; // violation

    }

    class FIleNameFormatException extends Exception {

        private static final long serialVersionUID = 1L;

        public FIleNameFormatException(Exception e) {
            super(e);
        }
    }
}
