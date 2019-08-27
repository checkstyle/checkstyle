package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
// groups are configured as follows
// com.puppycrawl,*,java
// the trailing javax.crypto.Cipher; should be flagged as a violation.

import com.puppycrawl.tools.checkstyle.checks.imports.importorder.InputImportOrder_Above;
import javax.crypto.BadPaddingException;
import java.util.List; //comment test
import javax.crypto.Cipher;

public class InputImportOrder_Wildcard {
}
