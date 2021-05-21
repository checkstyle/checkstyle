//non-compiled with javac: Compilable with Java9
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

/*
 * Config:
 * scope = public
 */
public interface InputMissingJavadocTypeInterfaceType { // violation
    enum EnumWithPublicScope { // violation
    }

    class ClassWithPublicScope { // violation
    }

    interface InterfaceWithPublicScope { // violation
    }

    class ClassWithPublicScopeTwo { // violation
        private class PrivateClassInPublicNestedClass {
            public class PublicInPrivateClass {
                public PublicInPrivateClass() { } // Redundant in non public class
            }
        }
    }
}
