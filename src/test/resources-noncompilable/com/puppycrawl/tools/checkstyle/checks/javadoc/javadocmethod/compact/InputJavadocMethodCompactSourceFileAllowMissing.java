/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = public
allowMissingParamTags = true
allowMissingReturnTag = true
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

/** Missing param and return tags are allowed. */
public int allowed(int value) {
    return value;
}

// ok below until https://github.com/checkstyle/checkstyle/issues/20221
/** @param other not a parameter */
public void unused() {
}

void main() {
}

/** Api. */
public class Api {
    /** Missing param and return tags are allowed. */
    public int allowed(int value) {
        return value;
    }

    // violation below 'Unused @param tag for 'other'.'
    /** @param other not a parameter */
    public void unused() {
    }

    /** @param other only public methods are checked */
    void notPublic() {
    }
}
