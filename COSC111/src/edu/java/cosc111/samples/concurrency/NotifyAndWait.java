package edu.java.cosc111.samples.concurrency;

/**
 *
 * @author RLVillacarlos
 */
public class NotifyAndWait {
    private static class Message{
        private String msg;
        private volatile boolean readyToAnnounce;

        public Message() {
            readyToAnnounce = false;
        }

        public Message(String msg) {
            this.msg = msg;
            this.readyToAnnounce = true;
        }
        
        public void setMessage(String msg){
            synchronized(this){
                this.msg = msg;
                this.readyToAnnounce = true;
                this.notifyAll();
            }
        }
        
        public synchronized void announce(){
            while(!this.readyToAnnounce){
                System.out.println("Waiting for announcement...");

                try {
                    this.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            
            System.out.println("Announcement: " + msg);
        }
        
    }
    
    private static class MessageSetter implements Runnable{
        private Message msg;
        private String content;

        public MessageSetter(Message msg,String content) {
            this.msg = msg;
            this.content = content;
        }
        
        
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                msg.setMessage(content);
            } catch (InterruptedException ex) {}
        }
        
    }
    
     private static class MessageAnnouncer implements Runnable{
        private Message msg;

        public MessageAnnouncer(Message msg) {
            this.msg = msg;
        }
        
        
        @Override
        public void run() {
            msg.announce();
        }
        
    }
    
    public static void main(String[] args) {
        Message msg = new Message();
        
        new Thread(new MessageSetter(msg,"Hello World")).start();
        new Thread(new MessageAnnouncer(msg)).start();
        new Thread(new MessageAnnouncer(msg)).start();
        
    }
}
