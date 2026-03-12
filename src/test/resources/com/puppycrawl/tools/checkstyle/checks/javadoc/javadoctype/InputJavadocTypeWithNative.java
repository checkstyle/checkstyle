/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = \\S
allowMissingParamTags = true
allowUnknownTags = true
allowedAnnotations = (default)Generated
tokens = (default)BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

public class InputJavadocTypeWithNative {

    /**
     * Creates Plugin
     *
     * @param chartComponent chart component
     * @return plugin
     */
    private native String createPlugin(Long chartComponent) /*-{
        return pluginModel;
    }-*/;

    private interface PluginModel {
    }
}
