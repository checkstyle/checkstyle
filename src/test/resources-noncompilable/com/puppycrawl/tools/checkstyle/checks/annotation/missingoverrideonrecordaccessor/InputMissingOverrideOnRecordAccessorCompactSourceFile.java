/*
MissingOverrideOnRecordAccessor

*/

// non-compiled with javac: Compilable with Java25

record Point(int x, int y) {

    // violation below 'Record component accessor method must include @java.lang.Override'
    public int x() {
        return x;
    }

}

void main() {
}
