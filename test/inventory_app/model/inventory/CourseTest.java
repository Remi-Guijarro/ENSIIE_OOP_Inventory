package inventory_app.model.inventory;

import inventory_app.model.inventory.equipements.Smartphone;
import inventory_app.model.users.Student;
import inventory_app.model.users.Teacher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CourseTest {
    @Test
    void createCourse(){
        ArrayList<Student> students = new ArrayList();
        students.add(new Student("1234567890","Jhon","Doe","11 avenue du test","0123648723","email@doie.fr",Student.Grade._2A));
        ArrayList<inventory_app.model.inventory.Equipment> equipments = new ArrayList<>();
        equipments.add(new Smartphone("phone1","Apple", new inventory_app.model.inventory.School("Ensiie"),Calendar.getInstance().getTime(),345.08,Smartphone.PHONE_OS.IOS, 1920*1080));
        inventory_app.model.inventory.Course c = new inventory_app.model.inventory.Course("123455","Système d'exploitation",students,new Teacher("01234568547","Joe","Average","2 rue du val ","0177339623","average.joe@joe.fr"),equipments);
        assertNotEquals(null,c);
    }
}