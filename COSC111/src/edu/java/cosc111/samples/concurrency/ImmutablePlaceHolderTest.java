/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.java.cosc111.samples.concurrency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author russel
 */
public class ImmutablePlaceHolderTest {
    public static void main(String[] args) {
        List<String> lst = new ArrayList<>();
        
        lst.add("John");
        lst.add("Mark");
        lst.add("Anna");
        lst.add("Jane");
        lst.add("Mark");
        
        System.out.println(lst);
        
        Set<String> set = new TreeSet<>();
        
        set.add("Mark");
        set.add("Anna");
        set.add("Jane");
        set.add("Mark");
        
        System.out.println(set);
        
        Map<String,List<String>> crush = new HashMap<>();

        crush.put("Mark", new ArrayList<>());
        crush.get("Mark").add("Kate");
        crush.get("Mark").add("Paula");
        crush.get("Mark").add("Ellen");
        crush.get("Mark").add("Jennifer");
        
        crush.put("Shanna", new ArrayList<>());
        crush.get("Shanna").add("Carl");
        crush.put("Paulino", new ArrayList<>());
        crush.get("Paulino").add("Paulina");
        
        System.out.println(crush);
        
    }
    
}
