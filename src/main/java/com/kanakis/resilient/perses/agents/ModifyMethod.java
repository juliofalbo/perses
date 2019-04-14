package com.kanakis.resilient.perses.agents;

import javassist.ByteArrayClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

public class ModifyMethod {

    /**
     * Creates a new ModifyMethodTest
     *
     * @param className   The internal form class name to modify
     * @param classLoader The intrumentation provided classloader
     * @param byteCode    The pre-transform byte code
     * @param properties  The transformation metadata
     * @return the modified byte code if successful, otherwise returns the original unmodified byte code
     */
    public static byte[] instrument(String className,
                                    ClassLoader classLoader,
                                    byte[] byteCode,
                                    TransformProperties properties) {
        String binName = className.replace('/', '.');
        try {
            ClassPool cPool = new ClassPool(true);
            cPool.appendClassPath(new LoaderClassPath(classLoader));
            cPool.appendClassPath(new ByteArrayClassPath(binName, byteCode));
            CtClass ctClazz = cPool.get(binName);
            int modifies = 0;
            for (CtMethod method : ctClazz.getDeclaredMethods()) {
                if (method.getName().equals(properties.getMethodName())) {
                    ctClazz.removeMethod(method);
                    CtMethod modifiedMethod = properties.getMode().generateCode(method, properties);
                    ctClazz.addMethod(modifiedMethod);
                    modifies++;
                }
            }

            System.out.println("[ModifyMethodTest] Intrumented [" + modifies + "] methods");
            return ctClazz.toBytecode();
        } catch (Exception ex) {
            System.err.println("Failed to compile retransform class [" + binName + "] Stack trace follows...");
            ex.printStackTrace(System.err);
            return byteCode;
        }
    }
}
