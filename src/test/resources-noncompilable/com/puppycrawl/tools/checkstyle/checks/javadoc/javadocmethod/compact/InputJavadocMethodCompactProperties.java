/*
JavadocMethod
accessModifiers = public
allowInlineReturn = true
allowMissingParamTags = true
allowMissingReturnTag = true
allowedAnnotations = Deprecated
validateThrows = true
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

import java.io.IOException;

public class Inner {

    /**
     * Method using inline return tag {@return the number} instead of block return tag.
     * Missing param tag is also allowed by allowMissingParamTags.
     */
    public int inlineReturn(int x) {
        return x;
    }

    /**
     * Method missing return tag, allowed by allowMissingReturnTag.
     * Missing param tag is also allowed.
     */
    public int missingReturn(int x) {
        return x;
    }

    @Deprecated
    public int annotatedMethod(int x) {
        return x;
    }

    // violation 4 lines below 'Expected @throws tag for 'IOException'.'
    /**
     * Method declaring throws without javadoc tag.
     */
    public void throwsMethod() throws IOException {
        throw new IOException();
    }

    void packagePrivateMethod() throws IOException {
        throw new IOException();
    }
}

void main() {
}
