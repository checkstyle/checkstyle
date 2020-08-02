//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;
/* Config:
 * ignoreNumbers = {-1, 0, 1, 2}
 * ignoreHashCodeMethod = false
 * ignoreAnnotation = false
 * ignoreFieldDeclaration = true
 * ignoreAnnotationElementDefaults = true
 * constantWaiverParentToken = {TYPECAST , METHOD_CALL , EXPR , ARRAY_INIT , UNARY_MINUS ,
 *  UNARY_PLUS , ELIST , STAR , ASSIGN , PLUS , MINUS , DIV , LITERAL_NEW}
 * tokens = {NUM_DOUBLE , NUM_FLOAT , NUM_INT , NUM_LONG}
 */
public class InputMagicNumberIgnoreFieldDeclarationRecords {
    @anno(6) // violation
    public record MyRecord() {
        private static int myInt = 7; // ok

        public MyRecord{
            int i = myInt + 1; // no violation, 1 is defined as non-magic
            int j = myInt + 8; // violation
        }
        void foo() {
            int i = myInt + 1; // no violation, 1 is defined as non-magic
            int j = myInt + 8; // violation
        }

        public int hashCode() {
            return 10;    // violation
        }
    }

    @interface anno {
        int value() default 10; // no violation
    }

}

class TestClass {
    static int X = 42; // ok
}

record TestRecord() {
    static int X = 42; // ok
}
