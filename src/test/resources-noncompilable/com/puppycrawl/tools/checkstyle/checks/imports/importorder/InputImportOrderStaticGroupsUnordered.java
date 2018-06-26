//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
/**
 * This test-input is intended to be checked using following configuration:
 *
 * groups = {org, com, java}
 * staticGroups = {}
 * option = top
 * ordered = true
 * separated = false
 * sortStaticImportsAlphabetically = false
 *
 */
import static org.infinispan.test.TestingUtil.extractComponent;
import static java.lang.String.format;
import static org.junit.Assert.fail;
import static com.google.common.truth.Truth.assertThat;
import static javax.lang.model.SourceVersion;
import org.infinispan.Cache;
import org.infinispan.commons.api.BasicCacheContainer;
import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.lang.model.type.ArrayType;

public class InputImportOrderStaticGroupsUnordered {
}
