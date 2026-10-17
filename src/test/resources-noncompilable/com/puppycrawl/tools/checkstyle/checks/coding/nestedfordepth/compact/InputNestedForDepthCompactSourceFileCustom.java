/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NestedForDepth">
      <property name="max" value="2"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
}

void classicLoops() {
    for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 2; k++) { // ok, depth 2 is at the custom limit
                // violation below 'Nested for depth is 3 (max allowed is 2)\.'
                for (int l = 0; l < 2; l++) {
                }
            }
            for (int sibling = 0; sibling < 2; sibling++) { // ok, sibling depth is 2
            }
        }
    }
}

void enhancedLoops(int[] values) {
    for (int i : values) {
        for (int j : values) {
            for (int k : values) { // ok, enhanced for depth is 2
                // violation below 'Nested for depth is 3 (max allowed is 2)\.'
                for (int l : values) {
                }
            }
            for (int sibling : values) { // ok, sibling depth is 2
            }
        }
    }
}

void followingMethod(int[] values) {
    for (int i = 0; i < 2; i++) { // ok, depth resets for the next method
        for (int j : values) {
            for (int k = 0; k < 2; k++) { // ok, mixed loop depth is 2
            }
        }
    }
}
