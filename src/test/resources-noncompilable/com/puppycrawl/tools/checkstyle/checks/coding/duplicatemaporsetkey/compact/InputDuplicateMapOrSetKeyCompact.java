/*
DuplicateMapOrSetKey

*/

// non-compiled with javac: Compilable with Java25

void main() {
    java.util.Set.of("a", "b", "a"); // violation 'Duplicate key or element argument identical to the argument at line 8.'
}
