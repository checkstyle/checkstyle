package com.puppycrawl.tools.checkstyle.checks.imports;

// groups are configured as follows
// com.puppycrawl,*,java
// the trailing javax.crypto.Cipher; should be flagged as an error.

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import com.puppycrawl.tools.checkstyle.checks.imports.InputImportBug;

public class InputImportOrder_WildcardUnspecified {
}
