//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
/**
 * This test-input is intended to be checked using following configuration:
 *
 * groups = {android,com,net,junit,org,java,javax}
 * staticGroups = {android,com,net,junit,org,java,javax}
 * option = bottom
 * ordered = true
 * sortStaticImportsAlphabetically = true
 * separated = true
 * separatedStaticGroups = true
 * useContainerOrderingForStatic = false
 *
 */
import com.spotify.x.R;

import javax.inject.Inject;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static com.spotify.X.o;

import static org.hamcrest.core.AllOf.allOf;

public class InputImportOrderStaticGroupsBottomSeparated {
}
