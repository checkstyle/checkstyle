/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

// non-compiled with javac: Compilable with Java25

/**
 * {@summary A simple correct Javadoc.}
 */
void foo1() {}

// violation 2 lines below 'Summary of Javadoc is missing an ending period.'
/**
 * {@summary This code is wrong }
 */
void foo2() {}

// violation below 'Summary javadoc is missing.'
/**
 * @throws Exception if a problem occurs.
 */
void foo3() {}

/**
 * {@summary <p>This code is right.</p>}
 */
void foo4() {}

// violation below 'First sentence of Javadoc is missing an ending period.'
/**
 * Sentence without period
 */
void foo5() {}

void main() {}
