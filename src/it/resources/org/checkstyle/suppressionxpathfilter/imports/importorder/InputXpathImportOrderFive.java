/*
ImportOrder
option = (default)under
groups = /^java\./, javax, org
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)IMPORT,STATIC_IMPORT


*/

package org.checkstyle.suppressionxpathfilter.imports.importorder;

import java.util.List;
import static org.junit.After.*;
import java.util.Date;
// violation above 'Import statement for 'java.util.Date' violates the configured'

public class InputXpathImportOrderFive {
    // code
}
