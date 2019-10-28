package serializable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer extends Thread {
    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(9999);
            Socket s = ss.accept();
            ObjectOutputStream ops = new ObjectOutputStream(s.getOutputStream());
            Student stu = new Student(1, "赵本山");
            ops.writeObject(stu);
            ops.close();
            s.close();
            ss.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MyServer().start();
        new MyClient().start();
    }
}
