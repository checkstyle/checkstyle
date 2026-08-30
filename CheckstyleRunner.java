import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CheckstyleRunner {
    public static void main(String[] args) throws Exception {
        System.setProperty("checkstyle.basedir", ".");
        Configuration config = ConfigurationLoader.loadConfiguration(
            "/sun_checks.xml", new PropertiesExpander(System.getProperties()));
        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        checker.configure(config);
        
        List<File> files = new ArrayList<>();
        files.add(new File("src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/indentation/indentation/module-info/annotation/module-info.java"));
        int errors = checker.process(files);
        System.out.println("Errors found: " + errors);
    }
}
