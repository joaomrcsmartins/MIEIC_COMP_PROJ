package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.Symbol;
import javamm.semantics.SymbolTable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* Generated By:JJTree: Do not edit this line. ASTMethod.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTMethod extends TypeNode {
    public List<Symbol> parameters = new ArrayList<>();
    public Boolean hasThis = null;

    public ASTMethod(int id) {
        super(id);
    }

    public ASTMethod(Javamm p, int id) {
        super(p, id);
    }

    @Override
    public void eval(Javamm parser) {
        if (this.jjtGetNumChildren() != 2 && this.jjtGetNumChildren() != 3){
            parser.semanticErrors.add(new SemanticsException("Lacks the number of required children!", this));
            return;
        }
        SimpleNode methodType = (SimpleNode) this.jjtGetChild(0);
        SimpleNode parameters;

        if (this.jjtGetNumChildren() == 2) {
            parameters = null;
        } else {
            parameters = (SimpleNode) this.jjtGetChild(1);
        }

        if (methodType.id == JavammTreeConstants.JJTMETHODNAME) {
            methodType.setTables(table, methodTable);
            ASTMethodName method = (ASTMethodName) methodType;
            this.parameters = method.parameters;
            method.eval(parser, parameters);
            this.type = method.returnType;
        } else if (methodType.id == JavammTreeConstants.JJTMAIN) {
            methodType.setTables(table, methodTable);
            methodType.eval(parser);
        } else {
            parser.semanticErrors.add(new SemanticsException("Wrong method type was found", methodType));
        }
    }

    public void processBody(Javamm parser, int stackPointer) {
        ASTMethodBody methodBody;

        if (this.jjtGetNumChildren() == 2) {
            methodBody = (ASTMethodBody) this.jjtGetChild(1);
        } else {
            methodBody = (ASTMethodBody) this.jjtGetChild(2);
        }
        methodBody.returnType = this.type;
        methodBody.setTables(table, methodTable);
        methodBody.eval(parser, stackPointer);
    }

    @Override
    public void write(PrintWriter writer) {
        SimpleNode methodType = (SimpleNode) this.jjtGetChild(0);

        if (methodType.id == JavammTreeConstants.JJTMETHODNAME) {
            methodType.write(writer);
        } else if (methodType.id == JavammTreeConstants.JJTMAIN) {
            writer.println(".method public static main([Ljava/lang/String;)V");
        }

        int paramsCount = 0;
        ASTMethodBody methodBody;
        if (this.jjtGetNumChildren() == 2) {
            methodBody = (ASTMethodBody) this.jjtGetChild(1);
        } else {
            paramsCount = ((ASTParameters) this.jjtGetChild(1)).nParams;
            methodBody = (ASTMethodBody) this.jjtGetChild(2);
        }
        int localsLimit = paramsCount +
                methodBody.localsCount +
                (this.checkForThis(this.table) ? 1 : 0);
        int stackLimit = methodBody.getMaxStackUsage();

        writer.println("  .limit stack " + stackLimit);//TODO Check for these limits actual values
        writer.println("  .limit locals " + localsLimit + "\n");
        methodBody.write(writer);

        if(methodType.id == JavammTreeConstants.JJTMAIN || this.type == Symbol.Type.VOID) {
            writer.println("  return");
        }
        writer.println(".end method\n");
    }

    public List<Symbol> getParameters() {
        return parameters;
    }

    @Override
    public void printTable() {
        System.out.println("//".repeat(40));
        System.out.println("Method Table:");

        SimpleNode methodHeader = (SimpleNode) this.jjtGetChild(0);
        if (methodHeader.id == JavammTreeConstants.JJTMETHODNAME) {
            ASTMethodName method = (ASTMethodName) methodHeader;
            System.out.print("    Header: " + method.methodName + " | Params: <");
            for(int i = 0 ; i < parameters.size(); i++) {
                System.out.print(parameters.get(i).getType());
                if(i != parameters.size()-1)
                    System.out.print(",");
                else
                    break;
            }
            System.out.println("> | Returns: " + this.type + "\n");
        } else  {
            System.out.println("    Header: main | Returns: VOID\n");
        }

        if (!this.table.getTable().isEmpty()) {
            System.out.println("    Variable Table:");
            Iterator it = this.table.getTable().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                System.out.println("      Name: " + pair.getKey() + " | Type: " + ((Symbol) pair.getValue()).getType());
            }
            System.out.println();
        }
    }

    @Override
    protected boolean checkForThis(SymbolTable classTable) {
        if (this.hasThis == null)
            this.hasThis = super.checkForThis(classTable);
        return this.hasThis;
    }
}
/* JavaCC - OriginalChecksum=e01bdf01dd9e8aa606ef225a59a26df3 (do not edit this line) */
