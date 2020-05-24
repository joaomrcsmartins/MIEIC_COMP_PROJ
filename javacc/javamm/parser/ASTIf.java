package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.StackUsage;

import java.io.PrintWriter;
import java.util.*;

/* Generated By:JJTree: Do not edit this line. ASTIf.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTIf extends ConditionalNode {
    public static int labelCounter = 0; //if/else counter
    private int requiredThenPops = 0;
    private int requiredElsePops = 0;

    public TreeSet<String> initializedVars = new TreeSet<>();

    public ASTIf(int id) {
        super(id);
    }

    public ASTIf(Javamm p, int id) {
        super(p, id);
    }

    @Override
    public void eval(Javamm parser) {
        super.eval(parser);
        ASTThen thenNode = ((ASTThen) this.jjtGetChild(1));
        thenNode.setTables(table, methodTable);
        ASTElse elseNode = ((ASTElse) this.jjtGetChild(2));
        elseNode.setTables(table, methodTable);
        final TreeSet<String> thenInitializedVars = thenNode.evaluate(parser);
        final TreeSet<String> elseInitializedVars = elseNode.evaluate(parser);

        final TreeSet<String> allInitializedVars = new TreeSet<String>() {{
            addAll(thenInitializedVars);
            addAll(elseInitializedVars);
        }};

        for (String identifier : allInitializedVars) {
            boolean inThen = thenInitializedVars.contains(identifier);
            boolean inElse = elseInitializedVars.contains(identifier);

            if(inThen && inElse) {
                this.table.getSymbol(identifier).setInitialized();
                this.initializedVars.add(identifier);
            }
            else if (inThen && !inElse)
                parser.semanticWarnings.add(new SemanticsException(identifier + " is not initialized in else", elseNode));
            else if (!inThen && inElse)
                parser.semanticWarnings.add(new SemanticsException(identifier + " is not initialized in then", elseNode));
        }
    }

    @Override
    public void write(PrintWriter writer) {
        SimpleNode expression = (SimpleNode) this.jjtGetChild(0);
        int currCounter = labelCounter;
        switch (expression.id) {
            case JavammTreeConstants.JJTAND:
                ASTAnd andExp = (ASTAnd) expression;
                andExp.write(writer,"else_" + currCounter);
                break;
            case JavammTreeConstants.JJTLESSTHAN:
                ASTLessThan lsThanExp = (ASTLessThan) expression;
                lsThanExp.write(writer, "else_" + currCounter);
                break;
            case JavammTreeConstants.JJTIDENTIFIER:
            case JavammTreeConstants.JJTBOOLEANVALUE:
            case JavammTreeConstants.JJTNEGATION:
            case JavammTreeConstants.JJTMETHODCALL:
                expression.write(writer);
                writer.println("  ifeq else_" + currCounter);
                break;
            default:
                return;
        }

        ASTThen thenNode = (ASTThen) this.jjtGetChild(1);
        ASTElse elseNode = (ASTElse) this.jjtGetChild(2);

        labelCounter++;
        thenNode.write(writer);
        StackUsage.popStack(writer, requiredThenPops);
        writer.println("  goto endif_" + currCounter);
        writer.println("else_" + currCounter + ":");
        elseNode.write(writer);
        StackUsage.popStack(writer, requiredElsePops);
        writer.println("endif_" + currCounter + ":");
    }

    @Override
    protected void calculateStackUsage(StackUsage stackUsage) {
        SimpleNode expression = (SimpleNode) this.jjtGetChild(0);
        switch (expression.id) {
            case JavammTreeConstants.JJTIDENTIFIER:
            case JavammTreeConstants.JJTBOOLEANVALUE:
            case JavammTreeConstants.JJTNEGATION:
            case JavammTreeConstants.JJTMETHODCALL:
                expression.calculateStackUsage(stackUsage);
                stackUsage.dec(1); // ifeq
                break;
            case JavammTreeConstants.JJTAND:
                ASTAnd andExp = (ASTAnd) expression;
                andExp.calculateParamsStackUsage(stackUsage);
                break;
            case JavammTreeConstants.JJTLESSTHAN:
                ASTLessThan lsThanExp = (ASTLessThan) expression;
                lsThanExp.calculateParamsStackUsage(stackUsage);
                break;
            default:
                return;
        }

        ASTThen thenNode = (ASTThen) this.jjtGetChild(1);
        ASTElse elseNode = (ASTElse) this.jjtGetChild(2);

        int stackUsageBeforeThen = stackUsage.getStackUsage();
        thenNode.calculateStackUsage(stackUsage);
        int stackUsageAfterThen = stackUsage.getStackUsage();

        stackUsage.set(stackUsageBeforeThen);
        elseNode.calculateStackUsage(stackUsage);

        this.requiredThenPops = stackUsageAfterThen - stackUsageBeforeThen;
        this.requiredElsePops = stackUsage.getStackUsage() - stackUsageBeforeThen;

        stackUsage.set(stackUsageBeforeThen);
    }
}
/* JavaCC - OriginalChecksum=3f17c4ed5b4fd5cc052c1c2d168b79b9 (do not edit this line) */
