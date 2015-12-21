/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.pckg;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser extends Tokenizer{
    
    private static int i = 0; // to ket the next token. 
    private final int ENDFILE = tokens.size() - 1;
    
    
    public Parser() throws IOException {
        
    }

    public Parser(Tokenizer tokens) throws IOException {
        
    }
    

// Parssing the code according to the following code(Grammer).
    
    public void programe(){
        declarationList();
    }
    
    private void declarationList(){
        
        
        declaration();
        while(i != ENDFILE)
            declaration();
    }
     
    private void declaration(){
        type_specifier();
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(str); // match ID
        if(m.matches()){
            int x = i;
            x++;
            if(tokens.get(x).getToken().equals("("))
                function_declaration();
            else
                variable_declartion();
        }
        
    }
    
    private void variable_declartion(){ // Unable to handle arrays.
        type_specifier();
        
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(str); // match ID
        
        if(m.matches())
            matchToken(str); // match ID

        if(tokens.get(i).getToken().equals("["))
            matchToken("[");
        
        str = tokens.get(i).getToken();
        p = Pattern.compile(this.intLetral);
        m = p.matcher(str); // match NUM [0-9]
        
        if(m.matches())
            matchToken(str); // match NUM [0-9]
        else
            syntaxError(); // if it's not NUM (This is just for performance).
        if(tokens.get(i).getToken().equals("]"))
            matchToken("]");
        matchToken(";");
    }
    
    private void type_specifier(){
        if(tokens.get(i).getToken().equals("int")){
            matchToken("int");
        }
        else {
            matchToken("void");
        }

    }
    
    private void function_declaration(){
        type_specifier();
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(str); // match ID
        if(m.matches()) // Match ID.
            matchToken(str);

        matchToken("(");
        params();
        matchToken(")");
        compouned_Statment();
        
    }
    
    private void params() {
        if(tokens.get(i).getToken().equals("void"))
            matchToken("void");
        else
            param_list();
    }
    
    private void param_list(){
        param();
        
        while(tokens.get(i).getToken().equals(",")){
            matchToken(",");
            param();
        }
    }
    
    private void param(){
        type_specifier();
        
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(str); // match ID
        if(m.matches()) // Match ID.
            matchToken(str);
        else // If it's not ID (This just for performance)
            syntaxError();
        
        if(tokens.get(i).getToken().equals("["))
            matchToken("[");
        if(tokens.get(i).getToken().equals("]"))
            matchToken("]");
    }

    private void compouned_Statment() { // --------
        //if( tokens.get(i).getToken().equals("int") || tokens.get(i).getToken().equals("void") ){
            while(tokens.get(i).getToken().equals("int") || tokens.get(i).getToken().equals("void") ){
                local_declaration();
                statment_list();
            }
       // }
    }
    
    private void local_declaration(){
        
        while (i != ENDFILE)
            variable_declartion();
    
    }
    
    private void statment_list(){
        while (i != ENDFILE)
            statment();
    }
    
    private void statment(){
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(str); // match ID
        
        if(str.equals("if"))
            selection_stmt();
        else if(str.equals("while"))
            iteration_stmt();
        else if (str.equals("read"))
            read_stmt();
        else if(str.equals("write"))
            write_stmt();
        else if(str.equals("int") || str.equals("void"))
            compouned_Statment();
        if(m.matches())
            expression_stmt(); 
    }
    
    private void expression_stmt(){
        if(tokens.get(i).getToken().equals(";"))
            matchToken(";");
        else{
            expressoin();
            matchToken(";");
        }
    }
    
    private void selection_stmt(){
        String str = tokens.get(i).getToken();
        if(str.equals("if"))
            matchToken(str);
        matchToken("(");
        expressoin();
        statment();
        str = tokens.get(i).getToken();
        if(str.equals("else")){
            matchToken(str);
            statment();
        }
        
    }
    
    private void iteration_stmt(){
        String str = tokens.get(i).getToken();
        
        if(str.equals("while"))
            matchToken(str);
        matchToken("(");
        expressoin();
        matchToken(")");
        statment();
    }
    
    private void return_stmt(){
        matchToken("return");
        
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(str); // match ID
        
        if(m.matches())
            expressoin();  
    }
    
    private void read_stmt(){
        matchToken("read");
        
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(str); // match ID
        if(m.matches())
            matchToken(str);
    }
    
    private void write_stmt(){
        matchToken("write");
        expressoin();
    }
    
    private void expressoin(){
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(str); // match ID
        while (m.matches()){
            str = tokens.get(i).getToken();
            p = Pattern.compile(this.identifireRegx);
            m = p.matcher(str); // match ID
            matchToken("=");
        }
        
        simple_expression();
    }
    
    private void var (){
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(str); // match ID
        
        if(m.matches())
            matchToken(str);
        if(tokens.get(i).getToken().equals("[")){
            matchToken("[");
            expressoin();
            matchToken("]");
        }
    }
    
    private void simple_expression(){
        additive_expression();
        
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.relOperator);
        Matcher m = p.matcher(str); // match ID
        if(m.matches()){
            matchToken(str);
            additive_expression();
        }
        
    }
    
    private void relop(){
        String str = tokens.get(i).getToken();
        switch(str){
            case "<=":
                matchToken("<=");
                break;
            case "<":
                matchToken("<=");
                break;
            case ">":
                matchToken(">");
                break;
            case ">=":
                matchToken(">=");
                break;
            case "!=":
                matchToken("!=");
                break;
            case "==":
                matchToken("==");
                break;
            default :
                    syntaxError();
                    break;
        }
    }
    
    private void additive_expression(){
        term();
        while(tokens.get(i).getToken().equals("+")){
            matchToken("+");
            term();
        }
    }
    
    private void addop(){
        if(tokens.get(i).getToken().equals("+"))
            matchToken("+");
        else
            matchToken("-");
    }
    
    private void term(){
        factor();
        while (tokens.get(i).getToken().equals("*")){
            matchToken("*");
            factor();
        }
    }
    
    private void multop(){
         if(tokens.get(i).getToken().equals("*"))
            matchToken("*");
        else
            matchToken("/");
    }
    
    private void factor(){
        
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.intLetral);
        Matcher m = p.matcher(str); // match ID
        
        Pattern id = Pattern.compile(this.identifireRegx);
        Matcher idM = id.matcher(str);
        
        if(tokens.get(i).getToken().equals("(")){
            matchToken("(");
            expressoin();
            matchToken(")");
        }
        else if(m.matches())
            matchToken(str); // Matching NUM [0-9]
        else if(idM.matches()){
            int j = i;
            j++;
            if(tokens.get(j).getToken().equals("("))
                call();
            else
                var();
        }
    }
    
    private void call(){
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(str); // match ID
        if(m.matches())
            matchToken(str);
        else
            syntaxError();
        matchToken("(");
        args();
        matchToken(")");
        
    }
    
    private void args(){
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(str); // match ID
        if(m.matches()){
            matchToken(str);
        }
        else
            empty();
    }
    
    private void arg_list(){
        expressoin();
        while( tokens.get(i).getToken().equals(",") ){
            matchToken(",");
            expressoin();
        }
    }
    
    
    
    private void empty(){} // do nothing just empty
    
    
    
    
    private boolean matchToken(String expected){
        if(i == ENDFILE){ // Reached the end of file(Code).
            System.out.println("Program Parsed Successfully");
            return true;
        }
        else if( expected.equals(tokens.get(i).getToken()) ){
            i++;// get the next token to be matched the next call.
            return true;
        }
        else{
            syntaxError();
            return false;
        }
            
            
    }
    
    private void syntaxError(){
        System.out.println("Syntax Error!");
        System.exit(1); // exit the programe if Syntax erro happend.
        
    }
    
    
}// end of parser class
