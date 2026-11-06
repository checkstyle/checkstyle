/*
ImportOrder
option = (default)under
groups = /^java\./, javax, org
ordered = (default)true
separated = true
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)IMPORT,STATIC_IMPORT


*/

package org.checkstyle.suppressionxpathfilter.imports.importorder;

import java.io.*;
import org.junit.*;
// violation above ''org.junit.*' should be separated from previous imports.'

public class InputXpathImportOrderThree {
    // code
}
