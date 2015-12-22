/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package compiler.pckg;

import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

        
public class Tokenizer {
    
    protected final String identifireRegx = "[a-zA-Z]([a-zA-Z]|\\d|_)*";
    protected final String intLetral = "[0-9]+";
    protected final String keyWord = "main|int|bool|string|void|true|false|if|else|while|return";
    protected final String strLetral = "\"(\\w|\\s)*\""; // Matching string.
    protected final String comment = "//.*[^\\n]";
    protected final String operator = "\\=|\\+|\\*|\\/|\\-|\\<|\\>|\\<=|\\>=|\\==|\\!=|\\!|\\+=|\\-=|\\/=";
    protected final String relOperator = "\\<|\\>|\\<=|\\>=|\\==|\\!=";
    protected final String specialSymboles = ";|,|[\\]]|[\\[]|[\\(]|[\\)]|[\\}]|[\\{]"; // Matching: + - { } ( ) [ ] ; ,
    
    
    
    private Map<String , String> regExp;
    protected ArrayList arr; // Will contain the regExp.
    protected ArrayList<Token> tokens;
    
    private final String codePath = "code.txt"; // If the porgrame not run so we need to wirte the full path
    private final String tokenPath = "tokens.txt";
    //private final String tokenPath = "E:\\FCI_ASYUT\\4th year\\CS Department\\Compiler\\Compiler Project\\regExp.txt";
    
    private PrintWriter tokenFile; // For writing.
    private File codeFile;
    private FileReader inputFile; // For reading.
    
    public Tokenizer() throws IOException{
        
        arr = new ArrayList();
        tokens = new ArrayList<>();// Array List of 'Token' Object.
        
        regExp = new HashMap<>();
        regExp.put("Identefier", this.identifireRegx);
        regExp.put("Int Letral", this.intLetral);
        regExp.put("Key Word", this.keyWord);
        regExp.put("String Letral", this.strLetral);
        regExp.put("Comment" , this.comment);
        regExp.put("Operator", this.operator);
        regExp.put("relOperator", this.relOperator);
        regExp.put("Special Symbole", this.specialSymboles);
        
            

        try {
            tokenFile = new PrintWriter(tokenPath);
            codeFile = new File(codePath);
            inputFile = new FileReader(codeFile); // For readeing the code and tokenize it.
            
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Tokenizer.class.getName()).log(Level.SEVERE, null, ex);
            ex.getMessage();
        }
        
        
    }
    
    
    
    public ArrayList tokenizedInput(String input){
        //ArrayList arr = new ArrayList(); // Will contain the regExp.
        
        StringBuilder buffer = new StringBuilder(""); // Empty buffer.
        for(int i = 0 ; i < input.length() ; i++){
            StringBuilder twoChar = new StringBuilder();
                
// Check if it a special char or operator.
            char ch = input.charAt(i);
            if(ch == '\n')
                ch = ' ';
            if( (ch == '>' || ch == '<' || ch == '!' || ch == '=' || ch == '/' || ch == '+' || ch == '-' ) && (i < input.length() - 1) ){
                String str = buffer.toString();
                if( !str.isEmpty() ){
                    arr.add(str); // push token.
                    buffer.delete(0, buffer.length());
                }
                
                twoChar.append(ch);
                twoChar.append(input.charAt(i+1)); // check if it ">= , <= , != , ==" 
                String buff = twoChar.toString();
                
                if( buff.equals(">=") || buff.equals("<=") || buff.equals("!=") || buff.equals("//")
                        || buff.equals("+=") || buff.equals("-=") || buff.equals("==") || buff.equals("/=") ){
                    arr.add(buff); // push token.
                    i++;
                }
                else
                    arr.add(ch); // push token.
            }// ENd of uni operator .
            else if(ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '(' || ch == ')' || ch == '[' || ch == ']' ||
                    ch == '{' || ch == '}' || ch == ';' || ch == ',' || ch == ' ' || ch == '=' ){
                    String str = buffer.toString();
                    if( !str.isEmpty() ){
                        arr.add(str); // push token.
                        buffer.delete(0, buffer.length());
                    }
                    arr.add(ch); // push token.
            }
// In Not a special char or operator(alphabet char).
            else{
                //buffer.append(ch);
                if(ch == input.charAt(input.length() - 1)){ // if it the last char
                    buffer.append(ch);
                    String str = buffer.toString();
                    arr.add(str);
                }
                else
                    buffer.append(ch);
            }
                
        }// End of for loop.
        arr.add("END"); // This is a special token indecate the end of file.
        
        return this.matchTokensWithRegx();
    } // End of function.
    
    private ArrayList matchTokensWithRegx(){
        Matcher m;
       
        for(int i = 0 ; i < arr.size() - 1 ; i++){
            
            if(arr.get(i).toString().equals(" ")|| arr.get(i).toString().equals("\t") || arr.get(i).toString().equals("\n"))
                continue;
            
            boolean isValidToken;
            Pattern pattern = Pattern.compile(regExp.get("Key Word"));
            m = pattern.matcher(arr.get(i).toString());
            isValidToken = m.matches();
            if(isValidToken == true){
                System.out.println(arr.get(i).toString() + "\tKey Word");
                Token tok = new Token(arr.get(i).toString() , "Key Word");
                tokens.add(tok);
                continue;
                
            }

            pattern = Pattern.compile(regExp.get("relOperator"));
            m = pattern.matcher(arr.get(i).toString());
            isValidToken = m.matches();
            if(isValidToken == true){
                System.out.println(arr.get(i).toString() + "\tRelation Operator");
                Token tok = new Token(arr.get(i).toString() , "relOperator");
                tokens.add(tok);
                continue;
            }
            
            pattern = Pattern.compile(regExp.get("Operator"));
            m = pattern.matcher(arr.get(i).toString());
            isValidToken = m.matches();
            if(isValidToken == true){
                System.out.println(arr.get(i).toString() + "\tOperator");
                Token tok = new Token(arr.get(i).toString() , "Operator");
                tokens.add(tok);
                continue;
            }
            

            
            pattern = Pattern.compile(regExp.get("Int Letral"));
            m = pattern.matcher(arr.get(i).toString());
            isValidToken = m.matches();
            if(isValidToken == true){
                System.out.println(arr.get(i).toString() + "\tInt Leteral");
                Token tok = new Token(arr.get(i).toString() , "Int Leteral");
                tokens.add(tok);
                continue;
            }
            
            pattern = Pattern.compile(regExp.get("String Letral"));
            m = pattern.matcher(arr.get(i).toString());
            isValidToken = m.matches();
            if(isValidToken == true){
                System.out.println(arr.get(i).toString() + "\tString Leteral");
                Token tok = new Token(arr.get(i).toString() , "String Leteral");
                tokens.add(tok);
                continue;
            }
            
            pattern = Pattern.compile(regExp.get("Identefier"));
            m = pattern.matcher(arr.get(i).toString());
            isValidToken = m.matches();
            if(isValidToken == true){
                System.out.println(arr.get(i).toString() + "\tIdentefier");
                Token tok = new Token(arr.get(i).toString() , "Identefier");
                tokens.add(tok);
                continue;
            }
            
        
            
            pattern = Pattern.compile(regExp.get("Special Symbole"));
            m = pattern.matcher(arr.get(i).toString());
            isValidToken = m.matches();
            if(isValidToken == true){
                System.out.println(arr.get(i).toString() + "\tSpecial Symbole");
                Token tok = new Token(arr.get(i).toString() , "Special Symbole");
                tokens.add(tok);
                continue;
            }
            
            pattern = Pattern.compile(regExp.get("Comment"));
            m = pattern.matcher(arr.get(i).toString());
            isValidToken = m.matches();
            if(isValidToken == true){
                System.out.println(arr.get(i).toString() + "\tcomment");
                Token tok = new Token(arr.get(i).toString() , "comment");
                tokens.add(tok);
                continue;
            }
            System.out.println(arr.get(i).toString() + "\tInvlalid token");
            
        }// End of for loop
    
        
        return tokens;
    }// end of method
}
