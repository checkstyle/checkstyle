public class InputFallThrough
{
    void method(int i, int j, boolean cond) {
        while (true) {
            switch (i) {
            case 0: // no problem
            case 1:
                i++;
                break;
            case 2:
                i++;
            case 3: //fall through!!!
                i++;
                break;
            case 4:
                return;
            case 5:
                throw new RuntimeException("");
            case 6:
                continue;
            case 7: {
                break;
            }
            case 8: {
                return;
            }
            case 9: {
                throw new RuntimeException("");
            }
            case 10: {
                continue;
            }
            case 11: {
                i++;
            }
            case 12: //fall through!!!
                if (false)
                    break;
                else
                    break;
            case 13:
                if (true) {
                    return;
                }
            case 14:
                if (true) {
                    return;
                } else {
                    //do nothing
                }
            case 15: //fall through!!!
                do {
                    System.out.println("something");
                    return;
                } while(true);
            case 16:
                for (int j1 = 0; j1 < 10; j1++) {
                    System.err.println("something");
                    return;
                }
            case 17:
                while (true)
                    throw new RuntimeException("");
            case 18:
                while(cond) {
                    break;
                }
            case 19: //fall through!!!
                try {
                    i++;
                    break;
                } catch (RuntimeException e) {
                    break;
                } catch (Error e) {
                    return;
                }
            case 20:
                try {
                    i++;
                    break;
                } catch (RuntimeException e) {
                } catch (Error e) {
                    return;
                }
            case 21: //fall through!!!
                try {
                    i++;
                } catch (RuntimeException e) {
                    i--;
                } finally {
                    break;
                }
            case 22:
                try {
                    i++;
                    break;
                } catch (RuntimeException e) {
                    i--;
                    break;
                } finally {
                    i++;
                }
            case 23: //fall through!!!
                switch (j) {
                case 1:
                    continue;
                case 2:
                    return;
                default:
                    return;
                }
            case 24:
                switch (j) {
                case 1:
                    continue;
                case 2:
                    break;
                default:
                    return;
                }
            default: //fall through!!!
                // this is the last label
                i++;
            }
        }
    }
}
