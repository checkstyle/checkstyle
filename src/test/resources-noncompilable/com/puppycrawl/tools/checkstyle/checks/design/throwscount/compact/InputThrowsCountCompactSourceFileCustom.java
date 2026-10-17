/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ThrowsCount">
      <property name="max" value="1"/>
      <property name="ignorePrivateMethods" value="false"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

import java.io.IOException;
import java.sql.SQLException;

void main() { }

void atLimit() throws IOException { }

// violation below 'Throws count is 2 (max allowed is 1).'
void aboveLimit() throws IOException, SQLException { }

private void privateAtLimit() throws IOException { }

// violation below 'Throws count is 2 (max allowed is 1).'
private void checked() throws IOException, SQLException { }
