/*
UncommentedMain
excludedClasses = .*CompactExcluded$


*/

// non-compiled with javac: Compilable with Java25

class CompactExcluded {
    public static void main(String[] args) {
    }
}

class CompactNotExcluded {
    public static void main(String[] args) { // violation 'Uncommented main method found'
    }
}

void main() {
}
