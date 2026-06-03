/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = package
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

private class Hidden {

    public int helper() {
        return 1;
    }
}

void main() { // violation 'Missing a Javadoc comment.'
}
