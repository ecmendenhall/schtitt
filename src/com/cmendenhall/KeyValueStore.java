package com.cmendenhall;

import java.io.*;
import java.util.HashMap;
import java.util.HashMap;

public class KeyValueStore {
    private static final KeyValueStore instance = new KeyValueStore();
    private static HashMap<String, String> values;

    private KeyValueStore() {
        values = new HashMap<String, String>();
    }

    public static KeyValueStore getInstance() {
        return instance;
    }

    public static void put(String key, String value) {
        values.put(key, value);
    }

    public static String get(String key) {
        return values.get(key);
    }

    public static void save() {
        try{
            OutputStream file = new FileOutputStream("kvstore.ser");
            OutputStream bufferedStream = new BufferedOutputStream(file);
            ObjectOutput objectOutput = new ObjectOutputStream(bufferedStream);
            try{
                objectOutput.writeObject(values);
            }
            finally{
                objectOutput.close();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void load() {
        try{
            InputStream file = new FileInputStream("kvstore.ser");
            InputStream inputBuffer = new BufferedInputStream(file);
            ObjectInput objectInput = new ObjectInputStream (inputBuffer);
            try{
                HashMap<String, String> loadedValues = (HashMap<String, String>) objectInput.readObject();
                values = loadedValues;
            }
            finally{
                objectInput.close();
            }
        } catch (Exception e) {
            return;
        }
    }

}
