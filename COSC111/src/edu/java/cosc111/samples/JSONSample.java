package edu.java.cosc111.samples;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;
import org.json.JSONWriter;
/**
 *
 * @author Acer
 */
public class JSONSample {

    public static class Person{
        private String name;
        private int age;

        public Person() {}

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Person[] persons = {new Person("John", 10), new Person("James", 11),
            new Person("Mary", 12), new Person("Jane", 13)};

        JSONArray ja = new JSONArray(persons);
        
        try(Writer  w = Files.newBufferedWriter(Paths.get("F:\\sample.json"))){
            ja.write(w);
        }
        
        try(Reader  r = Files.newBufferedReader(Paths.get("F:\\sample.json"))){
            ja = new JSONArray(new JSONTokener(r));
            persons = new Person[ja.length()];
            
            
        }
    }

}
