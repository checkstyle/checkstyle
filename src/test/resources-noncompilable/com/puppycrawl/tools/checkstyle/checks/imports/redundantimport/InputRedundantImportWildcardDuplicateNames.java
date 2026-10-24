/*
RedundantImport


*/

// non-compiled with javac: contains specially crafted set of imports

package com.puppycrawl.tools.checkstyle.checks.imports.redundantimport;

import java.util.*;
import java.sql.Date;
import java.util.Date; // violation 'Import .* is covered by wildcard import .*'

class InputRedundantImportWildcardDuplicateNames {
}
