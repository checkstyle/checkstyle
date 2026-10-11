/*
SuperClone


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

public Object clone() { // violation "Method 'clone' should call 'super.clone'"
    return null;
}

class Inner {
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
