/*
FinalClass


*/

// non-compiled with javac: Compilable with Java25

class Alpha { // violation 'Class Alpha should be declared as final'
    private Alpha() { }
}

final class Beta {
    private Beta() { }
}

abstract class Gamma {
    private Gamma() { }
}

class Delta {
    Delta() { }
}

class Epsilon {
    private Epsilon() { }
}

class Zeta extends Epsilon { // violation 'Class Zeta should be declared as final'
    private Zeta() { }
}

void main() { }
