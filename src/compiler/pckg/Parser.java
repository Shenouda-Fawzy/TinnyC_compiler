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
    private boolean parsed;
    
    
    public Parser() throws IOException {
        parsed = true;
    }

    public Parser(Tokenizer tokens) {
        parsed = true;
    }
    

// Parssing the code according to the following code(Grammer).
    
    public void programe(){
        declarationList();
    }
    
    private void declarationList(){
        
        
        declaration();
        while( (i < ENDFILE) && !tokens.get(i).getToken().equals(ENDTOKEN) )
            declaration();
    }
     
    private void declaration(){
        type_specifier();
        String dTok = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(dTok); // match ID
        if(m.matches()){
            int x = i;
            x++;
            if(tokens.get(x).getToken().equals("(")){
                //i++;
                function_declaration();
            }
            else
                variable_declartion();
        }
        
    }
    
    private void variable_declartion(){ // This just handle the declaration without intializing the variable.
        type_specifier();
        
        String str = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(str); // match ID
        
        if(m.matches())
            matchToken(str); // match ID

        if(tokens.get(i).getToken().equals("[")){
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
        }
        matchToken(";");
    }
    
    private void type_specifier(){
        String tsTok = tokens.get(i).getToken();
        if(tsTok.equals("int")){
            matchToken("int");
        }
        else if(tsTok.equals("void")){
            matchToken("void");
        }

    }
    
    private void function_declaration(){
        
        //type_specifier();
        String fdTok = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(fdTok); // match ID
        if(m.matches()) // Match ID.
            matchToken(fdTok);

        matchToken("(");
        params();
        matchToken(")");
        compouned_Statment();
        
    }
    
    private void params() {
        String psTok = tokens.get(i).getToken();
        if(psTok.equals("void"))
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
        
        String pTok = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(pTok); // match ID
        if(m.matches()) // Match ID.
            matchToken(pTok);
        else // If it's not ID (This just for performance)
            syntaxError();
        
        if(tokens.get(i).getToken().equals("["))
            matchToken("[");
        if(tokens.get(i).getToken().equals("]"))
            matchToken("]");
    }

    private void compouned_Statment() { // --------
        //if( tokens.get(i).getToken().equals("int") || tokens.get(i).getToken().equals("void") ){
            //while(tokens.get(i).getToken().equals("int") || tokens.get(i).getToken().equals("void") ){
        matchToken("{");
        local_declaration();
        statment_list();
        matchToken("}");
            //}
       // }
    }
    
    private void local_declaration(){
        
        while ( tokens.get(i).getToken().equals("int") && (i < ENDFILE || !tokens.get(i).getToken().equals(ENDTOKEN)) ){ // while tok = int and ch = ';'
            variable_declartion();
        }
    
    }
    
    private void statment_list(){
        while ( ( tokens.get(i).getToken().equals("int") ||
                tokens.get(i).getToken().equals("if") || 
                tokens.get(i).getToken().equals("return") || 
                tokens.get(i).getToken().equals("while") ||
                tokens.get(i).getToken().equals("read") ||
                tokens.get(i).getToken().equals("write") ||
                tokens.get(i).getValue().equals("Identefier")) && 
                (i < ENDFILE || !tokens.get(i).getToken().equals(ENDTOKEN)) ) // tokens.get(i).getToken().equals("int") && 
            statment();
    }
    
    private void statment(){
        String sTok = tokens.get(i).getToken();
//        //sTok = tokens.get(i).getToken();
//        Pattern p = Pattern.compile(this.identifireRegx);
//        Matcher m = p.matcher(sTok); // match ID
                
        switch(sTok){
            case "if":
                selection_stmt();
                break;
            case "while":
                iteration_stmt();
                break;
            case "read":
                read_stmt();
                break;
            case "return":
                return_stmt();
                break;
            case "write":
                write_stmt();
                break;
            case "{":
            case "int":
            case "void":
                compouned_Statment();
                break;
                default:
                    expression_stmt();
                    break;
        }     
        
    }
    
    private void expression_stmt(){
        String exsTok = tokens.get(i).getToken();
        if(exsTok.equals(";"))
            matchToken(";");
        else{
            expressoin();
            matchToken(";");
        }
    }
    
    private void selection_stmt(){
        String ssTok = tokens.get(i).getToken();
        if(ssTok.equals("if"))
            matchToken(ssTok);
        matchToken("(");
        simple_expression();//expressoin();
        matchToken(")");
        statment();
        ssTok = tokens.get(i).getToken();
        if(ssTok.equals("else")){
            matchToken(ssTok);
            statment();
        }
        
    }
    
    private void iteration_stmt(){
        String itTok = tokens.get(i).getToken();
        
        if(itTok.equals("while"))
            matchToken(itTok);
        matchToken("(");
        expressoin();
        matchToken(")");
        statment();
    }
    
    private void return_stmt(){
        matchToken("return");
        
        String retTok = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(retTok); // match ID
        
        if(m.matches())
            expressoin(); 
        matchToken(";");
    }
    
    private void read_stmt(){
        matchToken("read");
        
        String redTok = tokens.get(i).getToken();
        matchToken(redTok);
        matchToken(";");
    }
    
    private void write_stmt(){
        matchToken("write");
        expressoin();
        matchToken(";");
        
    }
    
    private void expressoin(){ // x = y = z = 5 + 7 
        String expTok = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(expTok); // match ID
        int x = i;
        x++;
        
        while (m.matches() && tokens.get(x).getToken().equals("=")){ // while the current toke is ID and the next is '='
            matchToken(expTok); // Match ID
            matchToken("=");
            x++;
            expTok = tokens.get(i).getToken();
            p = Pattern.compile(this.identifireRegx);
            m = p.matcher(expTok); // match ID
        }
        
        simple_expression();
    }
    
    private void var (){
        String varTok = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(varTok); // match ID
        
        if(m.matches())
            matchToken(varTok);
        if(tokens.get(i).getToken().equals("[")){
            matchToken("[");
            expressoin();
            matchToken("]");
        }
    }
    
    private void simple_expression(){
        additive_expression();
        
        //String simTok = tokens.get(i).getToken();
//        Pattern p = Pattern.compile(this.relOperator);
//        Matcher m = p.matcher(simTok); // match ID
        if(tokens.get(i).getValue().equals("relOperator")){
            relop();
            additive_expression();
        }
        
    }
    
    private void relop(){
        String relTok = tokens.get(i).getToken();
        switch(relTok){
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
        while(tokens.get(i).getToken().equals("+") || tokens.get(i).getToken().equals("-")){
            addop();
            term();
        }
    }
    
    private void addop(){
        if(tokens.get(i).getToken().equals("+"))
            matchToken("+");
        else if(tokens.get(i).getToken().equals("-"))
            matchToken("-");
    }
    
    private void term(){
        factor();
        while (tokens.get(i).getToken().equals("*") || tokens.get(i).getToken().equals("/")){
            multop();
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
        
        String facTok = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.intLetral);
        Matcher m = p.matcher(facTok); // match ID
        
        Pattern id = Pattern.compile(this.identifireRegx);
        Matcher idM = id.matcher(facTok);
        
        if(tokens.get(i).getToken().equals("(")){
            matchToken("(");
            expressoin();
            matchToken(")");
        }
        else if(m.matches())
            matchToken(facTok); // Matching NUM [0-9]
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
        String calTok = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(calTok); // match ID
        if(m.matches())
            matchToken(calTok);
        else
            syntaxError();
        matchToken("(");
        args();
        matchToken(")");
        
    }
    
    private void args(){
        String argTok = tokens.get(i).getToken();
        Pattern p = Pattern.compile(this.identifireRegx);
        Matcher m = p.matcher(argTok); // match ID
        if(m.matches()){
            matchToken(argTok);
            arg_list();
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
        if(i == ENDFILE|| tokens.get(i).getToken().equals(ENDTOKEN) ){ // Reached the end of file(Code).
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
        parsed = false;
        
        //System.exit(0); // exit the programe if Syntax erro happend.
        
    }
    public boolean isParsed(){
        return parsed;
    }
    
    public void resetParser(){
        tokens.clear();
        arr.clear();
    }
}// end of parser class
