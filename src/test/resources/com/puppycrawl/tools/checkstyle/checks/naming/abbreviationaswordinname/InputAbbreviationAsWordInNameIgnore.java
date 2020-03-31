package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

/*
 * Config:
 * allowedAbbreviationLength = 5
 * allowedAbbreviations = NUMBER,MARAZMATIC,VARIABLE
 * tokens = CLASS_DEF,VARIABLE_DEF,METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF,
 *      PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF
 * ignoreStatic = true
 * ignoreFinal = true
 * ignoreStaticFinal = true
 */
public class InputAbbreviationAsWordInNameIgnore {

    abstract class InputAbbreviationAsWordInNameType {
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
