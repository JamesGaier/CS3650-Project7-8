import java.util.HashMap;
import java.util.ArrayList;
import java.util.Stack;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.EmptyStackException;
public class CodeWriter {
    private PrintWriter pw;
    private static Stack<String> callStack;
    private String fileName;
    private int elseCounter;
    private int continueCounter;
    private int returnCount;
    private String outputDir;
    public CodeWriter(String outputDir) {

        elseCounter = 0;
        continueCounter = 0;
        returnCount = 0;
        callStack = new Stack<>();
        this.outputDir = outputDir;
        try{
            pw = new PrintWriter(new File(outputDir));

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public void writeArithmetic(String command) {
        if(command.equals("add")) {
            pw.println("@SP");
            pw.println("AM=M-1");
            pw.println("D=M");
            pw.println("A=A-1");
            pw.println("M=M+D");
        }
        else if(command.equals("sub")) {
            pw.println("@SP");
            pw.println("AM=M-1");
            pw.println("D=M");
            pw.println("A=A-1");
            pw.println("M=M-D");
        }
        else if(command.equals("neg")) {
            pw.println("@SP");
            pw.println("AM=M-1");
            pw.println("M=-M");
            pw.println("@SP");
            pw.println("AM=M+1");
        }
        else if(command.equals("eq")) {
            pw.println("@SP");
            pw.println("AM=M-1");
            pw.println("D=M");
            pw.println("A=A-1");
            pw.println("D=M-D");
            pw.println("@else"+elseCounter);
            pw.println("D;JEQ");
            pw.println("@SP");
            pw.println("A=M-1");
            pw.println("M=0");
            pw.println("@continue"+continueCounter);
            pw.println("0;JEQ");
            pw.println("(else"+elseCounter+++")");
            pw.println("@SP");
            pw.println("A=M-1");
            pw.println("M=-1");
            pw.println("(continue"+continueCounter+++")");
        }
        else if(command.equals("gt")) {
            pw.println("@SP");
            pw.println("AM=M-1");
            pw.println("D=M");
            pw.println("A=A-1");
            pw.println("D=M-D");
            pw.println("@else"+elseCounter);
            pw.println("D;JGT");
            pw.println("@SP");
            pw.println("A=M-1");
            pw.println("M=0");
            pw.println("@continue"+continueCounter);
            pw.println("0;JEQ");
            pw.println("(else"+elseCounter+++")");
            pw.println("@SP");
            pw.println("A=M-1");
            pw.println("M=-1");
            pw.println("(continue"+continueCounter+++")");
        }
        else if(command.equals("lt")) {
            pw.println("@SP");
            pw.println("AM=M-1");
            pw.println("D=M");
            pw.println("A=A-1");
            pw.println("D=M-D");
            pw.println("@else"+elseCounter);
            pw.println("D;JLT");
            pw.println("@SP");
            pw.println("A=M-1");
            pw.println("M=0");
            pw.println("@continue"+continueCounter);
            pw.println("0;JEQ");
            pw.println("(else"+elseCounter+++")");
            pw.println("@SP");
            pw.println("A=M-1");
            pw.println("M=-1");
            pw.println("(continue"+continueCounter+++")");
        }
        else if(command.equals("and")) {
            pw.println("@SP");
            pw.println("AM=M-1");
            pw.println("D=M");
            pw.println("A=A-1");
            pw.println("M=D&M");
        }
        else if(command.equals("or")) {
            pw.println("@SP");
            pw.println("AM=M-1");
            pw.println("D=M");
            pw.println("A=A-1");
            pw.println("M=D|M");
        }
        else if(command.equals("not")) {
            pw.println("@SP");
            pw.println("AM=M-1");
            pw.println("D=M");
            pw.println("M=!D");
            pw.println("@SP");
            pw.println("AM=M+1");
        }
    }
    public void WritePushPop(Command command, String segment, int num) {
        if(command == Command.C_PUSH) {
            if(segment.equals("constant")) {
                pw.println("@"+num);
                pw.println("D=A");
                pw.println("@SP");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("M=M+1");
            }
            else if(segment.equals("local")) {
                pw.println("@LCL");
                pw.println("D=M");
                pw.println("@"+num);
                pw.println("A=D+A");
                pw.println("D=M");
                pw.println("@SP");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("M=M+1");
            }
            else if(segment.equals("this")) {
                pw.println("@THIS");
                pw.println("D=M");
                pw.println("@"+num);
                pw.println("A=D+A");
                pw.println("D=M");
                pw.println("@SP");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("M=M+1");
                pw.println("//");
            }
            else if(segment.equals("that")) {
                pw.println("@THAT");
                pw.println("D=M");
                pw.println("@"+num);
                pw.println("A=D+A");
                pw.println("D=M");
                pw.println("@SP");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("M=M+1");
                pw.println("//");
            }
            else if(segment.equals("argument")) {
                pw.println("@ARG");
                pw.println("D=M");
                pw.println("@"+num);
                pw.println("A=D+A");
                pw.println("D=M");
                pw.println("@SP");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("M=M+1");
                pw.println("//");
            }
            else if(segment.equals("temp")) {
                pw.println("@"+(5+num));
                pw.println("D=M");
                pw.println("@SP");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("M=M+1");
                pw.println("//");
            }
            else if(segment.equals("pointer")) {
                pw.println("@"+(3+num));
                pw.println("D=M");
                pw.println("@SP");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("M=M+1");
                pw.println("//");
            }
            else if(segment.equals("static")) {
                pw.println("@"+fileName+"."+num);
                pw.println("D=M");
                pw.println("@SP");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("M=M+1");
                pw.println("//");
            }
        }
        else if(command == Command.C_POP) {

            if(segment.equals("local")) {
                pw.println("@LCL");
                pw.println("D=M");
                pw.println("@"+num);
                pw.println("D=D+A");
                pw.println("@R15");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("AM=M-1");
                pw.println("D=M");
                pw.println("@R15");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("//");
            }
            else if(segment.equals("this")) {
                pw.println("@THIS");
                pw.println("D=M");
                pw.println("@"+num);
                pw.println("D=D+A");
                pw.println("@R15");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("AM=M-1");
                pw.println("D=M");
                pw.println("@R15");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("//");
            }
            else if(segment.equals("that")) {
                pw.println("@THAT");
                pw.println("D=M");
                pw.println("@"+num);
                pw.println("D=D+A");
                pw.println("@R15");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("AM=M-1");
                pw.println("D=M");
                pw.println("@R15");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("//");
            }
            else if(segment.equals("argument")) {
                pw.println("@ARG");
                pw.println("D=M");
                pw.println("@"+num);
                pw.println("D=D+A");
                pw.println("@R15");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("AM=M-1");
                pw.println("D=M");
                pw.println("@R15");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("//");
            }
            else if(segment.equals("temp")) {
                pw.println("@"+(5+num));
                pw.println("D=A");
                pw.println("@R15");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("AM=M-1");
                pw.println("D=M");
                pw.println("@R15");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("//");
            }
            else if(segment.equals("pointer")) {
                pw.println("@"+(3+num));
                pw.println("D=A");
                pw.println("@R15");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("AM=M-1");
                pw.println("D=M");
                pw.println("@R15");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("//");
            }
            else if(segment.equals("static")) {
                pw.println("@"+fileName+"."+num);
                pw.println("D=A");
                pw.println("@R15");
                pw.println("M=D");
                pw.println("@SP");
                pw.println("AM=M-1");
                pw.println("D=M");
                pw.println("@R15");
                pw.println("A=M");
                pw.println("M=D");
                pw.println("//");
            }
        }
    }
    public void writeInit() {
        pw.println("@256");
        pw.println("D=A");
        pw.println("@SP");
        pw.println("M=D");
        writeCall("Sys.init", 0);
    }
    public void writeLabel(String label) {
        String funcName = null;
        if(!callStack.isEmpty()) {
            funcName = callStack.peek();
        }
        pw.println("("+funcName+"$"+label+")");
    }
    public void writeGoto(String label) {
        String funcName = null;
        if(!callStack.isEmpty()) {
            funcName = callStack.peek();
        }
        pw.println("@"+funcName + "$" + label);
        pw.println("0;JMP");
    }
    public void writeIf(String label) {
        String funcName = null;
        if(!callStack.isEmpty()) {
            funcName = callStack.peek();
        }
        pw.println("@SP");
        pw.println("AM=M-1");
        pw.println("D=M");
        pw.println("@"+funcName+"$"+label);
        pw.println("D;JNE");
        pw.println("//");
    }
    public void writeCall(String funcName, int numArgs) {
        callStack.push(funcName);
        pw.println("@return-address"+returnCount);
        pw.println("D=A");
        pw.println("@SP");
        pw.println("A=M");
        pw.println("M=D");
        pw.println("@SP");
        pw.println("M=M+1");

        pw.println("@LCL");
        pw.println("D=M");
        pw.println("@SP");
        pw.println("A=M");
        pw.println("M=D");
        pw.println("@SP");
        pw.println("M=M+1");

        pw.println("@ARG");
        pw.println("D=M");
        pw.println("@SP");
        pw.println("A=M");
        pw.println("M=D");
        pw.println("@SP");
        pw.println("M=M+1");

        pw.println("@THIS");
        pw.println("D=M");
        pw.println("@SP");
        pw.println("A=M");
        pw.println("M=D");
        pw.println("@SP");
        pw.println("M=M+1");

        pw.println("@THAT");
        pw.println("D=M");
        pw.println("@SP");
        pw.println("A=M");
        pw.println("M=D");
        pw.println("@SP");
        pw.println("M=M+1");

        pw.println("@5");
        pw.println("D=A");
        pw.println("@"+numArgs);
        pw.println("D=D+A");
        pw.println("@SP");
        pw.println("D=M-D");
        pw.println("@ARG");
        pw.println("M=D");

        pw.println("@SP");
        pw.println("D=M");
        pw.println("@LCL");
        pw.println("M=D");

        pw.println("@" + funcName);
        pw.println("0;JMP");

        pw.println("(return-address"+returnCount+++")");
    }
    public void writeReturn() {

        //FRAME = LCL
        pw.println("@LCL");
        pw.println("D=M");
        pw.println("@FRAME");
        pw.println("M=D");
        //RET = (FRAME-5)
        pw.println("@5");
        pw.println("A=D-A");
        pw.println("D=M");
        pw.println("@RETADD");
        pw.println("M=D");
        //ARG=pop()
        pw.println("@SP");
        pw.println("AM=M-1");
        pw.println("D=M");
        pw.println("@ARG");
        pw.println("A=M");
        pw.println("M=D");
        //SP=ARG+1
        pw.println("@ARG");
        pw.println("D=M");
        pw.println("@SP");
        pw.println("M=D+1");
        //THAT = *(FRAME-1)
        pw.println("@FRAME");
        pw.println("A=M-1");
        pw.println("D=M");
        pw.println("@THAT");
        pw.println("M=D");
        //THIS = *(FRAME-2)
        pw.println("@FRAME");
        pw.println("D=M");
        pw.println("@2");
        pw.println("A=D-A");
        pw.println("D=M");
        pw.println("@THIS");
        pw.println("M=D");
        //ARG = *(FRAME-3)
        pw.println("@FRAME");
        pw.println("D=M");
        pw.println("@3");
        pw.println("A=D-A");
        pw.println("D=M");
        pw.println("@ARG");
        pw.println("M=D");
        //LCL = *(FRAME-4)
        pw.println("@FRAME");
        pw.println("D=M");
        pw.println("@4");
        pw.println("A=D-A");
        pw.println("D=M");
        pw.println("@LCL");
        pw.println("M=D");
        //goto RET
        pw.println("@RETADD");
        pw.println("A=M");
        pw.println("0; JMP");
        pw.println("//END RETURN");
        try{
            callStack.pop();

        } catch(EmptyStackException e) {
            callStack.push("Main");
        }
    }
    public void writeFunction(String funcName, int numLocals) {
        pw.println("("+funcName+")");
        pw.println("@0");
        pw.println("D=A");
        for(int i = 0; i < numLocals; i++) {
            pw.println("@SP");
            pw.println("A=M");
            pw.println("M=D");
            pw.println("@SP");
            pw.println("M=M+1");
        }
    }
    public void Close() {
        pw.println("(END)");
        pw.println("@END");
        pw.println("0;JMP");
        pw.close();

    }
}