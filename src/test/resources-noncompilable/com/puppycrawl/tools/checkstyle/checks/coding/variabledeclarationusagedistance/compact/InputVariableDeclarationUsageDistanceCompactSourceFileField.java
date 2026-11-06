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

int processed;

void main() {
    System.out.println("Processing items");
    String[] items = {"orange", "apple"};
    java.util.Arrays.sort(items);
    System.out.println(java.util.Arrays.toString(items));
    processed = items.length;
    System.out.println(processed);
}
