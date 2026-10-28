/*
HideUtilityClassConstructor
ignoreAnnotatedBy = (default)

*/

// non-compiled with javac: Compilable with Java25

class CompactUtility { // violation 'should not have a public or default constructor'
    public static void func() {}
}

class CompactUtilityPublicCtor { // violation 'should not have a public or default constructor'
    public CompactUtilityPublicCtor() {}

    public static void func() {}
}

class CompactUtilityPrivateCtor {
    private CompactUtilityPrivateCtor() {}

    public static void func() {}
}

class CompactNonUtility {
    public void func() {}
}

static class CompactStaticUtility {
    public static void func() {}
}

void main() {
}
