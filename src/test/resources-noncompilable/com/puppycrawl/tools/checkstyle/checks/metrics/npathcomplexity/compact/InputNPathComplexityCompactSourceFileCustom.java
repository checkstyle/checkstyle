/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NPathComplexity">
      <property name="max" value="4"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
}

// violation below 'NPath Complexity is 6 (max allowed is 4)\.'
void overLimit(boolean first, boolean second) {
    if (first && second) {
    }
    if (first) {
    }
}

void atLimit(boolean first, boolean second) { // ok, complexity is 4 after a violating method
    if (first) {
    }
    if (second) {
    }
}

// violation below 'NPath Complexity is 5 (max allowed is 4)\.'
int ternary(boolean first, boolean second, boolean third) {
    return first && second ? 1 : third ? 2 : 3;
}

int belowLimit() { // ok, complexity is 1 after a violating method
    return 1;
}
