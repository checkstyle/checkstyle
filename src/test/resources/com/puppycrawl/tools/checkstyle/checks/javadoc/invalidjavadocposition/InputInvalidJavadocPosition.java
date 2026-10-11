/*
InvalidJavadocPosition


*/

package /** invalid */ com.puppycrawl.tools // violation 'Javadoc .* placed in the wrong location.'
        .checkstyle.checks.javadoc.invalidjavadocposition;

/** invalid */ // violation 'Javadoc .* placed in the wrong location.'
import java.lang.String;

/** invalid */ // violation 'Javadoc .* placed in the wrong location.'
/** valid */
class InputInvalidJavadocPosition {
    /** invalid */ // violation 'Javadoc .* placed in the wrong location.'
}
/** valid */
/* ignore */
class InputInvalidJavadocPosition2 {
    /** invalid */ // violation 'Javadoc .* placed in the wrong location.'
    static { /* ignore */ }

    /** invalid */ // violation 'Javadoc .* placed in the wrong location.'
    /** valid */
    int field1;
    /** valid */
    int[] field2;
    /** valid */
    public int[] field3;
    /** valid */
    @Deprecated int field4;

    int /** invalid */ field20; // violation 'Javadoc .* placed in the wrong location.'
    int field21 /** invalid */; // violation 'Javadoc .* placed in the wrong location.'
    @Deprecated /** invalid */ int field22; // violation 'Javadoc .* placed in the wrong location.'

    void method1() {}
    /** valid */
    void method2() {}
    /** valid */
    <T> T method3() { return null; }
    /** valid */
    String[] method4() { return null; }

    void /** invalid */ method20() {} // violation 'Javadoc .* placed in the wrong location.'
    void method21 /** invalid */ () {} // violation 'Javadoc .* placed in the wrong location.'
    void method22(/** invalid */) {} // violation 'Javadoc .* placed in the wrong location.'
    void method23() /** invalid */ {} // violation 'Javadoc .* placed in the wrong location.'
    void method24() { /** invalid */ } // violation 'Javadoc .* placed in the wrong location.'
    void method25() { /** invalid */ int variable; } // violation '.* placed in the wrong location.'
}
@Deprecated
/** invalid */ // violation 'Javadoc .* placed in the wrong location.'
class InputInvalidJavadocPosition3 {}
/** valid */
@Deprecated
class InputInvalidJavadocPosition4 {}
class /** invalid */ InputInvalidJavadocPosition5 {} // violation '.* placed in the wrong location.'
class InputInvalidJavadocPosition6 /** invalid */ {} // violation '.* placed in the wrong location.'
class InputInvalidJavadocPosition7 {
    void method() {
        /** invalid */ int variable1; // violation 'Javadoc .* placed in the wrong location.'
        /** invalid */ final int variable2; // violation 'Javadoc .* placed in the wrong location.'
        /** invalid */ @Deprecated int variable3; // violation '.* placed in the wrong location.'
    }
}
class GenericConstructor {
    /** valid */
    <E extends String> GenericConstructor(E a) {}

    // violation below 'Javadoc .* placed in the wrong location.'
    </** invalid */E extends String>  GenericConstructor(E a, E b) {}

    // violation below 'Javadoc .* placed in the wrong location.'
    <E extends String> /** invalid */ GenericConstructor(E a, E b, E c) {}

    // violation below 'Javadoc .* placed in the wrong location.'
    <E extends String> GenericConstructor(/** invalid */E a, E b, E c, E d) {}

    // violation below 'Javadoc .* placed in the wrong location.'
    <E extends String> GenericConstructor(E a, E b, E c, E d, E e) {/** invalid */}

    /** valid */
    private <E extends String> GenericConstructor() {}

    private class InnerClass {
        /** valid */
        <E extends String> InnerClass() {}

        /** valid */ <E extends String> InnerClass(E a) {}
    }
}
/** invalid */ // violation 'Javadoc .* placed in the wrong location.'
