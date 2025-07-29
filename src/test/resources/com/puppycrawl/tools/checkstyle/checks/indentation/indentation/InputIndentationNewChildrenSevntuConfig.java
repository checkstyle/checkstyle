/* Config:                                                                    //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 8                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 8                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;   //indent:0 exp:0

import java.io.File;                                                      //indent:0 exp:0
import java.util.HashSet;                                                 //indent:0 exp:0
import java.util.Set;                                                     //indent:0 exp:0
import org.junit.jupiter.api.Assertions;                                  //indent:0 exp:0

public class InputIndentationNewChildrenSevntuConfig {                    //indent:0 exp:0

    public void verifyTestConfigurationFiles() throws Exception {         //indent:4 exp:4
        final Set<String> packages = new HashSet<>();                     //indent:8 exp:8
        Assertions.assertFalse(packages.isEmpty(), "no modules"); //indent:8 exp:8
        final File extensionFile = new File("dummy.xml");        //indent:8 exp:8
        final String input = "xyz";                                        //indent:8 exp:8

        for (String pkgName : packages) {                                   //indent:8 exp:8
            Assertions.assertTrue(new File(                                 //indent:12 exp:12
                    getEclipseCsPath(pkgName)).exists(), "e" + pkgName //indent:20 exp:20
                    + " must exist in eclipsecs");                          //indent:20 exp:20

            validateEclipseCsMetaXmlFile(                                   //indent:12 exp:12
                    new File(getEclipseCsPath(pkgName            //indent:20 exp:20
                            + "check.xml")), pkgName, new HashSet<>(        //indent:28 exp:28
                                    packages));                             //indent:36 exp:36

            validateEclipseCsMetaXmlFile(                                   //indent:12 exp:12
                    new File(getEclipseCsPath(pkgName            //indent:20 exp:20
                            + "check.xml")), pkgName, new HashSet<>(        //indent:28 exp:28
                            packages));                                    //indent:28 exp:36 warn

            validateMetaPropFile(new File(getEclipseCsPath(pkgName //indent:12 exp:12
                    + "check.xml")), pkgName, new HashSet<>(packages));       //indent:20 exp:20
        }                                                                     //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    public static String getEclipseCsPath(String relativePath) {              //indent:4 exp:4
        return "src/main/resources/" + relativePath;                          //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    public static void validateEclipseCsMetaXmlFile(File metadata,            //indent:4 exp:4
                                                    String packageName,       //indent:52 exp:52
                                                    Set<String> modules) {    //indent:52 exp:52
        System.out.println("W" + modules);                                    //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    public static void validateMetaPropFile(File propertiesFile,              //indent:4 exp:4
                                            String packageName,               //indent:44 exp:44
                                            Set<String> modules) {            //indent:44 exp:44
        System.out.println("W" + modules);                                    //indent:8 exp:8
    }                                                                         //indent:4 exp:4
}                                                                             //indent:0 exp:0
