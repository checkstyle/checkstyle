package com.openjdk.checkstyle.test.chapterformatting.ruleindentation;

// violation first line 'Header mismatch*'

public class InputIdentationDoAndDonts {

    enum Choice {
        ONE,
        TWO,
        THREE;
    }

    public void setChoice(String choice) {
    }

    public void styleGuideDo(Choice var) {
        switch (var) {
            case TWO: // violation ''case' construct must use '{}'s.'
                setChoice("two");
                break;
            case THREE: // violation ''case' construct must use '{}'s.'
                setChoice("three");
                break;
            default: // violation ''default' construct must use '{}'s.'
                throw new IllegalArgumentException();
        }
    }

    public void styleGuideDont(Choice var) {
        switch (var) {
        case TWO:
            // 2 violations above:
            // ''case' construct must use '{}'s.'
            // '.* incorrect indentation level 8, expected .* 12.'
            setChoice("two"); // violation '.* incorrect indentation level 12, expected .* 16.'
            break; // violation '.* incorrect indentation level 12, expected .* 16.'
        case THREE:
            // 2 violations above:
            // ''case' construct must use '{}'s.'
            // '.* incorrect indentation level 8, expected .* 12.'
            setChoice("three"); // violation '.* incorrect indentation level 12, expected .* 16.'
            break; // violation '.* incorrect indentation level 12, expected .* 16.'
        default:
            // 2 violations above:
            // ''default' construct must use '{}'s.'
            // '.* incorrect indentation level 8, expected .* 12.'
            throw new IllegalArgumentException();
            // violation above '.* incorrect indentation level 12, expected .* 16.'
        }
    }
}
