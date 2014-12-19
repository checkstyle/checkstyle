package com.puppycrawl.tools.checkstyle.indentation;

public class InputZeroCaseLevel {
    protected void begin(){
        int i=0;
        switch (i)
        {
        case 1: i++;
        default: i++;
        }
    }
}
