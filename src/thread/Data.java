package thread;

public class Data {
    private String packet;

    // True if receiver should wait
    // False if sender should wait
    private boolean transfer = true;

    public synchronized String receive() {
        while (transfer) {
            try {
                System.out.println("before wait inside receive : " + Thread.currentThread());
                wait();
                System.out.println("after wait inside receive : " + Thread.currentThread());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread Interrupted");
            }
        }
        transfer = true;

        String returnPacket = packet;
        notifyAll();
        return returnPacket;
    }

    public synchronized void send(String packet) {
        while (!transfer) {
            try {
                System.out.println("before wait inside send : " + Thread.currentThread());
                wait();
                System.out.println("after wait inside send : " + Thread.currentThread());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread Interrupted");
            }
        }
        transfer = false;

        this.packet = packet;
        notifyAll();
    }
}