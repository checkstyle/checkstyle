/*
MissingJavadocType
excludeScope = (default)null
scope = (default)public
skipAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

// non-compiled with javac: Compilable with Java25

// The implicit class of a compact source file is not public, so even public
// member types are not in the default public scope.
public class PublicNested {
    public class Inner {
    }
}

class Nested {
}

public record Point(int x) {
}

public enum Color {
    RED
}

public interface Shape {
}

public @interface Marker {
}

void main() {
}
