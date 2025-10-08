import com.priyanka.assignment.employee.Developer;
import com.priyanka.assignment.employee.Employee;
import com.priyanka.assignment.employee.Manager;
import com.priyanka.assignment.utilities.EmployeeUtilities;

public class AssignmentMain {

	public static void main(String[] args) {

        EmployeeUtilities utils = new EmployeeUtilities();

		Employee emp = new Employee();
        Manager manager = new Manager();
        emp.setName("ABC");
        emp.setEmployeeId(5220365);
        emp.setSalary(90000);
        manager.setDepartment("Technology");
        utils.printEmployeeDetails(emp);

        Developer developer = new Developer();
        emp.setName("XYZ");
        emp.setEmployeeId(1234567);
        emp.setSalary(75000);
        developer.setProgrammingLanguage("Java");
        utils.printEmployeeDetails(emp);
	}
	
}
