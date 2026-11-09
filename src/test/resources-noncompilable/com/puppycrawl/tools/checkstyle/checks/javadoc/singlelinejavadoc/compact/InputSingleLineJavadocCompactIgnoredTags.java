/*
SingleLineJavadoc
violateExecutionOnNonTightHtml = (default)false
ignoredTags = @deprecated, @throws, @ignoredCustomTag
ignoreInlineTags = false


*/

// non-compiled with javac: Compilable with Java25

/** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)} */
void foo() {
    // violation 2 lines above 'Single-line Javadoc comment should be multi-line.'
}

/**
 * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
 */
void foo1() {}

/** @throws Exception if a problem occurs */
void foo2() {}

/**
 * @throws Exception if a problem occurs
 */
void foo3() {}

/** An especially short bit of Javadoc. */
void foo4() {}

/**
 * An especially short bit of Javadoc.
 */
void foo5() {}

/** @deprecated */
void foo6() {}

void main() {}
