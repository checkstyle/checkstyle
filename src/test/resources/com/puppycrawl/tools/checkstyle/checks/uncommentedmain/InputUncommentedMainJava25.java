/*
UncommentedMain
excludedClasses = (default)^$


*/

package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

public class InputUncommentedMainJava25 {
    void main() { // violation 'Uncommented main method found'
    }
}

class PackageAccessInstanceMain {
    void main(String[] args) { // violation 'Uncommented main method found'
    }
}

class PackageAccessStaticMain {
    static void main() { // violation 'Uncommented main method found'
    }
}

class ProtectedStaticMain {
    protected static void main(String... args) { // violation 'Uncommented main method found'
    }
}

class PrivateInstanceMain {
    private void main() {
    }
}

class InvalidMainMethods {
    public static int main() {
        return 0;
    }

    public static void main(String arg) {
    }

    public static void main(int[] args) {
    }
}
