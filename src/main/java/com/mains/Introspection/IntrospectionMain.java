package com.mains.Introspection;
import java.lang.reflect.Method;

public class IntrospectionMain {
    public static void main(String[] args) throws Exception {
        // Get the Class object for the class containing the method
        Class<?> clazz = MyIntrospect.class;

        // Get the Method object using introspection
        Method method = clazz.getDeclaredMethod("printMessage", String.class, int.class);

        // Create an instance (or use null for static methods)
        MyIntrospect instance = new MyIntrospect();

        // Invoke the method
        method.invoke(instance, "Number is = ", 20);
    }
}
