/*
AvoidStarImport
excludes = (default)
allowClassImports = (default)false
allowStaticMemberImports = (default)false
maxAllowed = 1


*/

package com.puppycrawl.tools.checkstyle.checks.imports.avoidstarimport;

import java.io.*;
import java.lang.*; // violation, 'Using the '.*' form of import should be avoided.'
import static java.io.File.*; // violation, 'Using the '.*' form of import should be avoided.'

public class InputAvoidStarImportMaxAllowed {

}
