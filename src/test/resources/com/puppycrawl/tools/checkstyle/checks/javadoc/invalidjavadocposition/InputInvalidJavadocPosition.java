/*
InvalidJavadocPosition


*/

package /** violation */ com.puppycrawl.tools // violation
        .checkstyle.checks.javadoc.invalidjavadocposition;

/** violation */ // violation
import java.lang.String;

/** violation */ // violation
/** valid */
class InputInvalidJavadocPosition {
    /** violation */ // violation
}
/** valid */
/* ignore */
class InputInvalidJavadocPosition2 {
    /** violation */ // violation
    static { /* ignore */ }

    /** violation */ // violation
    /** valid */
    int field1;
    /** valid */
    int[] field2;
    /** valid */
    public int[] field3;
    /** valid */
    @Deprecated int field4;

    int /** violation */ field20; // violation
    int field21 /** violation */; // violation
    @Deprecated /** violation */ int field22; // violation

    void method1() {}
    /** valid */
    void method2() {}
    /** valid */
    <T> T method3() { return null; }
    /** valid */
    String[] method4() { return null; }

    void /** violation */ method20() {} // violation
    void method21 /** violation */ () {} // violation
    void method22(/** violation */) {} // violation
    void method23() /** violation */ {} // violation
    void method24() { /** violation */ } // violation
    void method25() { /** violation */ int variable; } // violation
}
@Deprecated
/** violation */ // violation
class InputInvalidJavadocPosition3 {}
/** valid */
@Deprecated
class InputInvalidJavadocPosition4 {}
class /** violation */ InputInvalidJavadocPosition5 {} // violation
class InputInvalidJavadocPosition6 /** violation */ {} // violation
class InputInvalidJavadocPosition7 {
    void method() {
        /** violation */ int variable1; // violation
        /** violation */ final int variable2; // violation
        /** violation */ @Deprecated int variable3; // violation
    }
}
/** violation */ // violation
