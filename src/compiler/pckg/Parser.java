/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.pckg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser extends Tokenizer{

    Tokenizer tokens;
    //ArrayList arr;
    public Parser() throws IOException {
        
    }

    public Parser(Tokenizer tokens) throws IOException {
        this.tokens = tokens;
    }
    

    
    
    void declaration(ArrayList arr){
        
    }
    
    boolean variablDeclaration(ArrayList arr){
        Matcher m;
        
        if( arr.get(0).toString().equals("int") || arr.get(0).toString().equals("void")){
            Pattern pattern = Pattern.compile(super.identifireRegx);
            m = pattern.matcher(arr.get(1).toString());
             
            if( arr.get(2).toString().equals(";") ){
                return true;
            }
        }
        return false;
    }// end of method
    
    
}// end of class
