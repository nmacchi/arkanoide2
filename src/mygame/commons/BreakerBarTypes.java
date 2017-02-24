/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.commons;

/**
 *
 * @author nicolas
 */
public enum  BreakerBarTypes {
    
    ARKANOID(0, "ARKANOID"),
    SPACESHIP(1, "SPACESHIP");
    
    private final int codigo; 
    private final String name;
    
    BreakerBarTypes(int codigo, String name){
        this.codigo = codigo;
        this.name = name;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getName() {
        return name;
    }
    
    
    
}
