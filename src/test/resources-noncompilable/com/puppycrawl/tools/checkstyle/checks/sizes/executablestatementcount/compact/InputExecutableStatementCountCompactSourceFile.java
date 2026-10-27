/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ExecutableStatementCount"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

// violation below 'Executable statement count is 31 (max allowed is 30)\.'
void main() {
    int value = 0;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
}

// The count must reset after the violating main method.
void atLimit() {
    int value = 0;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
    value++;
}
