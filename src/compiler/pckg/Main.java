/*
 *          ============= Shenouda Fawzy Morees =============
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compiler.pckg;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.misc.Queue;

public class Main {
    public static void main(String [] arg) throws IOException{
        System.out.println("Hello");
//        Tokenizer tokeniz = new Tokenizer();
//        tokeniz.tokenize2();
        //CompilerUI comp = new CompilerUI();
        //comp.setVisible(true);

        
// Stor them in List.
        //String letterRex = "[a-zA-Z]";
        String identifireRegx = "[a-zA-Z]([a-zA-Z]|\\d|_)*";
        String intLetral = "[0-9]+";
        String keyWord = "main|int|bool|string|void|true|false|if|else|while|return";
        String strLetral = "\"(\\w|\\s)*\"";
        String comment = "//.*[^\\n]";
        String operator = "\\+|\\*|\\/|\\-|\\<|\\>|\\<=|\\>=|\\==|\\!=|\\!";
        String specialSymboles = ";|,|[\\]]|[\\[]|[\\(]|[\\)]|[\\}]|[\\{]"; // Matching: + - { } ( ) [ ] ; ,
        
        String id1 = "1246"; // False.
        String id2 = "_S"; // False.
        String id3 = "a1a"; // True
        String id4 = "y12asd";// false
        String isKeyWord = "true";
        String isString = "\"shenouda Fawzy\"";
        String isComment = "//I'am shenouda";
        String isOperator = "==";
        String isSymbole = ";";
        
        
        Pattern pattern = Pattern.compile(identifireRegx);
        System.out.println("Compiled Sucessfully");
        Matcher m = pattern.matcher(id3);
        boolean bol = m.matches();
        System.out.println("String abc: " + bol);
        
        Tokenizer t = new Tokenizer();
        String code0 = "int x ;";
        String code1 = "int gcd (int u, int v)\n" +
                        "{" +
                        "if (v == 0) return u;\n" +
                        "else return gcd (v,u-u/v*v);\n" +
                        "}" +
                        "void main (void)\n" +
                        "{" +
                        "int x; int y;" +
                        "write_int(gcd(x,y));" +
                        "}";
        
        String code2 = "int Fibonacci(int x1)\n" +
                        "{\n" +
                        "int val;\n" +
                        "if (x1 == 1) val = 1;\n" +
                        "if (x1 == 2) val = 1;\n" +
                        "if (x1 > 2) val = Fibonacci(x1-2)+Fibonacci(x1-1);\n" +
                        "}";
        String ss = "int main(int x)\n" +
                    "{\n" +
                    "int x;\n" +
                    "}"; // ----- Any word that start and end with the same symbole will cause logic error.
        System.out.println("---------- Tokens(Valid & InValid) --------------");
        ArrayList result ;
        //result = t.tokenizedInput(code2);
        Parser p = new Parser();
        p.tokenizedInput(ss);
        p.programe();
//        for(int i = 0 ; i < result.size() ; i++)
//            System.out.println(result.get(i));
//        ArrayList test1 = new ArrayList();
//        
//        for(int i = 0 ; i < result.size() ; i++)
//            if(!result.get(i).toString().equals(" ")){
//                test1.add(result.get(i));
//            }
//      
//        Parser p = new Parser();
//        boolean test = p.variablDeclaration(test1);
//        System.out.println("Parser Test: " + test);
//        System.out.println("----------- Matching Tokens ----------------");
        
        
        
        
        
    }
    
}
