package com.google.checkstyle.test.chapter4formatting.rule487modifiers;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.modifier.ModifierOrderCheck;

public class ModifierOrderTest extends BaseCheckTestSupport{
    
    private static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void modifierOrderTest() throws Exception {
        
        Class<ModifierOrderCheck> clazz = ModifierOrderCheck.class;
        String msgMod = "mod.order";
        String msgAnnotation = "annotation.order";

        final String[] expected = {
            "3:10: " + getCheckMessage(clazz, msgMod, "abstract"),
            "5:19: " + getCheckMessage(clazz, msgMod, "private"),
            "7:18: " + getCheckMessage(clazz, msgMod, "public"),
            "15:14: " + getCheckMessage(clazz, msgMod, "private"),
            "25:13: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "30:13: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "40:34: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation4"),
            "46:14: " + getCheckMessage(clazz, msgMod, "protected"),
            "48:18: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "50:40: " + getCheckMessage(clazz, msgMod, "final"),
            "52:29: " + getCheckMessage(clazz, msgMod, "static"),
            "54:18: " + getCheckMessage(clazz, msgMod, "final"),
            "56:49: " + getCheckMessage(clazz, msgMod, "private"),
            "58:20: " + getCheckMessage(clazz, msgMod, "synchronized"),
            "60:29: " + getCheckMessage(clazz, msgMod, "protected"),
            "62:14: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "80:11: " + getCheckMessage(clazz, msgMod, "private"),
            "96:15: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "98:12: " + getCheckMessage(clazz, msgMod, "protected"),
            "102:12: " + getCheckMessage(clazz, msgMod, "synchronized"),
            "104:12: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "106:19: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "108:18: " + getCheckMessage(clazz, msgMod, "static"),
            "110:25: " + getCheckMessage(clazz, msgMod, "private"),
            "137:27: " + getCheckMessage(clazz, msgMod, "private"),
            "139:26: " + getCheckMessage(clazz, msgMod, "public"),
            "143:23: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "145:26: " + getCheckMessage(clazz, msgMod, "protected"),
            "147:20: " + getCheckMessage(clazz, msgMod, "synchronized"),
            "149:20: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "151:20: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "153:33: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "155:26: " + getCheckMessage(clazz, msgMod, "private"),
            "168:35: " + getCheckMessage(clazz, msgMod, "private"),
            "170:34: " + getCheckMessage(clazz, msgMod, "public"),
            "172:35: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "174:31: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "176:34: " + getCheckMessage(clazz, msgMod, "protected"),
            "178:28: " + getCheckMessage(clazz, msgMod, "synchronized"),
            "182:28: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "184:41: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "186:34: " + getCheckMessage(clazz, msgMod, "private"),
            "197:27: " + getCheckMessage(clazz, msgMod, "private"),
            "199:26: " + getCheckMessage(clazz, msgMod, "public"),
            "203:22: " + getCheckMessage(clazz, msgMod, "protected"),
            "205:26: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
            "207:41: " + getCheckMessage(clazz, msgMod, "final"),
            "209:37: " + getCheckMessage(clazz, msgMod, "final"),
            "211:26: " + getCheckMessage(clazz, msgMod, "final"),
            "213:50: " + getCheckMessage(clazz, msgMod, "private"),
            "215:28: " + getCheckMessage(clazz, msgMod, "synchronized"),
            "217:37: " + getCheckMessage(clazz, msgMod, "protected"),
            "219:22: " + getCheckMessage(clazz, msgAnnotation, "@MyAnnotation2"),
        };
        
        Configuration checkConfig = builder.getCheckConfig("ModifierOrder");
        String filePath = builder.getFilePath("ModifierOrderInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
