/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NPathComplexity"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
}

// violation below 'NPath Complexity is 400 (max allowed is 200)\.'
void overLimit(boolean first, boolean second, boolean third, boolean fourth) {
    // Each condition multiplies the complexity: 2 * 4 * 5 * 5 * 2 = 400.
    if (first) {
    }
    if (first && second && third) {
    }
    if (first && second && third && fourth) {
    }
    if (first || second || third || fourth) {
    }
    if (second) {
    }
}

void atLimit(boolean first, boolean second, boolean third, boolean fourth) {
    // The previous method must not affect this complexity: 2 * 4 * 5 * 5 = 200.
    if (first) {
    }
    if (first && second && third) {
    }
    if (first && second && third && fourth) {
    }
    if (first || second || third || fourth) {
    }
}
