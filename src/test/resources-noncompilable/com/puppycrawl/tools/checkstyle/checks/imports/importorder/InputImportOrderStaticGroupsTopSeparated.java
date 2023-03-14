/*
ImportOrder
option = top
groups = android, androidx, java
ordered = true
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
import static android.app.slice.Slice.HINT_ACTIONS; // ok
import static android.app.slice.Slice.HINT_ERROR; // ok
import static android.app.slice.Slice.HINT_SUMMARY; // ok
import static android.app.slice.Slice.HINT_TITLE; // ok
import static android.app.slice.SliceItem.FORMAT_TEXT; // ok

import static androidx.annotation.RestrictTo.Scope.LIBRARY; // ok
import static androidx.slice.builders.ListBuilder.ICON_IMAGE; // ok
import static androidx.slice.builders.ListBuilder.INFINITY; // ok
import static androidx.slice.builders.ListBuilder.LARGE_IMAGE; // ok
import static androidx.slice.core.SliceHints.SUBTYPE_MIN; // ok

import android.app.PendingIntent; // ok
import android.net.Uri; // ok
import androidx.annotation.ColorInt; // ok
import androidx.annotation.NonNull; // ok
import androidx.core.graphics.drawable.IconCompat; // ok
import androidx.slice.Clock; // ok
import androidx.slice.Slice; // ok
import androidx.slice.builders.SliceAction; // ok
import java.time.Duration; // ok
import java.util.ArrayList; // ok
import java.util.List; // ok

public class InputImportOrderStaticGroupsTopSeparated {
}
