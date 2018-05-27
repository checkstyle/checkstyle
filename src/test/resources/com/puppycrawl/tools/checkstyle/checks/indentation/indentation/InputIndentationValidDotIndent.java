package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import javax.swing.border.BevelBorder; //indent:0 exp:0
import javax.swing.plaf.metal.MetalButtonUI; //indent:0 exp:0

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
public class InputIndentationValidDotIndent { //indent:0 exp:0

    /** Creates a new instance of InputIndentationValidDotIndent */ //indent:4 exp:4
    public InputIndentationValidDotIndent() { //indent:4 exp:4

        System.lineSeparator(); //indent:8 exp:8

        String. //indent:8 exp:8
            CASE_INSENSITIVE_ORDER.reversed(); //indent:12 exp:12

        String.CASE_INSENSITIVE_ORDER. //indent:8 exp:8
            reversed(); //indent:12 exp:12

        String. //indent:8 exp:8
            CASE_INSENSITIVE_ORDER. //indent:12 exp:12
                reversed(); //indent:16 exp:16

        String //indent:8 exp:8
            .CASE_INSENSITIVE_ORDER //indent:12 exp:12
                .reversed(); //indent:16 exp:16

        BevelBorder border = new BevelBorder(BevelBorder.LOWERED); //indent:8 exp:8
        border = new javax.swing.border. //indent:8 exp:8
            BevelBorder(BevelBorder.LOWERED); //indent:12 exp:12


        border = new javax.swing.border.BevelBorder( //indent:8 exp:8
            BevelBorder.LOWERED); //indent:12 exp:12
        border = new javax. //indent:8 exp:8
            swing. //indent:12 exp:12
                border. //indent:16 exp:16
                    BevelBorder(BevelBorder.LOWERED); //indent:20 exp:20
        border = //indent:8 exp:8
            new javax. //indent:12 exp:12
                swing //indent:16 exp:16
                    .border //indent:20 exp:20
                        .BevelBorder(BevelBorder.LOWERED); //indent:24 exp:24
        border = //indent:8 exp:8
            new javax. //indent:12 exp:12
                swing //indent:16 exp:16
                    .border //indent:20 exp:20
                        .BevelBorder(BevelBorder. //indent:24 exp:24
                            LOWERED); //indent:28 exp:28

        Class<MetalButtonUI> c = javax.swing. //indent:8 exp:8
            plaf.metal.MetalButtonUI.class; //indent:12 exp:12

        Class<MetalButtonUI> c1 = javax.swing //indent:8 exp:8
            .plaf.metal.MetalButtonUI.class; //indent:12 exp:12

        Class<MetalButtonUI> c2 = javax.swing //indent:8 exp:8
            .plaf.metal. //indent:12 exp:12
                MetalButtonUI.class; //indent:16 exp:16

        Class<MetalButtonUI> c3 = javax.swing //indent:8 exp:8
            .plaf.metal //indent:12 exp:12
                .MetalButtonUI.class; //indent:16 exp:16

        Class<MetalButtonUI> c4 = javax. //indent:8 exp:8
            swing.plaf.metal. //indent:12 exp:12
                MetalButtonUI.class; //indent:16 exp:16



        border = //indent:8 exp:8
            new javax. //indent:12 exp:12
                swing //indent:16 exp:16
                    .border.BevelBorder(BevelBorder. //indent:20 exp:20
                        LOWERED); //indent:24 exp:24

    } //indent:4 exp:4

} //indent:0 exp:0
