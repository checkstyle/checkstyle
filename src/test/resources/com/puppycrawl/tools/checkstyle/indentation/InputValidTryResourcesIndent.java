package com.puppycrawl.tools.checkstyle.indentation; //indent:0 exp:0

import java.io.FileInputStream; //indent:0 exp:0
import java.io.IOException; //indent:0 exp:0
import java.util.jar.JarInputStream; //indent:0 exp:0
import java.util.jar.Manifest; //indent:0 exp:0


/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
class InputValidTryResourcesIndent //indent:0 exp:0
{ //indent:0 exp:0
    // Taken from JDK7 java.lang.Package src code. //indent:4 exp:4
    private static Manifest loadManifest(String fn) { //indent:4 exp:4
        try (FileInputStream fis = new FileInputStream(fn); //indent:8 exp:8
    // This should be an error //indent:4 exp:4
    JarInputStream jis = new JarInputStream(fis, false)) //indent:4 exp:4
        { //indent:8 exp:8
            return jis.getManifest(); //indent:12 exp:12
        } catch (IOException e) //indent:8 exp:8
        { //indent:8 exp:8
            return null; //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
