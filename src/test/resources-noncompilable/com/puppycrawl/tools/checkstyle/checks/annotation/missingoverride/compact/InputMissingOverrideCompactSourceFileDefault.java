/*
MissingOverride
javaFiveCompatibility = (default)false


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

interface Greeter {
    void greet();
}

class GreeterImpl implements Greeter {

    /**
     * {@inheritDoc}
     */
    public void greet() {
        // violation above 'include @java.lang.Override annotation when '@inheritDoc''
    }
}

void main() { }
