package /** violation */ com.puppycrawl.tools.checkstyle.checks.javadoc.invalidjavadocposition;

/** violation */
import java.lang.String;

/** violation */
/** valid */
class InputInvalidJavadocPosition {
    /** violation */
}
/** valid */
/* ignore */
class InputInvalidJavadocPosition2 {
    /** violation */
    static { /* ignore */ }

    /** violation */
    /** valid */
    int field1;
    /** valid */
    int[] field2;
    /** valid */
    public int[] field3;
    /** valid */
    @Deprecated int field4;

    int /** violation */ field20;
    int field21 /** violation */;
    @Deprecated /** violation */ int field22;

    void method1() {}
    /** valid */
    void method2() {}
    /** valid */
    <T> T method3() { return null; }
    /** valid */
    String[] method4() { return null; }

    void /** violation */ method20() {}
    void method21 /** violation */ () {}
    void method22(/** violation */) {}
    void method23() /** violation */ {}
    void method24() { /** violation */ }
    void method25() { /** violation */ int variable; }
}
@Deprecated
/** violation */
class InputInvalidJavadocPosition3 {}
/** valid */
@Deprecated
class InputInvalidJavadocPosition4 {}
class /** violation */ InputInvalidJavadocPosition5 {}
class InputInvalidJavadocPosition6 /** violation */ {}
class InputInvalidJavadocPosition7 {
    void method() {
        /** violation */ int variable1;
        /** violation */ final int variable2;
        /** violation */ @Deprecated int variable3;
    }
}
/** violation */
