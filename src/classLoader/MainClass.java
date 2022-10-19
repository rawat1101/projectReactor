package classLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainClass {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException,
            NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        Test test = Test.getObj();
        CustomClassLoaderDemo loader = new CustomClassLoaderDemo();
        Class<?> c = loader.findClass("classLoader.Test");
        Object ob = c.newInstance();
        Method md = c.getMethod("show");
        md.invoke(ob);
    }
}