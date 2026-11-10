/*
CustomImportOrder
customImportOrderRules = STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE###SPECIAL_IMPORTS
standardPackageRegExp = ^java\\.
thirdPartyPackageRegExp = ^(org|com)\\.
specialImportsRegExp = ^javax\\.
separateLineBetweenGroups = false
sortImportsInGroupAlphabetically = true


*/

// non-compiled with javac: Compilable with Java25

import static java.lang.Math.max;
import java.util.List;
import java.io.File; // violation 'Wrong lexicographical order for 'java.io.File' import. .*'
import org.w3c.dom.Node;
import com.sun.source.tree.Tree; // violation 'Wrong lexicographical order .*'
import javax.swing.JPanel;
import java.util.Map; // violation 'Import statement for 'java.util.Map' is in the wrong order.*'

void main() {
}

int bigger(List<Integer> values, File file, Node node, JPanel panel) {
    Tree tree = null;
    Map<String, String> map = Map.of();
    return max(values.size(), map.size());
}
