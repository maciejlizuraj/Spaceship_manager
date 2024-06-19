package com.example.classes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * The ObjectPlus class is an abstract base class that implements
 * the Serializable interface. It provides functionality to keep
 * track of extents (collections of instances) for all subclasses.
 * <p>
 * This class maintains a static map of class types to sets of instances.
 * It also provides methods to write and read these extents to and from streams.
 */
public abstract class ObjectPlus implements Serializable {

    /**
     * A map that holds the extents of each class type.
     */
    private static Map<Class, Set<ObjectPlus>> allExtents = new Hashtable<>();

    /**
     * Constructs an instance of ObjectPlus and adds it to the extent
     * corresponding to its class type. All objects inheriting from ObjectPlus
     * should check validity of all parameters before calling the constructor.
     */
    public ObjectPlus() {
        Set<ObjectPlus> extent = null;
        Class theClass = this.getClass();

        if (allExtents.containsKey(theClass)) {
            extent = allExtents.get(theClass);
        } else {
            extent = new HashSet<>();
            allExtents.put(theClass, extent);
        }
        extent.add(this);
    }

    /**
     * Writes the extents of all class types to the given ObjectOutputStream.
     *
     * @param stream the output stream to write the extents to
     * @throws IOException if an I/O error occurs while writing the extents
     */
    public static void writeExtents(ObjectOutputStream stream) throws IOException {
        stream.writeObject(allExtents);
    }

    /**
     * Reads the extents of all class types from the given ObjectInputStream.
     *
     * @param stream the input stream to read the extents from
     * @throws IOException            if an I/O error occurs while reading the extents
     * @throws ClassNotFoundException if the class of a serialized object could not be found
     */
    public static void readExtents(ObjectInputStream stream) throws IOException,
            ClassNotFoundException {
        allExtents = (Hashtable) stream.readObject();
    }

    /**
     * Returns an iterable collection of instances of the given class type.
     *
     * @param <T>  the type of the class
     * @param type the Class object corresponding to the desired type
     * @return an iterable collection of instances of the given class type, or null if none exist
     * @throws ClassNotFoundException if the class type is not found in the extents
     */
    public static <T> Iterable<T> getExtent(Class<T> type) throws
            ClassNotFoundException {
        if (allExtents.containsKey(type)) {
            return (Iterable<T>) allExtents.get(type);
        } else return null;
    }
}
