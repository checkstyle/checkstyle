/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodLength"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
    int value = increment(1);
    System.out.println(value);
}

int increment(int value) {
    // Comments and blank lines count with the default configuration.

    return value + 1;
}
