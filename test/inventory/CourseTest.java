package inventory;

import inventory.equipments.Smartphone;
import org.junit.jupiter.api.Test;
import users.Student;
import users.Teacher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    @Test
    void createCourse(){
        ArrayList<Student> students = new ArrayList();
        students.add(new Student("1234567890","Jhon","Doe","11 avenue du test","0123648723","email@doie.fr",Student.Grade._2A));
        ArrayList<Equipment> equipments = new ArrayList<>();
        equipments.add(new Smartphone("phone1","Apple", new School("Ensiie"),Calendar.getInstance().getTime(),345.08,Smartphone.PHONE_OS.IOS, 1920*1080));
        Course c = new Course("123455","Système d'exploitation",students,new Teacher("01234568547","Joe","Average","2 rue du val ","0177339623","average.joe@joe.fr"),equipments);
        assertNotEquals(null,c);
    }
}