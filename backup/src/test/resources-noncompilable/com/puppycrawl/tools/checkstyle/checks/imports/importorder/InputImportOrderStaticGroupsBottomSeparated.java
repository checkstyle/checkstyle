/*
ImportOrder
option = bottom
groups = android, com, net, junit, org, java, javax
ordered = (default)true
separated = true
separatedStaticGroups = true
caseSensitive = (default)true
staticGroups = android, com, net, junit, org, java, javax
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import com.spotify.x.R; // ok

import javax.inject.Inject; // ok

import static android.support.test.espresso.action.ViewActions.click; // ok
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed; // ok
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription; // ok
import static android.support.test.espresso.matcher.ViewMatchers.withText; // ok

import static com.spotify.X.o; // ok

import static org.hamcrest.core.AllOf.allOf; // ok

public class InputImportOrderStaticGroupsBottomSeparated {
}
