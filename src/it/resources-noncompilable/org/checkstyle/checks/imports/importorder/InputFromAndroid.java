// non-compiled with javac: contains specially crafted set of imports for testing
package org.checkstyle.checks.importorder;
/**
 * This test-input is intended to be checked using following configuration:
 * Config
 * groups = {android,androidx,com.android,dalvik,com,gov,junit,libcore,net,org,java,javax}
 * option = top
 * ordered = true
 * separated = true
 * separatedStaticGroups = true
 * staticGroups = {android,androidx,com.android,dalvik,com,gov,junit,libcore,net,org,java,javax}
 *
 * This test-input is based on real application code and shouldn't be changed.
 * @see https://android.googlesource.com/platform/prebuilts/checkstyle/+/master/default-treewalker-checks.xml
 */
import static android.graphics.drawable.Icon.TYPE_ADAPTIVE_BITMAP;
import static android.graphics.drawable.Icon.TYPE_BITMAP;
import static android.graphics.drawable.Icon.TYPE_DATA;
import static android.graphics.drawable.Icon.TYPE_RESOURCE;
import static android.graphics.drawable.Icon.TYPE_URI;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;
import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Preconditions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;

class InputFromAndroid {
}
