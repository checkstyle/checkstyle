/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
ignoreTooShortPattern = (default)[.:,]$
lineLimit = (default)80
validateOnlyJoinableLines = (default)true
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

/**
 * Valid compact source main method.
 */
void main() {}

// violation 3 lines below 'Line under-utilized'
/**
 * @return
 * This line is intentionally short
 * and should be reported as too short.
 */
int helper() {
    return 0;
}
