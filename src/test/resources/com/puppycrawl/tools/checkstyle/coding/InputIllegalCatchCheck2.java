package com.puppycrawl.tools.checkstyle.checks.coding;

public class InputIllegalCatchCheck2 {
    public void foo() {
        try {
        } catch (RuntimeException | SQLException e) {

        } catch (RuntimeException | SQLException | OneMoreException e) {

        } catch (OneMoreException | RuntimeException | SQLException e) {

        } catch (OneMoreException | SQLException | RuntimeException e) {

        }

    }
}
