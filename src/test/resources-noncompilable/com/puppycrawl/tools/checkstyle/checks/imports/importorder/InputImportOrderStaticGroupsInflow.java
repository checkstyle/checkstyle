//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
/**
 * This test-input is intended to be checked using following configuration:
 *
 * groups = {}
 * staticGroups = {}
 * option = inflow
 *
 */
import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import static com.google.common.truth.Truth.assertThat;
import static java.lang.String.format;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import static javax.lang.model.SourceVersion;
import javax.lang.model.type.ArrayType;
import org.infinispan.Cache;
import org.infinispan.commons.api.BasicCacheContainer;
import static org.infinispan.test.TestingUtil.extractComponent;
import static org.junit.Assert.fail;

public class InputImportOrderStaticGroupsInflow {
}
