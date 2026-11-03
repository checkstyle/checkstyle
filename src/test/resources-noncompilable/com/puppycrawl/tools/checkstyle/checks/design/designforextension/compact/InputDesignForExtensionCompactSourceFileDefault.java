/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

void main() {
}

public void topLevelMethod() {
    System.identityHashCode("Top level method in unnamed class");
}

class InnerClass {
    public void nonFinalMethod() {
        System.identityHashCode("Non-final method in inner class");
    }

    public final void finalMethod() {
        System.identityHashCode("Final method");
    }

    private void privateMethod() {
        System.identityHashCode("Private method");
    }
}

