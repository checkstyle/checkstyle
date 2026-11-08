/*
MissingOverrideOnRecordAccessor


*/

// non-compiled with javac: Compilable with Java25

void main() {}

record MyRecord(String name, int value) {
    // violation below 'Record component accessor method must include @java.lang.Override'
    public String name() {
        return name;
    }

    @Override
    public int value() {
        return value;
    }
}
