import java.sql.*;

//this class will communicate with DB
public class StudentRepository {

    // 4- Create connection with DB
    private Connection con;
    private Statement stmnt;
    private PreparedStatement prs;

    //5 - method to create connection to db
    private void getConnection(){
        try {
            this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                    "dev_user", "password");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //6-method to create statement
    private void getStatement(){
        try {
            this.stmnt = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //7- method to create prepared statement
    private void getPreparedStatement(String query){
        try {
            this.prs = con.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //8-method to create a table
    public void createTable(){
        getConnection(); //create connection
        getStatement(); //
        try {
            stmnt.execute("CREATE table IF NOT EXISTS t_student (id SERIAL, name VARCHAR(50), lastname VARCHAR(50), city VARCHAR(30), age int)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                stmnt.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    //11 -method to save student obj into DB
    public void save(Student newStudent) {
        getConnection();
        String query = "INSERT INTO t_student (name, lastname, city, age) VALUES (?, ?, ?, ?)";
        getPreparedStatement(query);
        try {
            prs.setString(1, newStudent.getName());
            prs.setString(2, newStudent.getLastname());
            prs.setString(3, newStudent.getCity());
            prs.setInt(4, newStudent.getAge());
            prs.executeUpdate();
            System.out.println("Student registered successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                prs.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //13-method to get all students from DB
    public void findAll() {
        getConnection();
        String query = "SELECT * FROM t_student";
        getStatement();
        try {
            ResultSet rs= stmnt.executeQuery(query);
            while (rs.next()){
                System.out.print("id-"+ rs.getString("id"));
                System.out.print(" Name-"+ rs.getString("name"));
                System.out.print(" Last Name-"+ rs.getString("lastname"));
                System.out.print(" City-"+ rs.getString("city"));
                System.out.print(" Age-"+ rs.getString("age"));
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                stmnt.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //15- method to delete student from database/table
    public void delete(int id) {
        getConnection();
        String query ="DELETE FROM t_student WHERE id = ?";
        getPreparedStatement(query);
        try {
            prs.setInt(1, id);
            int deleted = prs.executeUpdate(); //returns row affected row counts
            if(deleted>0){
                System.out.println("Student with id: "+id+" deleted successfully");
            }else{
                System.out.println("Student with id: "+id+" not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                prs.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }

    //17-method to findStudent by id
    public Student findStudentById(int id) {
        Student student = null;
        getConnection();
        String query = "SELECT * FROM t_student WHERE id= ?";
        getPreparedStatement(query);
        try {
            prs.setInt(1, id);
            ResultSet rs = prs.executeQuery(); //return Result set
            while (rs.next()){
                student = new Student(); //new Student obj to get data from result set
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setLastname(rs.getString("lastname"));
                student.setCity(rs.getString("city"));
                student.setAge(rs.getInt("age"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                prs.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return student;
    }

    //19- update student method
    public void update(Student student) {
        getConnection();
        String sql = "UPDATE t_student SET name=?, lastname=?, city=?, age=? WHERE id=?";
        getPreparedStatement(sql);
        try {
            prs.setString(1, student.getName());
            prs.setString(2, student.getLastname());
            prs.setString(3, student.getCity());
            prs.setInt(4, student.getAge());
            prs.setInt(5, student.getId());
            int updated = prs.executeUpdate();
            if(updated>0){
                System.out.println("Student updated successfully");
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                prs.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
