/*
CovariantEquals


*/

// non-compiled with javac: Compilable with Java25

public boolean equals(String other) { // violation 'covariant equals'
    return false;
}

public boolean equals(Integer other) { // violation 'covariant equals'
    return false;
}

void main() { }
