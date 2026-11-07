/*
InterfaceIsType
allowMarkerInterfaces = (default)true


*/

// non-compiled with javac: Compilable with Java25

interface Marker {
}

interface ConstantsOnly { // violation 'interfaces should describe a type'
    int MAX = 1;
}

interface WithMethod {
    void run();
}

interface ConstantAndMethod {
    int SIZE = 1;

    int size();
}

void main() {
}

class Inner {
    interface NestedMarker {
    }

    interface NestedConstants { // violation 'interfaces should describe a type'
        String NAME = "inner";
    }

    interface NestedWithMethod {
        void run();
    }
}
