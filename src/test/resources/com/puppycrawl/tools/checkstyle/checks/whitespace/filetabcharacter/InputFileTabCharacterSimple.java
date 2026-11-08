/*
FileTabCharacter
eachLine = (default)false
fileExtensions = (default)(null)


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.filetabcharacter;

final class InputFileTabCharacterSimple
{
    // Contains a tab ->	<- // violation 'File contains tab characters (this is the first instance)'

    // long line that has a tab ->	<- and would be OK if tab counted as 1 char
    // tabs that count as one char because of their position ->	<-   ->	<-, OK

    /** some lines to test the violation column after tabs */
    void errorColumnAfterTabs()
    {
        // with tab-width 8 all statements below start at the same column,
        // with different combinations of ' ' and '\t' before the statement
                int tab0 =1;
        	int tab1 =1;
         	int tab2 =1;
		int tab3 =1;
  	  	int tab4 =1;
  	        int tab5 =1;
    }
}

