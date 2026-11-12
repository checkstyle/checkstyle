/*
ImportOrder
option = top
groups = (default)
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = true
tokens = (default)IMPORT,STATIC_IMPORT


*/

// non-compiled with javac: reference to non existent packages

import static com.example.HttpHeaders.Names.DATE;
import static com.example.HttpHeaders.addHeader; // violation 'Wrong lexicographical order for 'com.example.HttpHeaders.addHeader' import. Should be before 'com.example.HttpHeaders.Names.DATE'.'
import java.util.List;

void main() {
    List<String> headers = List.of();
    addHeader(headers, DATE);
}
