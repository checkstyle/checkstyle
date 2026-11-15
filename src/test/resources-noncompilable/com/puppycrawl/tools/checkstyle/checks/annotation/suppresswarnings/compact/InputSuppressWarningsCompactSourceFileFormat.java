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

// violation below 'The warning 'unchecked' cannot be suppressed at this location'
@SuppressWarnings({"unused", "unchecked"})
void method() {
}

// violation below 'The warning 'unchecked' cannot be suppressed at this location'
@SuppressWarnings(value = "unchecked")
void valuePair() {
}

// violation below 'The warning 'unchecked' cannot be suppressed at this location'
@java.lang.SuppressWarnings("unchecked")
void fullyQualified() {
}

// violation below 'The warning 'unchecked' cannot be suppressed at this location'
@SuppressWarnings(true ? "unchecked" : "unused")
void conditional() {
}

void main() {
}

// violation below 'The warning 'unchecked' cannot be suppressed at this location'
@SuppressWarnings("unchecked")
class Nested {
    @SuppressWarnings("unused")
    int allowed;
}
