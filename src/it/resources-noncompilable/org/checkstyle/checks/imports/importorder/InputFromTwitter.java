//non-compiled with javac: contains specially crafted set of imports for testing
package org.checkstyle.checks.importorder;
/**
 * This test-input is intended to be checked using following configuration:
 * Config
 * caseSensitive = true
 * groups = {android,com.twitter,com,junit,net,org,java,javax}
 * option = bottom
 * ordered = true
 * separated = true
 * separatedStaticGroups = true
 * staticGroups = {android,com.twitter,com,junit,net,org,java,javax}
 *
 * This test-input is based on real application code and shouldn't be changed.
 */
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.twitter.android.utils.EspressoHelpers.goBack;
import static com.twitter.android.utils.EspressoHelpers.swipeAwayAndWait;
import static com.twitter.android.utils.EspressoHelpers.swipeDown;
import static com.twitter.android.utils.EspressoHelpers.tapOnHomeTab;
import static com.twitter.android.utils.EspressoHelpers.tapOnTweetWithText;

import static org.assertj.core.api.Assertions.assertThat;

class InputFromTwitter {
}
