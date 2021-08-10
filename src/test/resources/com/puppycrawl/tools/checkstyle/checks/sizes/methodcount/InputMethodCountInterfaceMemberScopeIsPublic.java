/*
MethodCount
maxTotal = (default)100
maxPrivate = (default)100
maxPackage = (default)100
maxProtected = (default)100
maxPublic = 1
tokens = ENUM_DEF, CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

public interface InputMethodCountInterfaceMemberScopeIsPublic {

    enum Enum { // violation

        ;

        public static void method1() {}

        public static void method2() {}

    }

    class Class { // violation

        public void method1() {}

        public void method2() {}

    }

}
