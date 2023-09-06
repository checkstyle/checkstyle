/*
ImportControl
file = (file)InputImportControlBlacklist.xml
path = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;

import java.util.stream.Stream; // violation 'Disallowed import - java.util.stream.Stream'
import java.util.Date; // violation 'Disallowed import - java.util.Date'
import java.util.List;
import java.util.stream.Collectors; // violation 'Disallowed import - java.util.stream.Collectors'
import java.util.stream.IntStream; // violation 'Disallowed import - java.util.stream.IntStream'

public class InputImportControl_Blacklist
{

}
