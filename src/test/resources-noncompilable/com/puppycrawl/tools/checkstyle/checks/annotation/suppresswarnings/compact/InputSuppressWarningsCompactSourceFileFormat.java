/*
SuppressWarnings
format = ^unchecked$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF, PATTERN_VARIABLE_DEF, MODULE_DEF


*/

// non-compiled with javac: Compilable with Java25

@SuppressWarnings("unchecked") // violation 'The warning 'unchecked' cannot be suppressed'
int field = 0;

@SuppressWarnings("")
int emptyAllowed = 0;

@SuppressWarnings({"unused", "unchecked"}) // violation 'The warning 'unchecked' cannot be'
void method() {
}

@SuppressWarnings(value = "unchecked") // violation 'The warning 'unchecked' cannot be'
void valuePair() {
}

@java.lang.SuppressWarnings("unchecked") // violation 'The warning 'unchecked' cannot be'
void fullyQualified() {
}

// violation below 'The warning 'unchecked' cannot be suppressed at this location'
@SuppressWarnings(true ? "unchecked" : "unused")
void conditional() {
}

void main() {
}

@SuppressWarnings("unchecked") // violation 'The warning 'unchecked' cannot be suppressed'
class Nested {
    @SuppressWarnings("unused")
    int allowed;
}
