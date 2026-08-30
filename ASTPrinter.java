import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import java.io.File;

public class ASTPrinter {
    public static void main(String[] args) throws Exception {
        DetailAST ast = JavaParser.parseFile(new File("TestModule.java"), JavaParser.Options.WITHOUT_COMMENTS);
        printAST(ast, "");
    }
    private static void printAST(DetailAST ast, String indent) {
        if (ast == null) return;
        System.out.println(indent + ast.getText() + " [" + ast.getType() + "]");
        printAST(ast.getFirstChild(), indent + "  ");
        printAST(ast.getNextSibling(), indent);
    }
}
