package edu.java.cosc111.samples;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Russel
 */
public class JSONTest {
    private static class MyObject{
        String name;
        int age;

        public MyObject() {}
        
        public MyObject(String name, int age) {
            this.name = name;
            this.age = age;
        }

        
        @Override
        public String toString() {
            return "Name: " + name + ", Age: " + age;
        }
        
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        MyObject[] objs = {new MyObject("John", 10),new MyObject("James", 11),
                            new MyObject("Mary", 12),new MyObject("Jane", 13)};
        
        System.out.println("-Original Objects-");
        for(MyObject o:objs){
            System.out.println(o);
        }
        System.out.println("-As JSON String-");
        String jsonString = toJsonString(objs);
        System.out.println("JSON String: " + jsonString);
        
        System.out.println("-Generated Objects From JSON String-");
        MyObject[] genObjs = toMyObject(jsonString);
        for(MyObject o:genObjs){
            System.out.println(o);
        }

    }
    private static String toJsonString(MyObject ... objs) throws IOException{
        JsonFactory f = new JsonFactory();
        
        try(StringWriter out = new StringWriter()){
            try (JsonGenerator gen = f.createGenerator(out)) {

                gen.writeStartArray();
                for(MyObject obj:objs){            
                    gen.writeStartObject();
                    gen.writeFieldName("name");
                    gen.writeString(obj.name);
                    gen.writeFieldName("age");
                    gen.writeNumber(obj.age);
                    gen.writeEndObject();
                }            
                gen.writeEndArray();
            }
            return out.toString();
        }
    }
    private static MyObject[] toMyObject(String jsonString) throws IOException{
        JsonFactory f = new JsonFactory();
        JsonParser parser =  f.createParser(jsonString);
        
        List<MyObject> objs = new ArrayList<>();
        
        MyObject obj = null;
        
        while(parser.nextToken()!=null){
            JsonToken cur = parser.getCurrentToken();
            if(cur.equals(JsonToken.START_OBJECT)){
                obj = new MyObject();
            }else if(cur.equals(JsonToken.FIELD_NAME)){
                String fieldName = parser.getText();
                parser.nextToken();
                switch(fieldName){                    
                    case "name":                        
                        if(parser.getCurrentToken() == JsonToken.VALUE_STRING){
                            obj.name = parser.getValueAsString();
                        }else{
                        }
                        break;
                    case "age":
                        if(parser.getCurrentToken() == JsonToken.VALUE_NUMBER_INT){
                            obj.age = parser.getValueAsInt();
                        }
                        break; 
                }
            }else if(cur.equals(JsonToken.END_OBJECT)){
                objs.add(obj);
            }
        }
        MyObject[] toReturn = new MyObject[objs.size()];
        return objs.toArray(toReturn);
    }
}
