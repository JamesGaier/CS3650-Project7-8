import java.io.File;
import java.util.List;
import java.util.Arrays;

public class VirtualMachine {
    private Parser parser;
    private CodeWriter codeWriter;
    private File folder;
    private String inputDir = "../08/ProgramFlow/FibonacciSeries/";
    private String outputDir = "../08/ProgramFlow/FibonacciSeries/FibonacciSeries.asm";
    public VirtualMachine() {
        folder = new File(inputDir);
        parser = new Parser();
        codeWriter = new CodeWriter(outputDir);
    }
    public void parseAndWrite() {
        while(parser.hasMoreCommands()) {
                if(parser.isEmpty()
                || parser.onlyNewLine()
                || parser.isComment()){
                    parser.advance();
                    continue;
                }
                parser.clean();
                Command commandType = parser.commandType();
                String arg1 = parser.arg1();
                int arg2 = 0;
                switch(commandType) {
                    case C_ARITHMETIC:
                        codeWriter.writeArithmetic(arg1);
                        break;
                    case C_PUSH:
                    case C_POP:
                        arg2 = parser.arg2();
                        codeWriter.WritePushPop(commandType, arg1, arg2);
                        break;
                    case C_LABEL:
                        codeWriter.writeLabel(arg1);
                        break;
                    case C_GOTO:
                        codeWriter.writeGoto(arg1);
                        break;
                    case C_IF:
                        codeWriter.writeIf(arg1);
                        break;
                    case C_CALL:
                        arg2 = parser.arg2();
                        codeWriter.writeCall(arg1, arg2);
                        break;
                    case C_RETURN:
                        codeWriter.writeReturn();
                        break;
                    case C_FUNCTION:
                        arg2 = parser.arg2();
                        codeWriter.writeFunction(arg1, arg2);
                        break;
                    default:
                        break;
            }
            parser.advance();
        }
    }
    public void run() {
        final List<File> fileList = Arrays.asList(folder.listFiles());
        for(File f: fileList) {
            if(f.getName().endsWith(".vm")){
                if(f.getName().equals("Sys.vm")) {
                    codeWriter.setFileName(f.getName());
                    parser.setFileName(inputDir, f.getName());
                    parser.setReadFile();
                    codeWriter.writeInit();
                    parseAndWrite();
                }
            }
        }

        for(File f: fileList) {
            if(f.getName().endsWith(".vm") && !f.getName().equals("Sys.vm")){
                codeWriter.setFileName(f.getName());
                parser.setFileName(inputDir, f.getName());
                parser.setReadFile();
                parseAndWrite();
            }
        }

        codeWriter.Close();
    }
    public static void main(String[] args) {
        VirtualMachine vm = new VirtualMachine();
        vm.run();
    }
}