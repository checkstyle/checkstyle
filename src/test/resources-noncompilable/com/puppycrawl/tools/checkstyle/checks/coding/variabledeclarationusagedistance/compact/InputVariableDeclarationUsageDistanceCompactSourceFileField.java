/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="VariableDeclarationUsageDistance">
      <property name="validateBetweenScopes" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

// until #22458: fields in compact sources should be ignored
int processed; // violation 'Distance between variable .* is 4, but allowed 3.'

void main() {
    System.out.println("Processing items");
    String[] items = {"orange", "apple"};
    java.util.Arrays.sort(items);
    System.out.println(java.util.Arrays.toString(items));
    processed = items.length;
    System.out.println(processed);
}
