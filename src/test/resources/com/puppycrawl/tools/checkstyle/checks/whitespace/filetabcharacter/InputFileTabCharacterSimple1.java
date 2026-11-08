/*
FileTabCharacter
eachLine = true
fileExtensions = (default)(null)


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.filetabcharacter;

final class InputFileTabCharacterSimple1
{
    // Contains a tab ->	<- // violation 'Line contains a tab character'

    // long line that has a tab ->	<- and would be // violation 'Line contains a tab character'
    // ok if tab counted as 1 char
    // tabs that count as one char because
    // of their position ->	<-   ->	<-, OK // violation 'Line contains a tab character'

    /** some lines to test the violation column after tabs */
    void errorColumnAfterTabs()
    {
        // with tab-width 8 all statements below start at the same column,
        // with different combinations of ' ' and '\t' before the statement
                int tab0 =1;
        	int tab1 =1; // violation 'Line contains a tab character'
         	int tab2 =1; // violation 'Line contains a tab character'
		int tab3 =1; // violation 'Line contains a tab character'
  	  	int tab4 =1; // violation 'Line contains a tab character'
  	        int tab5 =1; // violation 'Line contains a tab character'
    }
}

