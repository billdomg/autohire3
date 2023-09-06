package com.mains.Reflection;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Properties;

import com.carrental.autohire.config.DatabaseConfig;


public class ReflectionMain {
    private static final String PROPERTY_FILE_PATH = "application.properties";
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,  InvocationTargetException {
        Boolean showStructure = true;
        String className = "";
        // Obtain the class object for a specific class
        Properties properties = new Properties();
        try (InputStream inputStream = DatabaseConfig.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_PATH)) {

            properties.load(inputStream);

            className = properties.getProperty("class.className");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Class<?> clazz = Class.forName(className);
        ClassInterface instance = (ClassInterface) clazz.getDeclaredConstructor().newInstance();
        System.out.println("Id = " + instance.getId() + " Name = " + instance.getName() + " Number = " + instance.getNumber());

        if (showStructure) {
            // Print the class name
            System.out.println("Class Name: " + clazz.getName());

            // Print the modifiers of the class
            int modifiers = clazz.getModifiers();
            System.out.println("Modifiers: " + Modifier.toString(modifiers));

            // Print the declared fields of the class
            System.out.println("Declared Fields:");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                System.out.println("  " + field.getType().getSimpleName() + " " + field.getName());
            }

            // Print the declared methods of the class
            System.out.println("Declared Methods:");
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                System.out.print("  " + method.getReturnType().getSimpleName() + " " + method.getName() + "(");
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    System.out.print(parameterTypes[i].getSimpleName());
                    if (i < parameterTypes.length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println(")");
            }
        }
    }
}

