//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.grammar.java21;

import java.util.FormatProcessor;

public class InputStringTemplateCustom {
    String interpolationOfJSONBlockWithFMT(String feelsLike, float temperature, String unit) {
    return FormatProcessor.FMT
      . """
      {
        "feelsLike": "%1s\{ feelsLike }",
        "temperature": "%2.2f\{ temperature }",
        "unit": "%1s\{ unit }"
      }
      """ ;
    }

    String rawUnprocessedJSONBlock(String feelsLike, float temperature, String unit) {
    final StringTemplate template = StringTemplate.RAW."""
      {
        "feelsLike": "%1s\{ feelsLike }",
        "temperature": "%2.2f\{ temperature }",
        "unit": "%1s\{ unit }"
      }
      """ ;
    return STR.process(template);
    }

    String xProcessedJSONBlock(String feelsLike, float temperature, String unit) {
    final StringTemplate template = X."""
      {
        "feelsLike": "%1s\{ feelsLike }",
        "temperature": "%2.2f\{ temperature }",
        "unit": "%1s\{ unit }"
      }
      """ ;
      return new X().process(template);
    }

    // nested string template
    String nestedStringTemplate(String feelsLike, float temperature, String unit) {
    final StringTemplate template = X."""
      {
        "feelsLike": "%1s\{ X."\{ X."\{ feelsLike }" }" }",
        "temperature": "%2.2f\{ temperature }",
        "unit": "%1s\{ unit }"
      }
      """ ;
      return STR.process(template);
    }

    static class X implements StringTemplate.Processor<String, RuntimeException> {
        @Override
        public String process(StringTemplate stringTemplate) throws RuntimeException {
            return stringTemplate.toString();
        }
    }

}
