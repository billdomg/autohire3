package com.carrental.autohire.util;

import com.carrental.autohire.dto.CarRequestDto;
import java.beans.PropertyDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CarRequestValidator {

        public static boolean isValidCarRequestDto(CarRequestDto dto) throws IllegalAccessException {
            Field[] fields = dto.getClass().getDeclaredFields();
            try {
                BeanInfo bi = Introspector.getBeanInfo(dto.getClass());
                PropertyDescriptor[] propertyDescriptors = bi.getPropertyDescriptors();
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    Method readMethod = propertyDescriptor.getReadMethod();
                    String name = propertyDescriptor.getReadMethod().getName();
                    Object value = readMethod.invoke(dto);
                    System.out.println(name + " = " + value);
                }
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            for (Field field : fields) {
                field.setAccessible(true);

                
                // Check if field is manufacturer or model
                if (field.getName().equals("manufacturer") || field.getName().equals("model")) {
                    String value = (String) field.get(dto);
                    if (value == null || value.isEmpty()) {
                        return false;
                    }
                }

                // Check if field is year
                if (field.getName().equals("year")) {
                    int value = field.getInt(dto);
                    if (value == 0) {
                        return false;
                    }
                }
            }

            return true;
        }

}
