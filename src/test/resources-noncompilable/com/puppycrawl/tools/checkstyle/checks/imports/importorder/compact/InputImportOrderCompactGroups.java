/*
ImportOrder
option = bottom
groups = java, javax
ordered = (default)true
separated = true
separatedStaticGroups = true
caseSensitive = (default)true
staticGroups = java, javax
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = (default)false
tokens = (default)IMPORT,STATIC_IMPORT


*/

// non-compiled with javac: reference to non existent packages

import java.util.List;
import javax.swing.JButton; // violation ''javax.swing.JButton' should be separated from previous imports.'

import org.junit.Test;

import static java.util.Collections.sort;
import static java.util.Collections.max; // violation 'Wrong lexicographical order for 'java.util.Collections.max' import. Should be before 'java.util.Collections.sort'.'

import static javax.swing.SwingUtilities.invokeLater;
import static java.util.Collections.emptyList; // violation 'Import statement for 'java.util.Collections.emptyList' violates the configured import group order.'

import static java.util.Collections.min; // violation 'Extra separation in import group before 'java.util.Collections.min''

String title = "compact";

void main() {
    List<String> list = emptyList();
    sort(list);
    invokeLater(() -> { });
}
