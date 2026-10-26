/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BooleanExpressionComplexity"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
}

void logicalOperators(boolean a, boolean b, boolean c, boolean d, boolean e) {
    // violation below 'Boolean expression complexity is 4 (max allowed is 3).'
    if (a && b || c && d || e) {
    }
}

void bitwiseOperators(boolean a, boolean b, boolean c, boolean d, boolean e) {
    // violation below 'Boolean expression complexity is 4 (max allowed is 3).'
    if (a & b | c ^ d & e) {
    }
}
