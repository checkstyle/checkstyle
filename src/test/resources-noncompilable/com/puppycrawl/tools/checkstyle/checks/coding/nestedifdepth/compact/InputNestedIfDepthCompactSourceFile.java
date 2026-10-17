/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NestedIfDepth"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
    if (true) {
        if (true) { // ok, depth is at the default maximum
            if (true) { // violation 'Nested if-else depth is 2 (max allowed is 1)'
                if (true) { // violation 'Nested if-else depth is 3 (max allowed is 1)'
                }
            }
        }
    }
}

void elseIfChain(int value) {
    if (value == 0) {
    } else if (value == 1) {
    } else if (value == 2) {
        if (value > 0) { // ok, else-if branches do not increase the depth
        }
    } else {
        if (value > 0) { // ok, an else containing only an if is an else-if
            if (value > 1) { // ok, depth is at the default maximum
            }
        }
    }
}

void nestedInElseIf(int value) {
    if (value == 0) {
    } else if (value == 1) {
        if (value > 0) {
            if (value > 1) { // violation 'Nested if-else depth is 2 (max allowed is 1)'
            }
        }
    }
}

void depthResets(boolean first, boolean second) {
    if (first) {
        if (second) { // ok, depth resets after the preceding method
        }
    }
    if (second) {
        if (first) { // ok, depth resets between sibling if statements
        }
    }
}
