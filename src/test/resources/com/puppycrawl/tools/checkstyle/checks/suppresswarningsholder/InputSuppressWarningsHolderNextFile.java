/*
com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = (default)(null)

com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck
ignorePattern = (default)^(package|import) .*
max = (default)80
tabWidth = (default)0
fileExtensions = (default)null


*/

package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

@SuppressWarnings("all")
public class InputSuppressWarningsHolderNextFile {

    private final String text = "This line is longer than the default limit of 80 characters.";

    private final String first = "first";

    private final String second = "second";

    private final String third = "third";

    private final String fourth = "fourth";
}
