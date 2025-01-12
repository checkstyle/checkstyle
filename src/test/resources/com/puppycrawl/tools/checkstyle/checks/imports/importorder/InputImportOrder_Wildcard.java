/*
ImportOrder
option = (default)under
groups = com, *, java
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import javax.crypto.BadPaddingException;
import java.util.List; //comment test
import javax.crypto.Cipher; // violation 'Wrong order for 'javax.crypto.Cipher' import.'

public class InputImportOrder_Wildcard {
}
