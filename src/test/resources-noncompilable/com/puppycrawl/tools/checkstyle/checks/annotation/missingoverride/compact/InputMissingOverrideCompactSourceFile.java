/*
MissingOverride
javaFiveCompatibility = true


*/

// non-compiled with javac: Compilable with Java25

/**
 * {@inheritDoc}
 */
public int hashCode() { // violation 'include @java.lang.Override annotation when '@inheritDoc''
    return 1;
}

/**
 * {@inheritDoc}
 */
@Override
public String toString() {
    return "";
}

void main() { }
