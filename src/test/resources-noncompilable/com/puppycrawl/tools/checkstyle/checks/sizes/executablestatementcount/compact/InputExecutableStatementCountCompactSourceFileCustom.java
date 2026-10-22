/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ExecutableStatementCount">
      <property name="max" value="2"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() { // ok, the lambda body is counted separately from these two statements
    // violation below 'Executable statement count is 3 (max allowed is 2)\.'
    Runnable task = () -> {
        System.out.println("first");
        System.out.println("second");
        System.out.println("third");
    };
    task.run();
}

// violation below 'Executable statement count is 3 (max allowed is 2)\.'
void overLimit() {
    System.out.println("first");
    System.out.println("second");
    System.out.println("third");
}

void atLimit() { // ok, exactly two statements after a violating method
    System.out.println("first");
    System.out.println("second");
}

void belowLimit() { // ok, no statements
}
