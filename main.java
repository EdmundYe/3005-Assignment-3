import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class main{
    public static void main(String [] args){
        // database credentials
        String url = "jdbc:postgresql://localhost:5432/assignment3";
        String user = "postgres";
        String password = "postgres";

        /*
        Menu and user input
        1 : gets student data from database
        2 : prompts user to input data for new student
        3 : prompts user to update a student's email based on id
        4 : prompts user to delete student based on id
         */
        while (true){
            System.out.println("1. Get all students");
            System.out.println("2. Add a student");
            System.out.println("3. Update a student's email");
            System.out.println("4. Delete a student");
            System.out.println("5. Exit");
            System.out.println("Enter your choice: ");
            Scanner userChoice = new Scanner(System.in);
            int choice = userChoice.nextInt();
            main q = new main();
            switch (choice){
                case 1:
                    q.getAllStudents(url, user, password);
                    break;
                case 2:
                    System.out.println("Enter first name: ");
                    String first_name = userChoice.next();
                    System.out.println("Enter last name: ");
                    String last_name = userChoice.next();
                    System.out.println("Enter email: ");
                    String email = userChoice.next();
                    System.out.println("Enter enrollment date: ");
                    String enrollment_date = userChoice.next();
                    q.addStudent(url, user, password, first_name, last_name, email, enrollment_date);
                    break;
                case 3:
                    System.out.println("Enter student ID: ");
                    int student_id = userChoice.nextInt();
                    System.out.println("Enter new email: ");
                    String new_email = userChoice.next();
                    q.updateStudentEmail(url, user, password, student_id, new_email);
                    break;
                case 4:
                    System.out.println("Enter student ID: ");
                    int student_id1 = userChoice.nextInt();
                    q.deleteStudent(url, user, password, student_id1);
                    break;
                case 5:
                    userChoice.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    static void getAllStudents(String url, String user, String password){
        // connects to database and performs select operation
        // close connections at the end
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null){
                Statement statement = conn.createStatement();
                statement.executeQuery("SELECT * FROM students");
                ResultSet rs = statement.getResultSet();
                while (rs.next()){
                    System.out.println(rs.getInt("student_id") + " \t");
                    System.out.println(rs.getString("first_name") + " \t");
                    System.out.println(rs.getString("last_name") + " \t");
                    System.out.println(rs.getString("email") + " \t");
                    System.out.println(rs.getString("enrollment_date") + " \t");
                    System.out.println("");
                }
                rs.close();
                statement.close();
            }
            else {
                System.out.println("Failed to make connection!");
            }
            conn.close();
        }
        catch (Exception e){
        }
    }

    static void addStudent(String url, String user, String password, String first_name, String last_name, String email, String enrollment_date){
        // connects to database and performs insert operation using user input
        // close connections at the end
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null){
                Statement statement = conn.createStatement();
                String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES ('" + first_name + "', '" + last_name + "', '" + email + "', '" + enrollment_date + "')";
                System.out.println("New student added!");
                statement.executeQuery(query);
                statement.close();
            }
            else {
                System.out.println("Failed to make connection!");
            }
            conn.close();
        }
        catch (Exception e){
        }
    }

    static void updateStudentEmail(String url, String user, String password, int student_id, String new_email){
        // connect to database and perform update operation
        // close connection at the end
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null){
                Statement statement = conn.createStatement();
                String query = "UPDATE students SET email = '" + new_email + "' WHERE student_id = " + student_id;
                System.out.println("Email updated!");
                statement.executeQuery(query);
                statement.close();
            }
            else {
                System.out.println("Failed to make connection!");
            }
            conn.close();
        }
        catch (Exception e){
        }
    }

    static void deleteStudent(String url, String user, String password, int student_id){
        // connect to database and perform delete operation
        // close connection at the end
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null){
                Statement statement = conn.createStatement();
                String query = "DELETE FROM students WHERE student_id = " + student_id;
                System.out.println("Student deleted!");
                statement.executeQuery(query);
                statement.close();
            }
            else {
                System.out.println("Failed to make connection!");
            }
            conn.close();
        }
        catch (Exception e){
        }
    }
}
