/*
CustomImportOrder
customImportOrderRules = STATIC###THIRD_PARTY_PACKAGE
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = true


*/

//non-compiled with javac: contains no package for testing
import java.io.*;


import java.util.*; // violation 'Extra separation in import group before .*'

import java.util.HashMap; // violation 'Extra separation in import group before .*'
import java.util.LinkedList;
// comments between imports

import javax.net.ServerSocketFactory; // violation 'Extra separation in import group before .*'

// comments between imports
import javax.net.SocketFactory; // violation 'Extra separation in import group before .*'

class InputCustomImportOrderNoPackage {

}
