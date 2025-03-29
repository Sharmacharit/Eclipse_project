package in.sp.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import in.sp.beans.Student;

public class Main {
    public static void main(String[] args) {
        // Correct location of the config file
        String config_loc = "in/sp/resources/applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(config_loc);
        
        // Assuming the bean ID in applicationContext.xml is "Stdid", change if necessary
        Student std = (Student) context.getBean("Stdid");
        
        // Calling the display method of Student class
        std.display();
    }
}
