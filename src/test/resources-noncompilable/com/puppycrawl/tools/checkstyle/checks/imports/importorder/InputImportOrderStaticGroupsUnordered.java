/*
ImportOrder
option = top
groups = org, com, java
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
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
