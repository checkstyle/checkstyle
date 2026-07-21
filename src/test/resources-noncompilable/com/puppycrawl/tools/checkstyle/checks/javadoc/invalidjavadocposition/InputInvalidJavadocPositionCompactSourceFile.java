/*
InvalidJavadocPosition


*/

// non-compiled with javac: Compilable with Java25

/** valid - plain field */
int field1 = 1;

/** valid - field with modifiers */
public static final int field2 = 2;

/** valid - field with annotation */
@Deprecated
int field3 = 3;

/** valid - plain method */
void method1() {
}

/** valid - method with modifiers */
public void method2() {
}

/** valid - method with annotation */
@Deprecated
void method3() {
}

/** valid - generic method */
<T> T method4(T value) {
    return value;
}

/** valid - method preceding main */
void method5() {
}

// violation below, 'Javadoc .* placed in the wrong location.'
/** invalid, ignored because another Javadoc follows */
/** valid, recognized on the top-level method */
void method6() {
}

void main() {
    /** invalid - inside method body */ // violation 'Javadoc .* placed in the wrong location.'
    int local = 5;
    System.out.println(local);
}

/** valid - nested type */
class Nested {
    /** valid - nested plain field */
    int nestedField = 1;

    /** valid - nested field with modifiers */
    private final int nestedField2 = 2;

    /** valid - nested method */
    void nestedMethod() {

        // violation below, 'Javadoc .* placed in the wrong location.'
        /** invalid - inside nested method body */
        int nestedLocal = 3;
        System.out.println(nestedLocal);
    }

    /** valid - nested constructor */
    Nested() {
    }
}
