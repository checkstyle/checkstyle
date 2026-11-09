/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceListeners {

    protected JMenuItem createSubMenuItem(LogLevel level) {
        final JMenuItem result = new JMenuItem(level.toString());
        final LogLevel logLevel = level;
        result.setMnemonic(level.toString().charAt(0));
        result.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLogLevelColorChangeDialog(result, logLevel);
            }
        });
        return result;
    }

    public static Color darker(Color color, double fraction) {
        int red = (int) Math.round(color.getRed() * (1.0 - fraction));
        int green = (int) Math.round(color.getGreen() * (1.0 - fraction));
        int blue = (int) Math.round(color.getBlue() * (1.0 - fraction));

        if (red < 0) {
            red = 0;
        }
        else if (red > 255) {
            red = 255;
        }
        if (green < 0) {
            green = 0;
        }
        else if (green > 255) {
            green = 255;
        }
        if (blue < 0) {
            // blue = 0;
        }

        int alpha = color.getAlpha();
        return new Color(red, green, blue, alpha);
    }

    public void testFinal() {
        AuthUpdateTask task = null;
        final long intervalMs = 30 * 60000L;
        Object authCheckUrl = null;
        Object authInfo = null;
        task = new AuthUpdateTask(authCheckUrl, authInfo, new IAuthListener() {
            @Override
            public void authTokenChanged(String cookie, String token) {
                fireAuthTokenChanged(cookie, token);
            }
        });

        Timer timer = new Timer("Auth Guard", true);
        timer.schedule(task, intervalMs / 2, intervalMs);
    }

    public void testForCycle() {
        int filterCount = 0;
        for (int i = 0; i < 10; i++, filterCount++) {
            int abc = 0;
            System.identityHashCode(abc);

            for (int j = 0; j < 10; j++) {
                abc = filterCount;
                System.identityHashCode(abc);
            }
        }
    }

    static class JMenuItem {
        public JMenuItem(String s) {}
        public void setMnemonic(char c) {}
        public void addActionListener(ActionListener a) {}
    }

    static class LogLevel {}
    interface ActionListener { void actionPerformed(ActionEvent e); }
    static class ActionEvent {}
    static void showLogLevelColorChangeDialog(JMenuItem j, LogLevel l) {}

    static class Color {
        public Color(int r, int g, int b, int a) {}
        public double getRed() { return 0; }
        public double getGreen() { return 0; }
        public double getBlue() { return 0; }
        public int getAlpha() { return 0; }
    }

    static class AuthUpdateTask {
        public AuthUpdateTask(Object u, Object i, IAuthListener l) {}
    }

    interface IAuthListener { void authTokenChanged(String cookie, String token); }
    static void fireAuthTokenChanged(String s, String s1) {}

    static class Timer {
        public Timer(String s, boolean b) {}
        public void schedule(AuthUpdateTask t, long d, long p) {}
    }
}
