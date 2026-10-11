/*
EqualsHashCode


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

public boolean equals(Object obj) { // violation 'without .* of 'hashCode()'.'
    return this == obj;
}

class Helper {

    public int hashCode() { // violation 'without .* of 'equals()'.'
        return 1;
    }

}
