/*
DuplicateMapOrSetKey

*/

// non-compiled with javac: Compilable with Java25

void main() {
    // violation below 'Duplicate key or element argument identical to the argument at line '10'.'
    java.util.Set.of("a", "b", "a");
}
