//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

/*
 * Config:
 * option = above
 * groups = {}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
import static com.google.common.truth.Truth.assertThat; // ok
import static java.lang.String.format; // ok
import static javax.lang.model.SourceVersion; // ok
import static org.infinispan.test.TestingUtil.extractComponent; // ok
import static org.junit.Assert.fail; // ok
import com.google.common.collect.Lists; // ok
import com.google.common.primitives.Doubles; // ok
import java.util.Arrays; // ok
import java.util.Objects; // ok
import java.util.concurrent.TimeUnit; // ok
import javax.lang.model.type.ArrayType; // ok
import org.infinispan.Cache; // ok
import org.infinispan.commons.api.BasicCacheContainer; // ok

public class InputImportOrderStaticGroupsAbove {
}
