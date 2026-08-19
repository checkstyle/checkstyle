/*
RedundantModifierCompactSource
tokens = (default)METHOD_DEF, VARIABLE_DEF


*/

// non-compiled with javac: Compilable with Java25

void localDeclarations(final int parameter) {
    final int local = parameter;
    try (var resource = new java.io.StringReader("")) {
        resource.read();
    }
    catch (final java.io.IOException ex) {
        if (ex instanceof java.io.IOException finalException) {
            java.util.function.Consumer<Object> lambda = (final var value) -> { };
        }
    }
    class Local {
        public static int field;
        private static final void method() {}
    }
}

Object anonymous = new Object() {
    public static int field;
    private static final void method() {}
};

class Nested {
    public static int field;
    protected final void method() {}

    static {
        int initializerLocal = 0;
    }
}

interface NestedInterface {
    public static final int FIELD = 1;
    public abstract void method();
}

@interface NestedAnnotation {
    public abstract String value();
}

enum NestedEnum {
    VALUE;

    public static int field;
    private final void method() {}
}

record NestedRecord(int component) {
    public static int field;
    private final void method() {}
}

void main() {}
