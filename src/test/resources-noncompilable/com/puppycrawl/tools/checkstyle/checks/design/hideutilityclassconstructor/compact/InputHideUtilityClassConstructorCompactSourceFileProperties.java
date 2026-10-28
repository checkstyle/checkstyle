/*
HideUtilityClassConstructor
ignoreAnnotatedBy = Skip, Other

*/

// non-compiled with javac: Compilable with Java25

@Skip
class CompactIgnoredUtility {
    public static void func() {}
}

@Other
class CompactOtherIgnoredUtility {
    public CompactOtherIgnoredUtility() {}

    public static void func() {}
}

@CommonAnnot // violation 'should not have a public or default constructor'
class CompactFlaggedUtility {
    public static void func() {}
}

class CompactPlainUtility { // violation 'should not have a public or default constructor'
    public static void func() {}
}

@interface Skip {}

@interface Other {}

@interface CommonAnnot {}

void main() {
}
