package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

/*
 * Config:
 * allowedAbbreviationLength = 4
 * allowedAbbreviations = MARAZMATIC,VARIABLE
 * tokens = CLASS_DEF,VARIABLE_DEF,METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF,
 *      PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF
 * ignoreStatic = true
 * ignoreFinal = true
 * ignoreStaticFinal = false
 */
public class InputAbbreviationAsWordInNameIgnoreNonStaticFinal {

    abstract class InputAbbreviationAsWordInNameType {
    }

    abstract class NonAAAAbstractClassName {
    }

    abstract class FactoryWithBADNAme {
    }

    abstract class AbstractCLASSName { // violation
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
        class AbstractINNERRClass { // violation
        }
    }

    abstract class Class3Factory {
        class WellNamedFACTORY { // violation
            public void marazmaticMETHODName() { // violation
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

    interface BadNameForInterface
    {
       void interfaceMethod();
    }

    abstract static class NonAAAAbstractClassName2 {
        public int serialNUMBER = 6; // violation
        public final int s1erialNUMBER = 6;
        private static int s2erialNUMBER = 6;
        private static final int s3erialNUMBER = 6; // violation
    }

    interface Interface1 {

        String VALUEEEE = "value"; // violation

    }

    interface Interface2 {

        static String VALUEEEE = "value"; // violation

    }

    interface Interface3 {

        final String VALUEEEE = "value"; // violation

    }

    interface Interface4 {

        final static String VALUEEEE = "value"; // violation

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
        String VALUEEEE = "value"; // violation
    }

    @interface Annotation2 {
        static String VALUEEEE = "value"; // violation
    }

    @interface Annotation3 {
        final String VALUEEEE = "value"; // violation
    }

    @interface Annotation4 {
        final static String VALUEEEE = "value"; // violation
    }

    final class InnerClassOneVIOLATION { // violation
        // only variable definitions are affected by ignore static/final properties
    }

    static class InnerClassTwoVIOLATION { // violation
        // only variable definitions are affected by ignore static/final properties
    }

    static final class InnerClassThreeVIOLATION { // violation
        // only variable definitions are affected by ignore static/final properties
    }

}
