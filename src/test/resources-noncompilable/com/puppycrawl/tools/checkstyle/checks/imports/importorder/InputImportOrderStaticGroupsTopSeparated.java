/*
ImportOrder
option = top
groups = android, androidx, java
ordered = (default)true
separated = (default)false
separatedStaticGroups = true
caseSensitive = (default)true
staticGroups = android, androidx, java
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
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
