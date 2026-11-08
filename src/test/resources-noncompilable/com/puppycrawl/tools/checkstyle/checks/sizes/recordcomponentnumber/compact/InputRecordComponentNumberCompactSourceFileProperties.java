/*
RecordComponentNumber
max = 3
accessModifiers = public


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

// violation below 'Number of record components is 4'
public record MyPublicRecord(String first, String second, String third, String fourth) {
}

record MyPackageRecord(String first, String second, String third, String fourth,
        String fifth) {
}
