/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantModifierCompactSource"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25
// Java 25 compact source file.

void helper() {}

// xdoc section -- start
// violation below 'Redundant 'final' modifier on a direct member.'
final void finalized() {}

// violation below 'Redundant 'strictfp' modifier on a direct member.'
strictfp double calculate() { return 1.0; }

void main() {}
// xdoc section -- end
