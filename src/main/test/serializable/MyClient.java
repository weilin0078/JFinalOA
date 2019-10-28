package serializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MyClient extends Thread{
    @Override
    public void run() {
        try {
            Socket s = new Socket("localhost", 9999);
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Student stu = (Student) ois.readObject();
            System.out.println("客户端程序收到服务器端程序传输过来的学生对象>> " + stu);
            ois.close();
            s.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
