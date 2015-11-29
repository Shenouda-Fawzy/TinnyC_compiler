/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.pckg;

/**
 *
 * @author Shenouda
 */
public class Token {
    String token;
    String value;

    public Token(String token, String value) {
        this.token = token;
        this.value = value;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getToken() {
        return token;
    }

    public String getValue() {
        return value;
    }
    
    
}
