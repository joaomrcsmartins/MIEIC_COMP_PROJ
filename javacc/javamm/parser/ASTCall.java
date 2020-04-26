/* Generated By:JJTree: Do not edit this line. ASTCall.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.ClassSymbol;
import javamm.semantics.MethodIdentifier;
import javamm.semantics.MethodSymbol;
import javamm.semantics.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;

public
class ASTCall extends TypeNode {
    private String methodInvokation;
    public boolean isStatic = false;

    public ASTCall(int id) {
        super(id);
    }

    public ASTCall(Javamm p, int id) {
        super(p, id);
    }

    public void evalWithIdentifier(String identifier, boolean newIdentifier, Javamm parser) {
        final ASTIdentifier methodIdentifier = (ASTIdentifier) this.jjtGetChild(0);
        final MethodIdentifier importMethodId = getMethodIdentifier(identifier + "." + methodIdentifier.identifierName);
        if (importMethodId == null)
            return;

        if (table.checkSymbol(identifier)) {
            final Symbol symbol = table.getSymbol(identifier);

            if ((!newIdentifier && symbol.getType() != Symbol.Type.OBJ)) {
                parser.semanticErrors.add(new SemanticsException(identifier + " is not an object", methodIdentifier));
                return;
            }
            final MethodIdentifier methodId = getMethodIdentifier(methodIdentifier.identifierName);
            if (methodId == null)
                return;

            final ClassSymbol classSymbol = (ClassSymbol) symbol;

            if (!classSymbol.getSymbolTable().checkSymbol(methodId)) {
                parser.semanticErrors.add(new SemanticsException("Method " + methodIdentifier.identifierName + " not found in class " + identifier, methodIdentifier));
                return;
            }
            this.type = classSymbol.getSymbolTable().getSymbol(methodId).getReturnType();
            //TODO: check if needed to write parent class in cases where we have extensions
            methodInvokation = "invokevirtual " + classSymbol.getClassName() + "/" + methodIdentifier.identifierName + "(";
            for (Symbol.Type type : methodId.getParameters()) {
                methodInvokation += Symbol.getJVMTypeByType(type);
            }
            methodInvokation += ")" + Symbol.getJVMTypeByType(this.type);

        } else if (methodTable.checkSymbol(importMethodId)) {
            final MethodSymbol symbol = methodTable.getSymbol(importMethodId);
            if (symbol.getParameters().size() != this.jjtGetNumChildren() - 1) {
                parser.semanticErrors.add(new SemanticsException("Method " + methodIdentifier.identifierName + " expected " +
                        symbol.getParameters().size() + " parameters, got " + (this.jjtGetNumChildren() - 1), methodIdentifier));
                return;
            }
            this.type = symbol.getReturnType();
            methodInvokation = "invokestatic " + identifier + "/" + methodIdentifier.identifierName + "(";
            for (Symbol.Type type : importMethodId.getParameters()) {
                methodInvokation += Symbol.getJVMTypeByType(type);
            }
            methodInvokation += ")" + Symbol.getJVMTypeByType(this.type);
            isStatic =  true;
        } else
            parser.semanticErrors.add(new SemanticsException(identifier + " was not found", methodIdentifier));

    }

    private MethodIdentifier getMethodIdentifier(String identifier) {
        final List<Symbol.Type> params = new ArrayList<>();
        for (int i = 1; i < this.jjtGetNumChildren(); i++) {
            SimpleNode node = (SimpleNode) this.jjtGetChild(i);
            if (node.id == JavammTreeConstants.JJTIDENTIFIER) {
                final ASTIdentifier identifierNode = (ASTIdentifier) node;
                if (!table.checkSymbol(identifierNode.identifierName)) {
                    parser.semanticErrors.add(new SemanticsException("No variable named " + identifierNode.identifierName + " found", node));
                    return null;
                }
                Symbol symbol = table.getSymbol(identifierNode.identifierName);
                params.add(symbol.getType());

            } else if (node instanceof TypeNode) {
                TypeNode typeNode = (TypeNode) node;
                typeNode.setTables(table, methodTable);
                typeNode.eval(parser);
                params.add(typeNode.type);
            } else
                params.add(VarNode.getType(node, table, parser));

        }
        return new MethodIdentifier(identifier, params);
    }

    public void evalWithThis() {
        final ASTIdentifier methodIdentifier = (ASTIdentifier) this.jjtGetChild(0);
        final MethodIdentifier methodId = getMethodIdentifier(methodIdentifier.identifierName);
        if (!methodTable.checkSymbol(methodId))
            parser.semanticErrors.add(new SemanticsException("Method " + methodIdentifier.identifierName + " not found in line " + getLine(), methodIdentifier));
        final Symbol symbol = methodTable.getSymbol(methodId);

        if (symbol.getType() != Symbol.Type.METHOD)
            parser.semanticErrors.add(new SemanticsException(methodIdentifier.identifierName + " is not a method", methodIdentifier));

        final MethodSymbol methodSymbol = (MethodSymbol) symbol;

        this.type = methodSymbol.getReturnType();
        methodInvokation = "invokevirtual " + methodIdentifier.identifierName + "("; //TODO:add the method class (what 'this' represents)
        for (Symbol.Type type : methodId.getParameters()) {
            methodInvokation += Symbol.getJVMTypeByType(type);
        }
        methodInvokation += ")" + Symbol.getJVMTypeByType(this.type);
    }

    @Override
    public void write(PrintWriter writer) {
        for (int i = 1; i < this.jjtGetNumChildren(); i++) {
            ((SimpleNode) this.jjtGetChild(i)).setTables(table, methodTable);
            ((SimpleNode) this.jjtGetChild(i)).write(writer);
        }
        writer.println(methodInvokation);
    }

}
/* JavaCC - OriginalChecksum=9dd75f9d47df5bc95ebf7e6f50766bb0 (do not edit this line) */
