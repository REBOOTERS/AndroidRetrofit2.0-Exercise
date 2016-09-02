package com.example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public class MainClass {
    public static void main(String args[]) {
        try {
//            reflectTest("java.lang.String");
            reflectTest("com.example.TestClass");
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    /**
     * 反射
     */
    private static void reflectTest(String className) throws Exception {
        //获取类
        Class c = Class.forName(className);

        TestClass tc= (TestClass) c.newInstance();


        // 获取所有的属性
        Field[] fields = c.getDeclaredFields();

        StringBuffer sb = new StringBuffer();
        sb.append(Modifier.toString(c.getModifiers()) + " class " + c.getSimpleName() + "{\n");
        // 遍历每一个属性
        for (Field field : fields) {
            sb.append("\t");// 空格
            sb.append(Modifier.toString(field.getModifiers()) + " ");// 获得属性的修饰符，例如public，static等等
            sb.append(field.getType().getSimpleName() + " ");// 属性的类型的名字
            sb.append(field.getName() + ";\n");// 属性的名字+回车
        }

        sb.append("}\n");

        for (Field field : fields) {
            if(field.isAnnotationPresent(BindPort.class)){
                BindPort port = field.getAnnotation(BindPort.class);
                field.setAccessible(true);
                field.set(tc,port.value());
            }

            if (field.isAnnotationPresent(BindAddress.class)) {
                BindAddress address = field.getAnnotation(BindAddress.class);
                field.set(tc,address.value());
            }

        }

        tc.printInfo();
//


//        System.out.println(sb);


        // 获取所有的方法
        Method[] ms = c.getDeclaredMethods();

        //遍历输出所有方法
        for (Method method : ms) {
            //获取方法所有参数
            Parameter[] parameters = method.getParameters();
            String params = "";

            if (parameters.length > 0) {
                StringBuffer stringBuffer = new StringBuffer();
                for (Parameter parameter : parameters) {
                    stringBuffer.append(parameter.getType().getSimpleName() + " " + parameter.getName() + ",");
                }
                //去掉最后一个逗号
                params = stringBuffer.substring(0, stringBuffer.length() - 1);
            }

//            System.err.println(Modifier.toString(method.getModifiers())
//                    + " " + method.getReturnType().getSimpleName()
//                    + " " + method.getName()
//                    + " (" + params
//                    + ")");
        }

        // 获取所有的Annotation
        Annotation[] annotations=c.getAnnotations();

        for (Annotation annotation : annotations) {
            System.out.println("annotations " + annotation.annotationType());
        }
    }
}
