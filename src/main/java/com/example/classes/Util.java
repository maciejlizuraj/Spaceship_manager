package com.example.classes;

import java.io.*;
import java.util.Hashtable;
import java.util.Set;

public class Util {

    public static void positiveIntCheck(int i) {
        if (i <= 0) throw new RuntimeException("Int has to be positive.");
    }

    public static void nonNegativeIntCheck(int i) {
        if (i < 0) throw new RuntimeException("Int has to be non negative.");
    }

    public static void validString(String str) {
        if (str==null) throw new RuntimeException("String can't be null");
        if (str.trim().isEmpty()) throw new RuntimeException("String can't be empty");
    }

    public static void nonNegativeDoubleCheck(double d) {
        if (d < 0) throw new RuntimeException("Double has to be non negative.");
    }

    public static void saveToFile() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("extents.dat");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ObjectPlus.writeExtents(objectOutputStream);
        objectOutputStream.close();
        fileOutputStream.close();

        FileOutputStream fileOutputStream1 = new FileOutputStream("owners.dat");
        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(fileOutputStream1);
        objectOutputStream1.writeObject(Cargo.getRegisteredOwners());
        objectOutputStream1.close();
        fileOutputStream1.close();
    }

    public static void readFromFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("extents.dat");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ObjectPlus.readExtents(objectInputStream);
        objectInputStream.close();
        fileInputStream.close();

        FileInputStream fileInputStream1 = new FileInputStream("owners.dat");
        ObjectInputStream objectInputStream1 = new ObjectInputStream(fileInputStream);
        Cargo.setRegisteredOwners((Set<String>) objectInputStream1.readObject());
        objectInputStream1.close();
        fileInputStream1.close();
    }
}
