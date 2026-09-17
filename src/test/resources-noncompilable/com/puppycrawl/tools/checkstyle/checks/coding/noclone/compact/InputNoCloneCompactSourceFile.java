/*
NoClone


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

public Object clone() { // violation 'Avoid using clone method.'
    return this;
}

class Helper implements Cloneable {

    // violation below 'Avoid using clone method.'
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
