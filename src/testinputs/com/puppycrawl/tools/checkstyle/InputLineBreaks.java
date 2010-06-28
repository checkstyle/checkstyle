////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2009
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Test case for dealing with various line termination schemes.
 * @author Martin von Gagern
 **/
class InputLineBreaks
{
    int firstLine;
    int cr;
    int oneQuearter;
    int crlf;
    int middleLine;
    int lfcr;

    int threeQuarters;
    int cr2;
    int lastLine;
}
/* When splitting on \r\n, \r and \n, the lines should be numbered thus:
     13     int firstLine;\n
     14     int cr;\r
     15     int oneQuearter;\n
     16     int crlf;\r\n
     17     int middleLine;\n
     18     int lfcr;\n
     19 \r
     20     int threeQuarters;\n
     21     int cr2;\r
     22     int lastLine;\n
*/
