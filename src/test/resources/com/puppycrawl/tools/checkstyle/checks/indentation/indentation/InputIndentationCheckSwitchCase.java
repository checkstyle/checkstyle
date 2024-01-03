package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationCheckSwitchCase { //indent:0 exp:0
    void method(int num) { //indent:4 exp:4
        if (true) { //indent:8 exp:8
            switch (num) { //indent:12 exp:12
                case 3: //indent:16 exp:16
                    System.out.println("3"); //indent:20 exp:20
            } //indent:12 exp:12
        } //indent:8 exp:8
    }//indent:4 exp:4

}                                         //indent:0 exp:0
