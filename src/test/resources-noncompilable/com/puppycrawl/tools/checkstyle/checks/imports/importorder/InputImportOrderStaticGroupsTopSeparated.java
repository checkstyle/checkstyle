//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
/**
 * This test-input is intended to be checked using following configuration:
 *
 * groups = {android, androidx, java}
 * staticGroups = {android, androidx, java}
 * option = top
 * ordered = true
 * sortStaticImportsAlphabetically = true
 * separated = false
 * separatedStaticGroups = true
 * useContainerOrderingForStatic = false
 *
 */
import static android.app.slice.Slice.HINT_ACTIONS;
import static android.app.slice.Slice.HINT_ERROR;
import static android.app.slice.Slice.HINT_SUMMARY;
import static android.app.slice.Slice.HINT_TITLE;
import static android.app.slice.SliceItem.FORMAT_TEXT;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;
import static androidx.slice.builders.ListBuilder.ICON_IMAGE;
import static androidx.slice.builders.ListBuilder.INFINITY;
import static androidx.slice.builders.ListBuilder.LARGE_IMAGE;
import static androidx.slice.core.SliceHints.SUBTYPE_MIN;

import android.app.PendingIntent;
import android.net.Uri;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Clock;
import androidx.slice.Slice;
import androidx.slice.builders.SliceAction;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InputImportOrderStaticGroupsTopSeparated {
}
