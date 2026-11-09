/*
Indentation
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = (default)4
throwsIndent = (default)4
arrayInitIndent = (default)4
lineWrappingIndentation = (default)4
forceStrictCondition = (default)false

*/

// non-compiled with javac: Compilable with Java25

int topLevelField = 10;
  int badField = 20; // violation ''member def type' has incorrect indentation level 2'

void validMethod(int param) throws Exception {
    int localVar = param;
    if (localVar > 0) {
        System.out.println(localVar);
    } else {
        System.out.println("non-positive");
    }
}

  void badMethod() { // violation ''method def modifier' has incorrect indentation level 2'
      int badLocal = 5; // violation ''method def' child has incorrect indentation level 6'
}

void testSwitch(int value) {
    switch (value) {
        case 1:
            System.out.println("one");
            break;
        default:
            System.out.println("other");
            break;
    }
}

void testArrayInit() {
    int[] array = new int[] {
        1,
        2,
        3,
    };
}

void testWrapping() {
    int result = 1 + 2
        + 3;
}

void main() throws Exception {
    validMethod(10);
}
