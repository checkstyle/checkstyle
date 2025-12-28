/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

// non-compiled with javac: Compilable with Java25

interface LocalContract {
    void run();
}

final class FinalHelper {
    protected void finalThroughClassDef() {
        System.identityHashCode("final class blocks extension");
    }
}

class ExtensibleType {
    protected void hook() { // violation 'hook' can be overridden and is non-empty
        System.identityHashCode("overridable with implementation");
    }

    protected void emptyHook() {
    }

    private void privateWorker() {
        System.identityHashCode("private methods are not overridable");
    }

    public final void finalApi() {
        System.identityHashCode("final methods are not overridable");
    }

    public static void staticApi() {
        System.identityHashCode("static methods are not overridable");
    }
}

enum LocalEnum {
    A, B;

    public int value() {
        return 1;
    }
}

void helperTopLevel() {
    System.identityHashCode("top-level method in compact file: must not be checked");
}

int topLevelField = 7;

void main() {
    new ExtensibleType().finalApi();
    helperTopLevel();
    System.out.println(topLevelField);
}
