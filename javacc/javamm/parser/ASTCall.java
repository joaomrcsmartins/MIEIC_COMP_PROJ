/* Generated By:JJTree: Do not edit this line. ASTCall.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public
class ASTCall extends TypeNode {
    public boolean isStatic = false;
    private String methodInvokation;
    private final String invokestatic = "invokestatic";
    private final String invokevirtual = "invokevirtual";
    public Symbol returnSymbol;

    public ASTCall(int id) {
        super(id);
    }

    public ASTCall(Javamm p, int id) {
        super(p, id);
    }

    public void setMethodInvokation(String invokationIdent, String classIdent, String methodIdent, List<Symbol.Type> types, Symbol.Type returnType) {
        methodInvokation = invokationIdent + classIdent + "/" + methodIdent + "(";
        for (Symbol.Type type : types) {
            methodInvokation += Symbol.getJVMTypeByType(type);
        }
        methodInvokation += ")" + Symbol.getJVMTypeByType(returnType);
    }

    public void evalWithIdentifier(String identifier, boolean newIdentifier, Javamm parser) {
        final ASTIdentifier methodIdentifier = (ASTIdentifier) this.jjtGetChild(0);
        final ASTCallParams staticCallParams =  ((ASTCallParams) this.jjtGetChild(1));
        staticCallParams.setTables(table, methodTable);
        final MethodIdentifier importMethodId = staticCallParams
                .getMethodIdentifier(identifier + "." + methodIdentifier.identifierName, parser);
        if (importMethodId == null)
            return;

        if (table.checkSymbol(identifier)) {
            final Symbol symbol = table.getSymbol(identifier);

            if ((!newIdentifier && symbol.getType() != Symbol.Type.OBJ)) {
                parser.semanticErrors.add(new SemanticsException(identifier + " is not an object", methodIdentifier));
                return;
            }
            final ASTCallParams callParams =  ((ASTCallParams) this.jjtGetChild(1));
            callParams.setTables(table, methodTable);
            final MethodIdentifier methodId = callParams
                    .getMethodIdentifier(methodIdentifier.identifierName, parser);
            if (methodId == null)
                return;

            final ClassSymbol classSymbol = (ClassSymbol) symbol;

            if (!classSymbol.getMethods().checkSymbol(methodId)) {
                parser.semanticErrors.add(new SemanticsException("Method " + methodIdentifier.identifierName + " not found in class " + identifier, methodIdentifier));
                return;
            }

            this.returnSymbol = classSymbol.getMethods().getSymbol(methodId).getReturnSymbol();
            this.type = returnSymbol.getType();
            setMethodInvokation("  " + invokevirtual + " ", classSymbol.getClassName(), methodIdentifier.identifierName, methodId.getParametersTypes(), this.type);

        } else if (methodTable.checkSymbol(importMethodId)) {
            final MethodSymbol symbol = methodTable.getSymbol(importMethodId);

            this.returnSymbol = symbol.getReturnSymbol();
            this.type = returnSymbol.getType();
            setMethodInvokation("  " + invokestatic + " ", identifier, methodIdentifier.identifierName, importMethodId.getParametersTypes(), this.type);
            isStatic = true;
        } else
            parser.semanticErrors.add(new SemanticsException(identifier + " was not found", methodIdentifier));

    }

    public void evalWithThis(Javamm parser) {
        final ASTIdentifier methodIdentifier = (ASTIdentifier) this.jjtGetChild(0);
        final ASTCallParams callParams =  ((ASTCallParams) this.jjtGetChild(1));
        callParams.setTables(table, methodTable);
        final MethodIdentifier methodId = callParams
                .getMethodIdentifier(methodIdentifier.identifierName, parser);
        if (!methodTable.checkSymbol(methodId)) {
            parser.semanticErrors.add(new SemanticsException("Method " + methodIdentifier.identifierName + " not found", methodIdentifier));
            return;
        }
        final MethodSymbol symbol = methodTable.getSymbol(methodId);

        if (symbol.getType() != Symbol.Type.METHOD) {
            parser.semanticErrors.add(new SemanticsException(methodIdentifier.identifierName + " is not a method", methodIdentifier));
            return;
        }
        this.returnSymbol = symbol.getReturnSymbol();
        this.type = returnSymbol.getType();
        setMethodInvokation("  " + invokevirtual + " ", this.table.getClassName(), methodIdentifier.identifierName, methodId.getParametersTypes(), this.type);
    }

    @Override
    public void write(PrintWriter writer) {
        for (int i = 1; i < this.jjtGetNumChildren(); i++) {
            ((SimpleNode) this.jjtGetChild(i)).write(writer);
        }
        writer.println(methodInvokation);
    }

    @Override
    protected void calculateStackUsage(StackUsage stackUsage) {
        ASTCallParams callParams = (ASTCallParams) this.jjtGetChild(1);
        callParams.calculateStackUsage(stackUsage);
        stackUsage.dec(callParams.nParams);
    }

}
/* JavaCC - OriginalChecksum=9dd75f9d47df5bc95ebf7e6f50766bb0 (do not edit this line) */
