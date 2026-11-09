/*
MissingJavadocType
excludeScope = package
scope = private
skipAnnotations = Deprecated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

// non-compiled with javac: Compilable with Java25

/** Javadoc. */
private class Documented {
}

private class Nested { // violation 'Missing a Javadoc comment for 'Nested'.'
    private class PrivateInner { // violation 'Missing a Javadoc comment for 'PrivateInner'.'
    }

    class PackageInner { // violation 'Missing a Javadoc comment for 'PackageInner'.'
    }
}

@Deprecated
private class Skipped {
}

class PackageClass {
}

private record Point(int x) { // violation 'Missing a Javadoc comment for 'Point'.'
}

private enum Color { // violation 'Missing a Javadoc comment for 'Color'.'
    RED
}

private interface Shape { // violation 'Missing a Javadoc comment for 'Shape'.'
}

private @interface Marker { // violation 'Missing a Javadoc comment for 'Marker'.'
}

void main() {
}
