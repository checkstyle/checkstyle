/*
SuppressWarnings
format = (default)^\\s*+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF, PATTERN_VARIABLE_DEF, MODULE_DEF


*/

// non-compiled with javac: Compilable with Java25

@SuppressWarnings("") // violation 'The warning '' cannot be suppressed at this location'
int field = 0;

@SuppressWarnings("unused")
int allowedField = 0;

@SuppressWarnings("   ") // violation 'The warning '   ' cannot be suppressed at this location'
void method(@SuppressWarnings("") int param) { // violation 'The warning '' cannot be suppressed'
    @SuppressWarnings({"unused", ""}) // violation 'The warning '' cannot be suppressed'
    Object local = param;
    if (local instanceof @SuppressWarnings("") String text) { // violation 'The warning '''
        System.out.println(text);
    }
}

void main() {
}

@SuppressWarnings("") // violation 'The warning '' cannot be suppressed at this location'
class Nested {
    @SuppressWarnings("") // violation 'The warning '' cannot be suppressed at this location'
    Nested() {
    }
}

@SuppressWarnings("") // violation 'The warning '' cannot be suppressed at this location'
interface Shape {
}

@SuppressWarnings("") // violation 'The warning '' cannot be suppressed at this location'
enum Color {
    @SuppressWarnings("") // violation 'The warning '' cannot be suppressed at this location'
    RED
}

@SuppressWarnings("") // violation 'The warning '' cannot be suppressed at this location'
record Point(int x) {
    @SuppressWarnings("") // violation 'The warning '' cannot be suppressed at this location'
    Point {
    }
}

@SuppressWarnings("") // violation 'The warning '' cannot be suppressed at this location'
@interface Marker {
    @SuppressWarnings("") // violation 'The warning '' cannot be suppressed at this location'
    String value();
}
