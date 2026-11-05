/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="VariableDeclarationUsageDistance"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
    int a = 1; // ok, initialization sequence on the same receiver
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println(a);
}

void methodWithViolation() {
    int b = 1; // violation 'Distance .* is 4.'
    first();
    second();
    third();
    System.out.println(b);
}

void first() {
}

void second() {
}

void third() {
}

void atDefaultDistance() {
    int atLimit = 1; // ok, distance equals allowedDistance
    first();
    second();
    System.out.println(atLimit);
}

void atCustomDistance() {
    int adjacent = 1; // ok, distance of 1
    System.out.println(adjacent);
}

void beyondCustomDistance() {
    int notIgnored = 1;
    first();
    System.out.println(notIgnored);
}

void finalVariable() {
    final int finalValue = 1; // ok, final variables are ignored
    first();
    second();
    third();
    System.out.println(finalValue);
}

void ignoredVariable() {
    int ignoredValue = 1; // violation 'first usage is 4'
    first();
    second();
    third();
    System.out.println(ignoredValue);
}

void nestedScope() {
    int nested = 1; // ok, usage is in a nested scope
    first();
    second();
    {
        third();
        System.out.println(nested);
    }
}
