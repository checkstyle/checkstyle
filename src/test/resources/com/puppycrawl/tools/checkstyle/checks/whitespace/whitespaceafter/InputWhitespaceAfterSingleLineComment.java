/*
WhitespaceAfter
tokens = SINGLE_LINE_COMMENT

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterSingleLineComment {

    void test() {
        int ok = 1; // normal trailing comment
        int missingAfter = 2; //bad
        // violation above '//' is not followed by whitespace'
        int missingAfterWithNoSpaceBefore = 3;//bad
        // violation above '//' is not followed by whitespace'
        //bad line
        // violation above '//' is not followed by whitespace'
        String emoji = "😀"; //bad
        // violation above '//' is not followed by whitespace'
        //
        ///
        //////
        int markerOnly = 4;//
    }
}

///////////////////////////////////////////////////////
// Name:     DatabaseConnection.java
// Purpose:  Handles SQL Server sessions
// Author:   Jane Doe
// Date:     July 2026
// Version:  2.1
///////////////////////////////////////////////////////

class DatabaseConnection {
    // Class implementation
}

/// # Order Processing Service
/// This service handles the core logic for checking out items.
/// It integrates directly with the [PaymentProcessor] class.
///
/// ## Key Features
/// - Validates inventory levels
/// - Charges via third-party gateways
/// - Sends email confirmations
///
/// ### Usage Example
/// ```java
/// OrderService service = new OrderService();
/// service.process(1042);
/// ```
///
/// @param orderId the unique identifier of the order
/// @return `true` if successful; `false` otherwise
class OrderService {

    boolean processOrder(int orderId) {
        // Regular single-line implementation comment
        return true;
    }
}
