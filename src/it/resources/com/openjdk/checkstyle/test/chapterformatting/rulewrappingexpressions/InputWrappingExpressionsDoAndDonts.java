package com.openjdk.checkstyle.test.chapterformatting.rulewrappingexpressions;

// violation first line 'Header mismatch*'

import java.util.ArrayList;
import java.util.List;

public class InputWrappingExpressionsDoAndDonts {

    class Person {
        String getName() {
            return "";
        }
    }

    public void methodCall(int a, int b) {
    }

    public void popupMsg(String message) {
    }

    public void styleGuideDo(int a, int simple, int formula, int some, int complex,
            int spanning, int multiple, int lines, int may, int look,
            int as, int follows, int newMsgs) {

        List<Person> persons = new ArrayList<>();

        methodCall(a * simple - formula,
                   some + complex - formula * spanning
                        + multiple - lines * may
                        + look - as * follows);

        popupMsg("Inbox notification: You have "
                + newMsgs + " new messages");

        persons.stream()
                .map(Person::getName)
                .forEach(System.out::println);
    }

    public void styleGuideDonts(int a, int simple, int formula, int some, int complex,
            int spanning, int multiple, int lines, int should, int look, int not,
            int as, int follows, int newMsgs) {

        List<Person> persons = new ArrayList<>();

        // violation 2 lines below ''\+' should be on a new line.'
        methodCall(a * simple - formula,
                   some + complex - formula * spanning +
                   multiple - lines * should + not *
                   look - as * follows);
        // violation 2 lines above ''*' should be on a new line.'

        // violation below ''\+' should be on a new line.'
        popupMsg("Inbox notification: You have " +
                newMsgs + " new messages");

        // violation below ''.' should be on a new line.'
        persons.stream().
                // violation below ''.' should be on a new line.'
                map(Person::getName).
                forEach(System.out::println);
    }
}
