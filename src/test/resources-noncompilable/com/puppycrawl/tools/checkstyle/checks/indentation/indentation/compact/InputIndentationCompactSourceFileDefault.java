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
  int topLevelFieldIncorrect = 20; // violation ''member def type' has incorrect indentation level 2, expected level should be 0.'

void validMethod(int param) throws Exception {
    int localVar = param;
    if (localVar > 0) {
        System.out.println(localVar);
    } else {
        System.out.println("non-positive");
    }
}

  void invalidMethodIndent() { // violation ''method def modifier' has incorrect indentation level 2, expected level should be 0.'
      int badLocal = 5; // violation ''method def' child has incorrect indentation level 6, expected level should be 4.'
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

void main() {
    validMethod(10);
}
