package serializable;

import java.io.Serializable;

public class Student implements Serializable {
    private int sno;
    private String sname;

    public Student(int sno, String sname) {
        this.sno = sno;
        this.sname = sname;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "学号:" + sno + ";姓名:" + sname;
    }
}
