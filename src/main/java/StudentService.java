import java.util.Scanner;

//to handle the business logic
public class StudentService {
    //to have access to the methods from repo
    StudentRepository repository = new StudentRepository();

    Scanner inp = new Scanner(System.in);

    //9-method to create table
    public void createTable(){
        repository.createTable();
    }

    //10-method to register student to DB
    public void saveStudent(){
        System.out.println("Name: ");
        String name = inp.nextLine();
        System.out.println("Last Name: ");
        String lastName = inp.nextLine();
        System.out.println("City: ");
        String city = inp.nextLine();
        System.out.println("Age: ");
        int age = inp.nextInt();
        inp.nextLine();

        //create student obj
        Student newStudent = new Student(name, lastName, city, age);
        repository.save(newStudent);
    }
    //12- method to list/display all students
    public void getAllStudents(){
        repository.findAll();
    }

    //14- method to delete Student by ID
    public void deleteStudent(int id){
        repository.delete(id);
    }

    //16 - method to find Student by id
    public Student getStudentById(int id){
        Student student = repository.findStudentById(id);
        return student;
    }

    //18-method to update student
    public void updateStudent(int id){
        //check if id exists in db
        Student student = getStudentById(id); //call the method we have created in step 16
        if(student==null){ //check if we have the student with id in our table
            System.out.println("Student with id: "+id+" not found");
        }else { //if we have student then we can update student by data which will be entered by user
            System.out.println("Name: ");
            String name = inp.nextLine();
            System.out.println("Last Name: ");
            String lastName = inp.nextLine();
            System.out.println("City: ");
            String city = inp.nextLine();
            System.out.println("Age: ");
            int age = inp.nextInt();
            inp.nextLine();//to call next enter/newline

            //update existing student data with the data from user input
            student.setName(name);
            student.setLastname(lastName);
            student.setAge(age);
            student.setCity(city);
            //student.setId(???);
            repository.update(student);
        }

    }

}
