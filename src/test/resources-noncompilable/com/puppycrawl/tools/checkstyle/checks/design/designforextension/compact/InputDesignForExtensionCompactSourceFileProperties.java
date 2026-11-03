/*
DesignForExtension
ignoredAnnotations = Deprecated, Override
requiredJavadocPhrase = This method can be overridden in subclasses


*/

void main() {
}

public void topLevelMethod() {
    System.identityHashCode("Top level method in unnamed class");
}

class InnerClass {
    /**
     * This method can be overridden in subclasses.
     */
    public void methodWithRequiredPhrase() {
        System.identityHashCode("Valid javadoc phrase");
    }

    /**
     * Just some other javadoc.
     */
    public void methodWithoutRequiredPhrase() {
        System.identityHashCode("Invalid javadoc phrase");
    }

    @Deprecated
    public void deprecatedMethod() {
        System.identityHashCode("Deprecated is ignored");
    }

    @Override
    public String toString() {
        return "Override is ignored";
    }

    public void plainMethod() {
        System.identityHashCode("Plain method without javadoc or ignored annotation");
    }
}

