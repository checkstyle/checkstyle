//non-compiled: bad import for testing
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import com.PackageClass.*;

/*
 * Config:
 * illegalClassNames = { com.PackageClass }
 */
public class InputIllegalTypePackageClassName {
    PackageClass o = new PackageClass(); // ok
}
