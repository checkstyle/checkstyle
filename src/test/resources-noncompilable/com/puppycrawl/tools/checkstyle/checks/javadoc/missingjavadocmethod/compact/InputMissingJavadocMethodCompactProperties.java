/*
MissingJavadocMethod
minLineCount = 2
allowedAnnotations = Deprecated
scope = private
excludeScope = public
allowMissingPropertyJavadoc = true
ignoreMethodNamesRegex = ^ignored.*
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

int value = 0;

int getValue() {
    return value;
}

void setValue(int value) {
    this.value = value;
}

void ignoredMethod() {
    value++;
    value++;
    value++;
}

void shortMethod() {
    value++;
}

void longMethod() { // violation 'Missing a Javadoc comment for 'longMethod'.'
    value++;
    value++;
    value++;
}

@Deprecated
void annotatedMethod() {
    value++;
    value++;
    value++;
}

void main() {
}

class Nested {
    private void nestedLongMethod() { // violation 'Missing .* for 'nestedLongMethod'.'
        value++;
        value++;
        value++;
    }

    public void excludedMethod() {
        value++;
        value++;
        value++;
    }
}
