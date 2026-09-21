/*
JavadocVariable
accessModifiers = public
considerEnclosingScope = true
ignoreNamePattern = (default)null
tokens = (default)ENUM_CONSTANT_DEF, VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public class InputJavadocVariableConsiderEnclosingScope {

    public int field1 = 0; // violation 'Missing a Javadoc comment'

    // violation below 'Missing a Javadoc comment'
    public Runnable anon = new Runnable() {

        public int field2 = 0;

        @Override
        public void run() {
        }
    };

    public static class Nested {

        public int field3 = 0; // violation 'Missing a Javadoc comment'
    }

    static class PackagePrivateNested {

        public int field4 = 0;
    }

    private static class PrivateNested {

        public int field5 = 0;
    }

    private record PrivateRecord(int value) {

        public static int field8 = 0;
    }

    public enum PublicEnum {

        A; // violation 'Missing a Javadoc comment'
    }

    enum PackagePrivateEnum {

        B;
    }
}

class PackagePrivateHolder {

    public int field6 = 0;

    public enum HiddenEnum {

        C;
    }
}

interface PackagePrivateInterface {

    int field7 = 0;
}
