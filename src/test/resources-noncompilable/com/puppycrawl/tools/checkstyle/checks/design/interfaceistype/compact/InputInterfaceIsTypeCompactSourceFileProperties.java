/*
InterfaceIsType
allowMarkerInterfaces = false


*/

// non-compiled with javac: Compilable with Java25

interface Marker { // violation 'interfaces should describe a type'
}

interface ConstantsOnly { // violation 'interfaces should describe a type'
    int MAX = 1;
}

interface WithMethod {
    void run();
}

void main() {
}

class Inner {
    interface NestedMarker { // violation 'interfaces should describe a type'
    }

    interface NestedConstants { // violation 'interfaces should describe a type'
        String NAME = "inner";
    }

    interface NestedWithMethod {
        void run();
    }
}
