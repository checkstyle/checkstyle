/*
Indentation
arrayInitIndent = (default)4
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = (default)4
forceStrictCondition = true
lineWrappingIndentation = (default)4
throwsIndent = (default)4

*/

// non-compiled with javac: Compilable with Java25

void met(int a,
    int b)
        // violation below 'incorrect indentation level 8, expected level should be 4.'
        throws Exception {}

void main() {
}
