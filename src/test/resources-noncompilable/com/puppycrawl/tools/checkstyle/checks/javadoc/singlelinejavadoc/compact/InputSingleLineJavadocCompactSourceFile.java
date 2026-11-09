/*
SingleLineJavadoc
violateExecutionOnNonTightHtml = (default)false
ignoredTags = (default)
ignoreInlineTags = (default)true


*/

// non-compiled with javac: Compilable with Java25

/** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)} */
void foo() {}

/**
 * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
 */
void foo1() {}

// violation 2 lines below 'Single-line Javadoc comment should be multi-line.'

/** @throws Exception if a problem occurs */
void foo2() {}

/**
 * @throws Exception if a problem occurs
 */
void foo3() {}

/** An especially short bit of Javadoc. */
void foo4() {}

// violation 2 lines below 'Single-line Javadoc comment should be multi-line.'

/** @deprecated */
void foo5() {}

// violation 2 lines below 'Single-line Javadoc comment should be multi-line.'

/** @see String */
void foo6() {}

void main() {}
