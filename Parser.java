import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Arrays;
public class Parser {
    private BufferedReader br;
    private String currentCommand;
    private String fileName;
    private String path;
    public void setReadFile() {
        try{
            br = new BufferedReader(new FileReader(path+fileName));
            currentCommand = br.readLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void setFileName(final String path, final String fileName) {
        this.fileName = fileName;
        this.path = path;
    }
    public boolean hasMoreCommands() {
        try{
            if(currentCommand == null) {
                br.close();
                return false;
            }
            else {
                return true;
            }
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isComment() {
        return currentCommand.substring(0,2).equals("//");
    }
    public boolean isEmpty() {
        return currentCommand.length() == 0;
    }
    public boolean onlyNewLine() {
        String newline = System.getProperty("line.separator");
        return currentCommand.equals(newline);
    }
    public void clean() {
        if(currentCommand.contains("//")) {
            currentCommand = currentCommand.substring(0, currentCommand.indexOf("//"));
        }
        currentCommand = currentCommand.trim();
    }


    public void advance() {
        try{
            currentCommand = br.readLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public Command commandType() {
        if(currentCommand.equals("add")
        || currentCommand.equals("sub")
        || currentCommand.equals("neg")
        || currentCommand.equals("eq")
        || currentCommand.equals("gt")
        || currentCommand.equals("lt")
        || currentCommand.equals("and")
        || currentCommand.equals("or")
        || currentCommand.equals("not")) {
            return Command.C_ARITHMETIC;
        }
        else if(currentCommand.contains("push")) {
            return Command.C_PUSH;
        }
        else if(currentCommand.contains("pop")) {
            return Command.C_POP;
        }
        else if(currentCommand.contains("label")) {
            return Command.C_LABEL;
        }
        else if(currentCommand.contains("if-goto")) {
            return Command.C_IF;
        }
        else if(currentCommand.contains("goto")) {
            return Command.C_GOTO;
        }
        else if(currentCommand.contains("function")) {
            return Command.C_FUNCTION;
        }
        else if(currentCommand.contains("return")) {
            return Command.C_RETURN;
        }
        else if(currentCommand.contains("call")) {
            return Command.C_CALL;
        }
        return null;
    }
    public String arg1() {
        if(commandType() == Command.C_ARITHMETIC
        || commandType() == Command.C_RETURN) {
            return currentCommand;
        }
        else {
            String[] parts = currentCommand.split(" ");
            return parts[1];
        }
    }
    public int arg2() {
        String[] parts = currentCommand.split(" ");
        return Integer.parseInt(parts[2]);
    }

}