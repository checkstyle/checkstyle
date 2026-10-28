/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BooleanExpressionComplexity">
      <property name="max" value="1"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
}

void logicalOperators(boolean a, boolean b, boolean c) {
    // violation below 'Boolean expression complexity is 2 (max allowed is 1).'
    if (a && b || c) {
    }
}

void bitwiseOperators(boolean a, boolean b, boolean c) {
    // violation below 'Boolean expression complexity is 2 (max allowed is 1).'
    if (a & b | c) {
    }
}
