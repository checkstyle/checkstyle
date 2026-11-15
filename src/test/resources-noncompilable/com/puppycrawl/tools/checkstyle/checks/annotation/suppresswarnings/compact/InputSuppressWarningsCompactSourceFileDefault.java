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

// violation below 'The warning '   ' cannot be suppressed at this location'
@SuppressWarnings("   ")
void method(@SuppressWarnings("") int param) { // violation 'The warning '' cannot be suppressed'
    @SuppressWarnings({"unused", ""}) // violation 'The warning '' cannot be suppressed'
    Object local = param;
    if (local instanceof @SuppressWarnings("") String text) { // violation 'The warning '''
        System.out.println(text);
    }
}

void main() {
}

// violation below 'The warning '' cannot be suppressed at this location'
@SuppressWarnings("")
class Nested {
    // violation below 'The warning '' cannot be suppressed at this location'
    @SuppressWarnings("")
    Nested() {
    }
}

// violation below 'The warning '' cannot be suppressed at this location'
@SuppressWarnings("")
interface Shape {
}

// violation below 'The warning '' cannot be suppressed at this location'
@SuppressWarnings("")
enum Color {
    // violation below 'The warning '' cannot be suppressed at this location'
    @SuppressWarnings("")
    RED
}

// violation below 'The warning '' cannot be suppressed at this location'
@SuppressWarnings("")
record Point(int x) {
    // violation below 'The warning '' cannot be suppressed at this location'
    @SuppressWarnings("")
    Point {
    }
}

// violation below 'The warning '' cannot be suppressed at this location'
@SuppressWarnings("")
@interface Marker {
    // violation below 'The warning '' cannot be suppressed at this location'
    @SuppressWarnings("")
    String value();
}
