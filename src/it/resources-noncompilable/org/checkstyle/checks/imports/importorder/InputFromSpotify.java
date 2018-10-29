//non-compiled with javac: contains specially crafted set of imports for testing
package org.checkstyle.checks.importorder;
/**
 * This test-input is intended to be checked using following configuration:
 *
 * groups = {android,com,net,junit,org,java,javax}
 * option = bottom
 * ordered = true
 * separated = true
 * separatedStaticGroups = true
 * staticGroups = {android,com,net,junit,org,java,javax}
 *
 * This test-input is based on real application code and shouldn't be changed.
 */
import com.spotify.x.R;

import javax.inject.Inject;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static com.spotify.X.o;

import static org.hamcrest.core.AllOf.allOf;

class InputFromSpotify {
}
