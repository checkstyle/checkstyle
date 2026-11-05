/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="VariableDeclarationUsageDistance">
      <property name="allowedDistance" value="1"/>
      <property name="ignoreVariablePattern" value="^ignored.*"/>
      <property name="validateBetweenScopes" value="true"/>
      <property name="ignoreFinal" value="false"/>
    </module>
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
    int b = 1; // violation 'first usage is 4,'
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
    int atLimit = 1; // violation 'first usage is 3,'
    first();
    second();
    System.out.println(atLimit);
}

void atCustomDistance() {
    int adjacent = 1; // ok, distance of 1
    System.out.println(adjacent);
}

void beyondCustomDistance() {
    int notIgnored = 1; // violation 'first usage is 2,'
    first();
    System.out.println(notIgnored);
}

void finalVariable() {
    final int finalValue = 1; // violation 'first usage is 4,'
    first();
    second();
    third();
    System.out.println(finalValue);
}

void ignoredVariable() {
    int ignoredValue = 1; // ok, matches ignoreVariablePattern
    first();
    second();
    third();
    System.out.println(ignoredValue);
}

void nestedScope() {
    int nested = 1; // violation 'first usage is 4,'
    first();
    second();
    {
        third();
        System.out.println(nested);
    }
}
