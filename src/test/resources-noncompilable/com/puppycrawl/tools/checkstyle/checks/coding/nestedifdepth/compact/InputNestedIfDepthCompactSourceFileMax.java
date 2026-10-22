/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NestedIfDepth">
      <property name="max" value="2"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
    if (true) {
        if (true) {
            if (true) { // ok, depth is at the configured maximum
                if (true) { // violation 'Nested if-else depth is 3 (max allowed is 2)'
                }
            }
        }
    }
}

void elseIfChain(int value) {
    if (value == 0) {
    } else if (value == 1) {
    } else if (value == 2) {
        if (value > 0) {
            if (value > 1) { // ok, else-if branches do not increase the depth
            }
        }
    }
}

void depthResets(boolean first, boolean second) {
    if (first) {
        if (second) {
            if (first) { // ok, depth resets after the preceding method
            }
        }
    }
    if (second) {
        if (first) {
            if (second) { // ok, depth resets between sibling if statements
            }
        }
    }
}
