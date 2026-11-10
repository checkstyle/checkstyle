/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = @return the *|This method returns
period = _


*/

// non-compiled with javac: Compilable with Java25

/**
 * {@summary A simple correct Javadoc_}
 */
void foo1() {}

// violation 2 lines below 'Forbidden summary fragment.'
/**
 * {@summary This method returns something_}
 */
void foo2() {}

// violation 2 lines below 'Summary of Javadoc is missing an ending period.'
/**
 * {@summary This code is wrong }
 */
void foo3() {}

// violation below 'First sentence of Javadoc is missing an ending period.'
/**
 * Sentence without underscore
 */
void foo4() {}

/**
 * Sentence with underscore_
 */
void foo5() {}

void main() {}
