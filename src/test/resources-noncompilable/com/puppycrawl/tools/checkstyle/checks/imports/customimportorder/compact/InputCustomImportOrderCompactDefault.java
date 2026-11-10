/*
CustomImportOrder
customImportOrderRules = (default)
standardPackageRegExp = (default)^(java|javax)\\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

// non-compiled with javac: Compilable with Java25

import static java.lang.Math.max;

import java.util.List; // violation 'Extra separation in import group before 'java.util.List''
import org.w3c.dom.Node;

import java.io.File; // violation 'Extra separation in import group before 'java.io.File''
import javax.swing.JPanel;

void main() {
}

int bigger(List<Integer> values, File file, Node node, JPanel panel) {
    return max(values.size(), 0);
}
