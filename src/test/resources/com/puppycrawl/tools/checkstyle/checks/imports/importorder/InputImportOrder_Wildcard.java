package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
// groups are configured as follows
// com.puppycrawl,*,java
// the trailing javax.crypto.Cipher; should be flagged as a violation.

/*
 * Config:
 * option = under
 * groups = {com, *, java}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
import com.puppycrawl.tools.checkstyle.checks.imports.importorder.InputImportOrder_Above; // ok
import javax.crypto.BadPaddingException; // ok
import java.util.List; //comment test // ok
import javax.crypto.Cipher; // violation

public class InputImportOrder_Wildcard {
}
