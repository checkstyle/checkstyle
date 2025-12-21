/*
FinalLocalVariable
validateEnhancedForLoopVariable = true
validateUnnamedVariables = true
tokens = VARIABLE_DEF, PARAMETER_DEF


*/

// non-compiled with javac: Compilable with Java25

String prefix = "Run: ";

int sum(int left, int right) {
    // 2 violations above
    // 'Variable 'left' should be declared final'
    // 'Variable 'right' should be declared final'
    int base = left + right; // violation, 'Variable 'base' should be declared final'
    int mutable = 0;
    mutable += base;
    return mutable;
}

void main() {
    String[] args = {"a", "bb", "ccc"}; // violation, 'Variable 'args' should be declared final'

    int total = 0;
    for (String s : args) { // violation, 'Variable 's' should be declared final'
        total += s.length();
    }

    String report = prefix + total; // violation, 'Variable 'report' should be declared final'
    IO.println(report);

    String _ = "ignored"; // violation, 'Variable '_' should be declared final'
    int result = sum(total, 5); // violation, 'Variable 'result' should be declared final'
    IO.println("Result=" + result);

    for (var i : args) { // violation, 'Variable 'i' should be declared final'
        IO.println(i);
    }
}
