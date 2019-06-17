//non-compiled with javac: contains no package for testing
import java.io.*;


import java.util.*; //warn

import java.util.HashMap; //warn
import java.util.LinkedList;
// comments between imports

import javax.net.ServerSocketFactory; //warn

// comments between imports
import javax.net.SocketFactory; //warn

class InputCustomImportOrderNoPackage {

}
/*
 * test: testNoPackage()
 *
 * Config = default
 * customImportOrderRules = STATIC###THIRD_PARTY_PACKAGE
 * standardPackageRegExp = ^(java|javax)\.
 * thirdPartyPackageRegExp = .*
 * specialImportsRegExp = ^$
 * sortImportsInGroupAlphabetically = true
 * separateLineBetweenGroups = true
 *
 */
