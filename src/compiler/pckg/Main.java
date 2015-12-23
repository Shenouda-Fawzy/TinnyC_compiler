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
        CompilerUI comp = new CompilerUI();
        comp.setVisible(true);

  
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
        String code3 = "void main(void)\n" +
                        "{\n" +
                        "int x;\n" +
                        "int y;\n" +
                        "int result;\n" +
                        "x = 5;\n" +
                        "y = 2;\n" +
                        "result = Fibonacci(x*y);\n" +
                        "output(result);\n" +
                        "}";
//        //System.out.println("---------- Tokens(Valid & InValid) --------------");
//        Token token = new Token(code3, code3);
//        ArrayList result = new ArrayList<Token>() ;
//        
//        result = t.tokenizedInput(code2);
//        System.out.print(t.toString());
//        Parser p = new Parser();
//        p.tokenizedInput(code2);
//        p.programe();
//        System.out.println(p.isParsed());
        
    }
    
}
