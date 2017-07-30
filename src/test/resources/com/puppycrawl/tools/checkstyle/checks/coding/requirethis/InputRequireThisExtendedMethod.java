package com.github.sevntu.checkstyle.checks.coding;

import java.util.logging.Logger;

public class InputRequireThisExtendedMethod
{
    public class Check {
        private Logger log1 = Logger.getLogger(getClass().getName());
    }
}
