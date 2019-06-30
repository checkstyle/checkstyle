package /** warn */ com.google.checkstyle.test.chapter7javadoc.rule734nonrequiredjavadoc;

/** warn */
import java.lang.String;

/** warn */
/** valid */
class InputInvalidJavadocPosition {
    /** warn */
}
/** valid */
/* ignore */
class InputInvalidJavadocPosition2 {
    /** warn */
    static { /* ignore */ }

    /** warn */
    /** valid */
    int field1;
    /** valid */
    int[] field2;
    /** valid */
    public int[] field3;
    /** valid */
    @Deprecated int field4;

    int /** warn */ field20;
    int field21 /** warn */;
    @Deprecated /** warn */ int field22;

    void method1() {}
    /** valid */
    void method2() {}
    /** valid */
    <T> T method3() { return null; }
    /** valid */
    String[] method4() { return null; }

    void /** warn */ method20() {}
    void method21 /** warn */ () {}
    void method22(/** warn */) {}
    void method23() /** warn */ {}
    void method24() { /** warn */ }
    void method25() { /** warn */ int variable; }
}
@Deprecated
/** warn */
class InputInvalidJavadocPosition3 {}
/** valid */
@Deprecated
class InputInvalidJavadocPosition4 {}
class /** warn */ InputInvalidJavadocPosition5 {}
class InputInvalidJavadocPosition6 /** warn */ {}
/** warn */
