package base;

import base.semantics.ClassSymbol;
import base.semantics.Symbol;
import base.semantics.Symbol.Type;

/* Generated By:JJTree: Do not edit this line. ASTNew.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTNew extends TypeNode {
    public ClassSymbol classSymbol;
    public String identifier;
    public ASTNew(int id) {
        super(id);
        type = Type.CLASS;
    }

    public ASTNew(Parser p, int id) {
        super(p, id);
        type = Type.CLASS;
    }

    @Override
    public void eval() throws SemanticsException {
        if (this.jjtGetNumChildren() != 1) throw new SemanticsException("New operation is unary");

        SimpleNode child = (SimpleNode) this.jjtGetChild(0);
        this.evaluateChild(child, new Symbol(type));

        ASTIdentifier identifier = (ASTIdentifier) child;
        classSymbol = (ClassSymbol) table.getSymbol(identifier.identifierName);
        this.identifier = identifier.identifierName;
    }
}
/* JavaCC - OriginalChecksum=c6d588009442d8c81f835326710afcd3 (do not edit this line) */
