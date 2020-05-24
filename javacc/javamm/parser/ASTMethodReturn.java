/* Generated By:JJTree: Do not edit this line. ASTMethodReturn.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.StackUsage;
import javamm.semantics.Symbol;
import java.io.PrintWriter;

public
class ASTMethodReturn extends SimpleNode {
    Symbol.Type expectedType = null;

    public ASTMethodReturn(int id) {
        super(id);
        this.validStatement = true;
    }

    public ASTMethodReturn(Javamm p, int id) {
        super(p, id);
        this.validStatement = true;
    }

    @Override
    public void eval(Javamm parser) {
        if(expectedType != Symbol.Type.VOID) {
            if(this.jjtGetNumChildren() == 0) {
                parser.semanticErrors.add(new SemanticsException("Expected return type: " + expectedType,this));
                return;
            }

            if (!(this.jjtGetChild(0) instanceof TypeNode)) {
                parser.semanticErrors.add(new SemanticsException("Unknown return type!", this));
                return;
            }

            final TypeNode node = (TypeNode) this.jjtGetChild(0);
            node.setTables(table, methodTable);
            node.evaluateChild(node, new Symbol(expectedType), parser);
        } else {
            if(this.jjtGetNumChildren() >0) {
                parser.semanticErrors.add(new SemanticsException("Expected return type: " + expectedType,this));
            }
        }
    }

    @Override
    public void write(PrintWriter writer) {
        if(expectedType != Symbol.Type.VOID) {
            final SimpleNode node = (SimpleNode) this.jjtGetChild(0);
            node.write(writer);
            writer.println("  "  + Symbol.getJVMPrefix(expectedType) + "return");
        }
    }

    @Override
    protected void calculateStackUsage(StackUsage stackUsage) {
        if(expectedType != Symbol.Type.VOID) {
            final SimpleNode node = (SimpleNode) this.jjtGetChild(0);
            node.calculateStackUsage(stackUsage);
            stackUsage.dec(1);
        }
    }
}
/* JavaCC - OriginalChecksum=f35d6e18a29162cb6c0e550634450721 (do not edit this line) */
