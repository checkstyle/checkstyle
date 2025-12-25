/*
MissingOverride
javaFiveCompatibility = true


*/

// non-compiled with javac: Compilable with Java25

/** {@inheritDoc} */
boolean ee(Object o) { // ok, @Override is not allowed on top level variables
    return this == o;
}

/** {@inheritDoc} */
void main() { // ok, @Override is not allowed on top level variables
    return;
}

static class NestedA {
    /** {@inheritDoc} */
    public String toString() { // violation 'include @java.lang.Override'
        return "NestedA";
    }

    /** {@inheritDoc} */
    private void hidden() { // violation '{@inheritDoc} tag is not valid at this location.'
    }

    /** {@inheritDoc} */
    static void util() { // violation '{@inheritDoc} tag is not valid at this location.'
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        return other instanceof NestedA;
    }
}

interface I {
    /** {@inheritDoc} */
    void run(); // violation 'include @java.lang.Override'
}

enum E {
    A;

    /** {@inheritDoc} */
    public String toString() { // violation 'include @java.lang.Override'
        return name();
    }
}
