/*
UnnecessaryTypeArgumentsWithRecordPattern

*/

// non-compiled with javac: Compilable with Java25

record Pair<T, U>(T t, U u) {}

void main() {
    final Pair<String, Integer> pair = new Pair<>("one", 1);
    testInstanceof(pair);
    testSwitchViolation(pair);
    testSwitchCorrect(pair);
}

void testInstanceof(Pair<String, Integer> pair) {
    // violation below 'Unnecessary type arguments with record pattern.'
    if (pair instanceof Pair<String, Integer>(var a, var b)) {
        System.out.println(a + " " + b);
    }

    if (pair instanceof Pair(var a, var b)) {
        System.out.println(a + " " + b);
    }
}

void testSwitchViolation(Pair<String, Integer> pair) {
    switch (pair) {
        // violation below 'Unnecessary type arguments with record pattern.'
        case Pair<String, Integer>(var a, var b) ->
            System.out.println(a + " " + b);
        default -> {}
    }
}

void testSwitchCorrect(Pair<String, Integer> pair) {
    switch (pair) {
        case Pair(var a, var b) ->
            System.out.println(a + " " + b);
        default -> {}
    }
}
