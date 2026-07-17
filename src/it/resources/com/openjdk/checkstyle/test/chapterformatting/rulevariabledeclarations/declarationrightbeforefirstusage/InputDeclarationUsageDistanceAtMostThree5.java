package com.openjdk.checkstyle.test.chapterformatting.rulevariabledeclarations.declarationrightbeforefirstusage;

// violation first line 'Header mismatch*'

/** Some javadoc. */
public class InputDeclarationUsageDistanceAtMostThree5 {

    /** Some javadoc. */
    protected JmenuItem createSubMenuItem(LogLevel level) {
        final JmenuItem result = new JmenuItem(level.toString());
        final LogLevel logLevel = level;
        result.setMnemonic(level.toString().charAt(0));
        result.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showLogLevelColorChangeDialog(result, logLevel);
                        // DECLARATION OF VARIABLE 'logLevel', SHOULD BE HERE (distance = 2)
                    }
                });
        return result;
    }

    /** Some javadoc. */
    public static Color darker(Color color, double fraction) {
        int red = (int) Math.round(color.getRed() * (1.0 - fraction));
        int green = (int) Math.round(color.getGreen() * (1.0 - fraction));
        int blue = (int) Math.round(color.getBlue() * (1.0 - fraction));
        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }
        if (green < 0) { // DECLARATION OF VARIABLE 'green' SHOULD BE HERE (distance = 2)
            green = 0;
        } else if (green > 255) {
            green = 255;
        }
        if (blue < 0) { // DECLARATION OF VARIABLE 'blue' SHOULD BE HERE (distance = 3)
            // blue = 0;
        }
        int alpha = color.getAlpha();
        return new Color(red, green, blue, alpha);
    }

    /** Some javadoc. */
    public void testFinal() {
        AuthUpdateTask task = null;
        final long intervalMs = 30 * 60000L;
        Object authCheckUrl = null;
        Object authInfo = null;
        task = new AuthUpdateTask(
                        authCheckUrl,
                        authInfo,
                        new IauthListener() {
                            @Override
                            public void authTokenChanged(String cookie, String token) {
                                fireAuthTokenChanged(cookie, token);
                            }
                        });
        Timer timer = new Timer("Auth Guard", true);
        timer.schedule(task, intervalMs / 2, intervalMs); // DECLARATION OF VARIABLE 'intervalMs'
        // SHOULD BE HERE (distance = 2)
    }

    /** Some javadoc. */
    public void testForCycle() {
        int filterCount = 0;
        for (int i = 0; i < 10; i++, filterCount++) {
            int abc = 0;
            System.identityHashCode(abc);
        }
    }

    private void showLogLevelColorChangeDialog(JmenuItem j, LogLevel l) {}

    void fireAuthTokenChanged(String s, String s1) {}

    class JmenuItem {
        JmenuItem(String string) {}

        public void addActionListener(ActionListener actionListener) {}

        public void setMnemonic(char charAt) {}
    }

    class LogLevel {}

    class ActionListener {}

    class ActionEvent {}

    static class Color {
        Color(int red, int green, int blue, int alpha) {}

        public double getRed() { return 0; }

        public int getAlpha() { return 0; }

        public double getBlue() { return 0; }

        public double getGreen() { return 0; }
    }

    class AuthUpdateTask {
        AuthUpdateTask(Object authCheckUrl, Object authInfo, IauthListener authListener) {}
    }

    interface IauthListener {
        void authTokenChanged(String cookie, String token);
    }

    class Timer {
        Timer(String string, boolean b) {}

        public void schedule(AuthUpdateTask authUpdateTask, long l, long intervalMs) {}
    }
}
