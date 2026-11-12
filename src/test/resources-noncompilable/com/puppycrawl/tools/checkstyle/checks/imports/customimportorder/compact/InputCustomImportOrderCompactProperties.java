/*
CustomImportOrder
customImportOrderRules = STATIC, STANDARD_JAVA_PACKAGE, SPECIAL_IMPORTS, THIRD_PARTY_PACKAGE
standardPackageRegExp = ^java\\.
thirdPartyPackageRegExp = ^org\\.
specialImportsRegExp = ^javax\\.
separateLineBetweenGroups = false
sortImportsInGroupAlphabetically = true


*/

// non-compiled with javac: Compilable with Java25

import static java.lang.Math.PI;
// violation below 'Wrong lexicographical order for .* Should be before .*'
import static java.lang.Math.E;
import java.util.List;
// violation below 'Wrong lexicographical order for .* Should be before .*'
import java.util.ArrayList;
import javax.swing.JComponent;
import org.w3c.dom.Node;

void main() {
}
