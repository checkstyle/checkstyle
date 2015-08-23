package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.Configuration;

public class BaseFileSetCheckTestSupport extends BaseCheckTestSupport {
    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration config) {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(config);
        return dc;
    }
}
