package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.ClassSymbol;

/* Generated By:JJTree: Do not edit this line. ASTMethodCall.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public class ASTMethodCall extends TypeNode {
    public ClassSymbol classSymbol;

    public ASTMethodCall(int id) {
        super(id);
    }

    public ASTMethodCall(Parser p, int id) {
        super(p, id);
    }

    @Override
    public void eval() throws SemanticsException {
        if (this.jjtGetNumChildren() != 2) throw new SemanticsException("Wrong number of children found");

        final SimpleNode methodName = (SimpleNode) this.jjtGetChild(0);
        final ASTCall call = ((ASTCall) this.jjtGetChild(1));
        call.setTables(table, methodTable);
        switch (methodName.id) {
            case ParserTreeConstants.JJTIDENTIFIER:
                final String methodIdentifier = ((ASTIdentifier) methodName).identifierName;
                call.evalWithIdentifier(methodIdentifier, false);
                break;
            case ParserTreeConstants.JJTTHIS:
                call.evalWithThis();
                break;
            case ParserTreeConstants.JJTNEW:
                ASTNew astNew = (ASTNew) methodName;
                astNew.setTables(table, methodTable);
                astNew.eval();
                call.evalWithIdentifier(astNew.identifier, true);
                break;
            default:
                throw new SemanticsException("Found type " + methodName.id + ", not supported");
        }
        this.type = call.type;
    }
}
/* JavaCC - OriginalChecksum=c431bc197d60321c47680450a6c0622a (do not edit this line) */
