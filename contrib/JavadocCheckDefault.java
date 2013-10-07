
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Tool that generates Javadoc description of the default tokens for a Check.
 * @author Rick Giles
 * @version 27-Nov-2002
 */
public class JavadocCheckDefault
{

    private static void usage()
    {
        System.out.println("Usage: java JavadocCheckDefault check element");
        System.exit(0);
    }
    
    public static void main(String[] args)
    {        
        if (args.length < 2) {
            usage();
        }
        final String header =
            " * <p> By default the check will check the following "
                + args[1] + ":\n";
        final String footer = ".\n * </p>\n";
        final String prefix = " *  {@link TokenTypes#";

        try {
            final Class clazz = Class.forName(args[0]);
            final Check check = (Check) clazz.newInstance();            
            final int[] defaultTokens = check.getDefaultTokens();
            if (defaultTokens.length > 0) {
                final StringBuffer buf = new StringBuffer();
                buf.append(header);
                final ArrayList tokenNames = new ArrayList();
                for (int i = 0; i < defaultTokens.length; i++) {
                    tokenNames.add(TokenTypes.getTokenName(defaultTokens[i]));
                }
                Collections.sort(tokenNames);
                final Iterator it = tokenNames.iterator();
                String token = (String) it.next();
                buf.append(prefix + token + " " + token + "}");
                while (it.hasNext()) {
                    token = (String) it.next();
                    buf.append(",\n" + prefix + token + " " + token + "}");
                }
                buf.append(footer);
                System.out.println(buf);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }
}
