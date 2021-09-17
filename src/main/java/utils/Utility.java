/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Properties;
import java.util.Set;
import com.google.gson.*;
import java.io.UnsupportedEncodingException;


public class Utility {
    private static Gson gson = new GsonBuilder().create();
    
    public static void printAllProperties() {
            Properties prop = System.getProperties();
            Set<Object> keySet = prop.keySet();
            for (Object obj : keySet) {
                    System.out.println("System Property: {" 
                                    + obj.toString() + "," 
                                    + System.getProperty(obj.toString()) + "}");
            }
    }
    
//    public static RenameMeDTO json2DTO(String json) throws UnsupportedEncodingException{
//            return gson.fromJson(new String(json.getBytes("UTF8")), RenameMeDTO.class);
//    }
//
//    public static String DTO2json(RenameMeDTO rmDTO){
//        return gson.toJson(rmDTO, RenameMeDTO.class);
//    }
//

}
