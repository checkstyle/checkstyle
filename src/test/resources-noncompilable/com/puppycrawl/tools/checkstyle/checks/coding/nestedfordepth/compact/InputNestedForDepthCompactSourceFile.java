/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NestedForDepth"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
}

void classicLoops() {
    for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 2; j++) { // ok, depth 1 is at the default limit
            // violation below 'Nested for depth is 2 (max allowed is 1)\.'
            for (int k = 0; k < 2; k++) {
                // violation below 'Nested for depth is 3 (max allowed is 1)\.'
                for (int l = 0; l < 2; l++) {
                }
            }
        }
        for (int sibling = 0; sibling < 2; sibling++) { // ok, sibling depth is 1
        }
    }
}

void enhancedLoops(int[] values) {
    for (int i : values) {
        for (int j : values) { // ok, enhanced for depth is 1
            // violation below 'Nested for depth is 2 (max allowed is 1)\.'
            for (int k : values) {
            }
        }
        for (int sibling : values) { // ok, sibling depth is 1
        }
    }
}

void followingMethod(int[] values) {
    for (int i : values) { // ok, depth resets for the next method
        for (int j = 0; j < 2; j++) { // ok, mixed loop depth is 1
        }
    }
}
