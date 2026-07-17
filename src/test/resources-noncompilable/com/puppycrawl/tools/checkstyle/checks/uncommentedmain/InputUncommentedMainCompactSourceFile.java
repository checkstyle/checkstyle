/*
UncommentedMain
excludedClasses = (default)^$


*/

// non-compiled with javac: Compilable with Java25

void main() { // violation 'Uncommented main method found'
    System.out.println("hello");
}

void main(String[] args) { // violation 'Uncommented main method found'
    System.out.println("hello");
}

public static void main(String[] args) { // violation 'Uncommented main method found'
    System.out.println("hello");
}

int main() {
    return 0;
}

void notMain() {
    System.out.println("not main");
}
