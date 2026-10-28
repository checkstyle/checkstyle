/*
UncommentedMain
excludedClasses = (default)^$


*/

// non-compiled with javac: Compilable with Java25

class CompactFlaggedMain {
    public static void main(String[] args) { // violation 'Uncommented main method found'
    }
}

class CompactFlaggedVarargsMain {
    public static void main(String... args) { // violation 'Uncommented main method found'
    }
}

class CompactInstanceMain {
    void main() {
    }
}

class CompactWrongTypeMain {
    public static void main(int[] args) {
    }
}

class CompactNonPublicMain {
    static void main(String[] args) {
    }
}

void main() {
}
