/*
RecordComponentNumber
max = (default)8
accessModifiers = (default)public, protected, package, private


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

// violation below 'Number of record components is 9'
record MyRecord(String first, String second, String third, String fourth, String fifth,
        String sixth, String seventh, String eighth, String ninth) {
}

record SmallRecord(String only) {
}
