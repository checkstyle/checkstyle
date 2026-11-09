/*
Indentation
basicOffset = 2
braceAdjustment = 2
caseIndent = 2
throwsIndent = 2
arrayInitIndent = 2
lineWrappingIndentation = 2
forceStrictCondition = true

*/

// non-compiled with javac: Compilable with Java25

int topLevelField = 10;

void testMethod(int param)
  throws Exception {
  int localVar = param;
  if (localVar > 0)
  { // violation ''if lcurly' has incorrect indentation level 2, expected level should be 4.'
    System.out.println(localVar);
  } // violation ''if rcurly' has incorrect indentation level 2, expected level should be 4.'
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
  };
}

void testWrapping() {
  int result = 1 + 2
    + 3;
}

void main() throws Exception {
  testMethod(10);
}
