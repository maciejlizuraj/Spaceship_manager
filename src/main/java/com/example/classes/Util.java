package com.example.classes;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * The Util class provides utility methods for various checks and file operations.
 */
public class Util {
    /**
     * Checks if the provided integer is positive.
     *
     * @param i the integer to check
     * @throws RuntimeException if the integer is not positive
     */
    public static void positiveIntCheck(int i) {
        if (i <= 0) throw new RuntimeException("Int has to be positive.");
    }

    /**
     * Checks if the provided integer is non-negative.
     *
     * @param i the integer to check
     * @throws RuntimeException if the integer is negative
     */
    public static void nonNegativeIntCheck(int i) {
        if (i < 0) throw new RuntimeException("Int has to be non negative.");
    }

    /**
     * Validates the provided string.
     *
     * @param str the string to validate
     * @throws RuntimeException if the string is null or empty
     */
    public static void validString(String str) {
        if (str == null) throw new RuntimeException("String can't be null");
        if (str.trim().isEmpty()) throw new RuntimeException("String can't be empty");
    }

    /**
     * Checks if the provided double value is non-negative.
     *
     * @param d the double value to check
     * @throws RuntimeException if the double value is negative
     */
    public static void nonNegativeDoubleCheck(double d) {
        if (d < 0) throw new RuntimeException("Double has to be non negative.");
    }

    /**
     * Saves extents and registered owners to files.
     *
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Reads extents and registered owners from files.
     *
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
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
