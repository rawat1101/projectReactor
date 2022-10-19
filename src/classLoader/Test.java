package classLoader;

public class Test {
    private Test() {
    }

    public static final Test t = new Test();

    public static Test getObj() {
        return t;
    }

    public void show() {
        System.out.println("Hello World!");
    }
}