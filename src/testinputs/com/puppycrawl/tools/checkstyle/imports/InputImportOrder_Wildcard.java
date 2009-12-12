
// groups are configured as follows
// com.puppycrawl,*,java
// the trailing javax.crypto.Cipher; should be flagged as an error.

import com.puppycrawl.tools.checkstyle.imports.InputImportOrder_Above;
import javax.crypto.BadPaddingException;
import java.util.List;
import javax.crypto.Cipher;

public class InputImportOrder_Wildcard {
}
