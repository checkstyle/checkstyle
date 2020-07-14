package com.puppycrawl.tools.checkstyle.api.metadata;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import org.junit.Test;

public class JavadocMetadataScraperTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/api/metadata";
    }

    @Test
    public void testX() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMetadataScraper.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("Input1.java"), expected);
    }
}
