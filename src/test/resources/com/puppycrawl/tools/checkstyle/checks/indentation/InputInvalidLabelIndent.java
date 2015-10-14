package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

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
 * @author  jrichard                                                         //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputInvalidLabelIndent { //indent:0 exp:0

    /** Creates a new instance of InputInvalidLabelIndent */ //indent:4 exp:4
    public InputInvalidLabelIndent() { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8

        while (test) { //indent:8 exp:8
          label: //indent:10 exp:8,12 warn
            System.out.println("label test"); //indent:12 exp:12

            if (test) { //indent:12 exp:12
                unusedLabel: //indent:16 exp:16
                System.out.println("more testing"); //indent:16 exp:16
            } //indent:12 exp:12

        } //indent:8 exp:8
  label2: //indent:2 exp:4,8 warn
        System.out.println("toplevel"); //indent:8 exp:8
    label3: //indent:4 exp:4
                  System.out.println("toplevel"); //indent:18 exp:8,12 warn
                  System.out.println("toplevel"); //indent:18 exp:8 warn
    label4: //indent:4 exp:4
      System.out.println("toplevel"); //indent:6 exp:8,12 warn
    label5: //indent:4 exp:4
      System //indent:6 exp:8,12 warn
            .out. //indent:12 exp:>=10
                println("toplevel"); //indent:16 exp:>=16
    } //indent:4 exp:4

} //indent:0 exp:0
