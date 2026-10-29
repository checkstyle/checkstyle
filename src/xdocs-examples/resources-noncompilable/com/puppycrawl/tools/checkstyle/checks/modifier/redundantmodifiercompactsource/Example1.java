/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantModifierCompactSource"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

// xdoc section - start
public static void process() {}
// 2 violations above:
// 'Redundant 'public' modifier on a direct member.'
// 'Redundant 'static' modifier on a direct member.'

protected final strictfp void calculate() {}
// 3 violations above:
// 'Redundant 'protected' modifier on a direct member.'
// 'Redundant 'final' modifier on a direct member.'
// 'Redundant 'strictfp' modifier on a direct member.'

// violation below 'Redundant 'private' modifier on a direct member.'
private void helper() {}

public static int first;
// 2 violations above:
// 'Redundant 'public' modifier on a direct member.'
// 'Redundant 'static' modifier on a direct member.'

// violation below 'Redundant 'protected' modifier on a direct member.'
protected int second;

// violation below 'Redundant 'private' modifier on a direct member.'
private int third;

void main() {}
// xdoc section - end
