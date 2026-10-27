/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CyclomaticComplexity"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() { // violation 'Cyclomatic Complexity is 12 (max allowed is 10).'
    int value = 0;
    switch (value) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
            lowComplexity();
            break;
        default:
            break;
    }
}

void lowComplexity() { }
