package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

public class InputRareSyntaxNoExceptionLocalTypes {
    public String run(Object value) {
        class Local {
            private final String text;

            Local(String text) {
                this.text = text;
            }

            String shout() {
                return text.toUpperCase();
            }
        }
        record LocalRecord(String text, int count) {
            String repeat() {
                return text.repeat(count);
            }
        }
        enum LocalEnum {
            A,
            B
        }
        interface LocalInterface {
            String name();
        }
        Local local = new Local("local");
        LocalRecord record = new LocalRecord("x", 3);
        LocalEnum kind = LocalEnum.A;
        LocalInterface named = () -> "named";
        String result = local.shout() + record.repeat() + kind + named.name();
        if (value instanceof String text) {
            return result + text;
        }
        return result;
    }
}
