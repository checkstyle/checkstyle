/*
ImportOrder
option = bottom
groups = org, java
ordered = (default)true
separated = true
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = java, org
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import org.infinispan.Cache; // ok
import org.infinispan.commons.api.BasicCacheContainer; // ok

import java.util.Arrays; // ok
import java.util.Objects; // ok
import java.util.concurrent.TimeUnit; // ok

import com.google.common.collect.Lists; // ok
import com.google.common.primitives.Doubles; // ok
import javax.lang.model.type.ArrayType; // ok

import static java.lang.String.format; // ok
import static org.infinispan.test.TestingUtil.extractComponent; // ok
import static org.junit.Assert.fail; // ok
import static com.google.common.truth.Truth.assertThat; // ok
import static javax.lang.model.SourceVersion; // ok

public class InputImportOrderStaticGroupsBottom {
}
