package com.puppycrawl.tools.checkstyle.checks.imports;
// groups are configured as follows
// com.puppycrawl,*,java
// the trailing javax.crypto.Cipher; should be flagged as an error.

import com.puppycrawl.tools.checkstyle.checks.imports.InputImportOrder_Above;
import javax.crypto.BadPaddingException;
import java.util.List; //comment test
import javax.crypto.Cipher;

public class InputImportOrder_Wildcard {
}
