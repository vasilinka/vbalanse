package by.vbalanse.convert;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ValueConstants;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created by Vasilina on 19.03.2015.
 */
@Component
public class TransfererImpl implements Transferer {

  public WeakHashMap<Class, List<String>> getMethodsMap = new WeakHashMap<>();
  public WeakHashMap<Class, Map<String, Method>> setMethodsMap = new WeakHashMap<>();

  WeakHashMap<Class, Map<String, Field>> classFields = new WeakHashMap<>();


  public <From, To> List<To> transferList(Class from, Class to, List<From> objects) {
    if (objects == null || objects.isEmpty()) {
      return Collections.emptyList();
    }
    List<To> resultList = new ArrayList<>();
    for (From object : objects) {
      resultList.add((To) transfer(from, to, object));
    }
    return resultList;
  }

  public <From, To> To transfer(Class from, Class to, From obj) {
    if (obj == null) {
      return null;
    }
    return (To) transferPrivate(from, to, obj);
  }

  //todo: refactoring to good transferring!!!!, patterns
  public Object transferPrivate(Class from, Class to, Object fromObj) {
//    Field[] fields = to.getDeclaredFields();
    Object toObj = null;
    try {
      toObj = to.newInstance();
      BeanInfo toBeanInfo = Introspector.getBeanInfo(to);
      BeanInfo fromBeanInfo = Introspector.getBeanInfo(from);
      PropertyDescriptor[] propertyDescriptors = toBeanInfo.getPropertyDescriptors();
      HashMap<String, PropertyDescriptor> toDescriptorsMap = new HashMap<>();
      for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
        toDescriptorsMap.put(propertyDescriptor.getName(), propertyDescriptor);
      }
      PropertyDescriptor[] fromPropertyDescriptors = fromBeanInfo.getPropertyDescriptors();
      HashMap<String, PropertyDescriptor> fromDescriptorsMap = new HashMap<>();
      for (PropertyDescriptor propertyDescriptor : fromPropertyDescriptors) {
        fromDescriptorsMap.put(propertyDescriptor.getName(), propertyDescriptor);
      }
      toBeanInfo.getPropertyDescriptors()[0].getReadMethod();


      Map<String, Field> fields = null;
      if (classFields.get(to) != null) {
        fields = classFields.get(to);
      } else {
        Field[] declaredFields = to.getDeclaredFields();
        fields = new HashMap<>();
        for (Field field : declaredFields) {
          fields.put(field.getName(), field);
        }
        classFields.put(to, fields);
        for (Class super_class = to.getSuperclass(); super_class != Object.class; super_class = super_class.getSuperclass()) {
          declaredFields = super_class.getDeclaredFields();
          for (Field field : declaredFields) {
            fields.put(field.getName(), field);
          }
        }
      }
      for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
        Class<?> fieldType = propertyDescriptor.getPropertyType();
        String fieldName = propertyDescriptor.getName();
        Field field = null;
        field = fields.get(propertyDescriptor.getName());
        if (fieldName.equals("class")) {
          continue;
        }
        //boolean declaredAnnotations = propertyDescriptor.get();
        //System.out.println(to + " "+ fieldName + " " + " no covert " + declaredAnnotations);
        if (field.isAnnotationPresent(NoConvert.class)) {
          continue;
        }
        Method writeMethod = propertyDescriptor.getWriteMethod();
        Method readMethod = propertyDescriptor.getReadMethod();
        Class<?> returnType = readMethod.getReturnType();
        Object res = null;
        Convert annotation = field.getAnnotation(Convert.class);

        PropertyDescriptor propertyDescriptor1 = fromDescriptorsMap.get(fieldName);
        if (propertyDescriptor1 == null ) {
          //todo here review
          continue;
        }
        if (annotation != null) {
          fieldName = annotation.name();
          String objectName = annotation.object();
          String[] names = annotation.names();
          if (!objectName.equals(ValueConstants.DEFAULT_NONE)) {
            //obj is child property of from object
            Object obj = fromDescriptorsMap.get(objectName).getReadMethod().invoke(fromObj, null);

            if (names.length > 0) {
              String result = "";
              for (String name : names) {
                if (!name.trim().isEmpty()) {
                  String fieldNameUpper = name.substring(0, 1).toUpperCase() + name.substring(1);
                  result += obj.getClass().getMethod("get" + fieldNameUpper).invoke(obj);
                } else {
                  result += name;
                }
              }
              res = result;
            } else {
              if (obj != null) {
                String fieldNameUpper = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                res = obj.getClass().getMethod("get" + fieldNameUpper).invoke(obj);
              }
            }
          } else if (fieldType.equals(List.class)) {
            //todo else if very difficult, dublicate
            List obj = transferList(fromObj, fromDescriptorsMap, field, fieldName);
            res = obj;
          } else {
            if (names.length > 0) {
              String result = "";
              for (String name : names) {
                if (!name.trim().isEmpty()) {
                  String fieldNameUpper = name.substring(0, 1).toUpperCase() + name.substring(1);
                  result += fromObj.getClass().getMethod("get" + fieldNameUpper).invoke(fromObj);
                } else {
                  result += name;
                }
              }
              res = result;
            } else {
              res = propertyDescriptor1.getReadMethod().invoke(fromObj, null);
            }
          }
        }
        if (annotation == null) {
          if (fieldType.equals(List.class)) {
            //todo else if very difficult, dublicate
            List obj = transferList(fromObj, fromDescriptorsMap, field, fieldName);
            res = obj;
          } else if (returnType == String.class || returnType.getSuperclass() == Number.class || returnType == Date.class) {
            System.out.println(fromDescriptorsMap == null ? "fieldname " + fieldName : "");
            res = propertyDescriptor1.getReadMethod().invoke(fromObj, null);
          } else {
            if (field == null) {
              String fieldNameUpper = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
              res = from.getMethod("get" + fieldNameUpper).invoke(fromObj);
            } else {
              Object obj = propertyDescriptor1.getReadMethod().invoke(fromObj, null);
              if (obj != null) {
                res = transferPrivate(obj.getClass(), returnType, obj);
              }
            }
          }
        }
        writeMethod.invoke(toObj, res);
      }

    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (java.lang.IllegalArgumentException e) {
      e.printStackTrace();

    }
     /*catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();*/ catch (IntrospectionException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    return toObj;

  }

  private List transferList(Object fromObj, HashMap<String, PropertyDescriptor> fromDescriptorsMap, Field field, String fieldName) throws IllegalAccessException, InvocationTargetException {
    List obj = new ArrayList();
    Object invoke = fromDescriptorsMap.get(fieldName).getReadMethod().invoke(fromObj, null);
    Collection<Object> fromList = (Collection) invoke;
    ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
    Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
    if (fromList != null) {
      for (Object element : fromList) {
        obj.add(transferPrivate(element.getClass(), (Class) fieldArgTypes[0], element));
      }
    }
    return obj;
  }

  public Object oldTransfer(Class from, Class to, Object fromObj) {
    Object toObj = null;
    try {
      toObj = to.newInstance();
      List<String> getMethods = getMethodsMap.get(to);
      Map<String, Method> setMethods = setMethodsMap.get(to);
      if (getMethods == null) {
        Method[] allMethods = to.getMethods();
        getMethods = new ArrayList<>();
        setMethods = new HashMap<>();
        for (Method method : allMethods) {
          String methodName = method.getName();
          if (methodName.startsWith("get") && !methodName.equals("getClass")) {
            getMethods.add(methodName);
          }
          if (methodName.startsWith("set")) {
            setMethods.put(methodName, method);
          }
        }
        getMethodsMap.put(to, getMethods);
        setMethodsMap.put(to, setMethods);
      }
      for (String getMethodName : getMethods) {
        Method getMethod = to.getMethod(getMethodName);
        String setMethodName = getMethodName.replace("get", "set");
        Method setMethod = setMethods.get(setMethodName);
        Class<?> returnType = getMethod.getReturnType();
        Method fromGetDeclaredMethod = from.getMethod(getMethodName, null);
        if (returnType == java.util.List.class) {
          ArrayList arr = new ArrayList();
          ParameterizedType parameterizedType = (ParameterizedType) setMethod.getGenericParameterTypes()[0];
          Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
          Object[] arrFrom = (Object[]) fromGetDeclaredMethod.getReturnType().getMethod("toArray").invoke(fromGetDeclaredMethod.invoke(fromObj));
          for (Object obj : arrFrom) {
            arr.add(transfer(obj.getClass(), (Class) fieldArgTypes[0], obj));
          }

          setMethod.invoke(toObj, arr);
        } else if (returnType == String.class || returnType.getSuperclass() == Number.class || returnType == Date.class) {
          setMethod.invoke(toObj, fromGetDeclaredMethod.invoke(fromObj));
        } else {
          setMethod.invoke(toObj, transfer(fromGetDeclaredMethod.getReturnType(), returnType, fromGetDeclaredMethod.invoke(fromObj)));
        }
      }
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return toObj;
  }

}