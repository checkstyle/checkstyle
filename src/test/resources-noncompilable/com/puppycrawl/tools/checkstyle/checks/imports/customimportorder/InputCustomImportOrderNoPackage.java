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


import java.util.*; // violation

import java.util.HashMap; // violation
import java.util.LinkedList;
// comments between imports

import javax.net.ServerSocketFactory; // violation

// comments between imports
import javax.net.SocketFactory; // violation

class InputCustomImportOrderNoPackage {

}
