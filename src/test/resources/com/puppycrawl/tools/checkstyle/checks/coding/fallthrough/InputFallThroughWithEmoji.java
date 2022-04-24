/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughWithEmoji {

    void foo() {

        while (true) {
            int i = 0;
            switch ("👍") {
                case "ds😂": // ok
                case "": i++; break;
                case "👇🏻":
                    i++;
                case "😂sda":
                    // violation above 'Fall through from previous branch of the switch statement.'
                    i++;
                case "d😂sda": return;
                    // violation above 'Fall through from previous branch of the switch statement.'

                case "5😆": throw new RuntimeException("");
                case "🧐6":
                    do {
                        System.identityHashCode("something");
                        return;
                    } while(true);
                case "7🤛🏻":
                    for (int j1 = 0; j1 < 10; j1++) {
                        String.valueOf("something");
                        "7🤛🏻".toString(); return;
                    }
                case "8🥳": try {
                        i++;
                        String s ="8🥳"; break;
                    } catch (RuntimeException e) {
                        i--;

                    } finally {
                        i++;
                    }
                    // fall👉🏻through,
                case "9": String s = "s🥳d🥳s";
                // violation above 'Fall through from previous branch of the switch statement.'
                // FALLTHRU (case-sensitive)
                default: // violation 'Fall through from previous branch of the switch statement.'
                    "🥳".toString().equals("🥳");

                    // this is the last label
                    i++;
            }
        }
    }

    void fooFallThru() {
        int i = 0;
        switch ("") {

            case "ds😂": // ok
            case "":
                i++;
                break;
            case "👇🏻": i++;

                // fallthru 😂 works
            case "😂sda":
                i++;
                // fallthru
            case "dsda":
                return;
            case "5😆":
                throw new RuntimeException("");
            case "🧐6":
                do {
                    System.identityHashCode("something");

                } while(true);
                /*falls through*/
            case "7🤛🏻": for (int j1 = 0; j1 < 10; j1++) {
                    String.valueOf("something");
                    return;
                }
            case "8🥳": try { break;
                } catch (RuntimeException e) {
                    i--;
                    break;
                } finally {
                    i++;
                }
            case "9": String s = "s🥳d🥳s";
                //🥳d🥳 fallthru

            case "10": String s2 = "s🥳d🥳s";
            /*🥳🥳🥳🥳🥳🥳*/ /* fallthru */ default: i++; // ok

    }
    }
}
