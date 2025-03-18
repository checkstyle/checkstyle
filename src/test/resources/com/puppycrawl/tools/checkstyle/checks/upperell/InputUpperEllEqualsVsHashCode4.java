package com.puppycrawl.tools.checkstyle.checks.upperell;

import java.io.ByteArrayOutputStream;

public class InputUpperEllEqualsVsHashCode4
{
    // in anon inner class
    ByteArrayOutputStream bos1 = new ByteArrayOutputStream()
    {
        public boolean equals(Object a) // don't flag
        {
            return true;
        }

        public int hashCode()
        {
            return 0;
        }
    };

    ByteArrayOutputStream bos2 = new ByteArrayOutputStream()
    {
        public boolean equals(Object a) // flag
        {
            return true;
        }
    };
}
