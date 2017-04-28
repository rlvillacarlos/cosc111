/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.java.cosc111.samples.concurrency;

/**
 *
 * @author russel
 */
public class ImmutablePlaceHolder<T> {
    private final T value;

    public ImmutablePlaceHolder(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
    
    public ImmutablePlaceHolder<T> setValue(T value) {
        return new ImmutablePlaceHolder<T>(value);
    }
    
    
    
}
