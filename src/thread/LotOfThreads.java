package thread;

public class LotOfThreads {
    public static void main(String[] args) throws InterruptedException {
        Data data = new Data();
        Thread sender = new Thread(new Sender(data));
        Thread receiver = new Thread(new Receiver(data));

        sender.start();
        receiver.start();
    }
    public static void main2(String[] args) throws InterruptedException {
        int max =100;
        Thread th =null;
        for (int i = 0; i < max; i++) {
            th = new Thread(LotOfThreads::doWork,i+1+"");
            th.start();
        }
        System.out.println("started");
        th.join();
        System.out.println("===== "+th.getName());
        System.out.println("done");
    }

    private static void doWork() {
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
