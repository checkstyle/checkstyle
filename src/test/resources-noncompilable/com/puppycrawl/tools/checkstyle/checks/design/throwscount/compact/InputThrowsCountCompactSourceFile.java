/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ThrowsCount"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;

void main() { }

void belowLimit() throws IOException { }

void atLimit() throws IOException, SQLException, ParseException, TimeoutException { }

// violation below 'Throws count is 5 (max allowed is 4).'
void aboveLimit() throws IOException, SQLException, ParseException,
        TimeoutException, GeneralSecurityException { }

private void ignored() throws IOException, SQLException, ParseException,
        TimeoutException, GeneralSecurityException { }
