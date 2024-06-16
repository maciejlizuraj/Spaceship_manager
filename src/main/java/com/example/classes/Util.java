package com.example.classes;

public class Util {

    public static void positiveIntCheck(int i) {
        if (i <= 0) throw new RuntimeException("Int has to be positive.");
    }

    public static void nonNegativeDoubleCheck(int i) {
        if (i < 0) throw new RuntimeException("Int has to be non negative.");
    }

    public static void validString(String str) {
        if (str==null) throw new RuntimeException("String can't be null");
        if (str.trim().isEmpty()) throw new RuntimeException("String can't be empty");
    }

    public static void nonNegativeDoubleCheck(double d) {
        if (d < 0) throw new RuntimeException("Double has to be non negative.");
    }
}
