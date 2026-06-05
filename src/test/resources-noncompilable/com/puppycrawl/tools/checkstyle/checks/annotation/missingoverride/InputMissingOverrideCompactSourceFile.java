/*
MissingOverride
javaFiveCompatibility = true


*/

// non-compiled with javac: Compilable with Java25

/**
 * {@inheritDoc}
 */
void doStuff() { } // violation 'include @java.lang.Override annotation when '@inheritDoc''

/**
 * {@inheritDoc}
 */
@Override
void doOtherStuff() { }

void main() { }
