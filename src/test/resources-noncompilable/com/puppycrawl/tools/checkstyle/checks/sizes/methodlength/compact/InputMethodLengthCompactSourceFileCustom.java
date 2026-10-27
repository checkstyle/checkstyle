/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodLength">
      <property name="max" value="4"/>
      <property name="countEmpty" value="false"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() { // violation 'Method main length is 5 lines (max allowed is 4).'
    atLimit();
    overLimit();
    commentOnly();
}

void atLimit() { // ok, four code lines despite the comments and blank line
    // A comment-only line is not counted.

    int value = 1;
    /* These comment-only lines
       are not counted either. */
    System.out.println(value);
}

void overLimit() { // violation 'Method overLimit length is 5 lines (max allowed is 4).'
    int value = 1;
    value++;
    System.out.println(value);
}

void commentOnly() { // ok, only the two brace lines count
    // A comment-only line.

    /* More
       comments. */
}
