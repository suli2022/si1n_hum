package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {

    public Database() {
        // Employee emp = new Employee(
        //     1, 
        //     "Marduk Árpád", 
        //     "Miskolc", 
        //     395.
        // );
        //this.insertEmployee(emp);
        ArrayList<Employee> empList = this.getEmployees();
        empList.forEach( (employee) -> {
            System.out.println(employee.name);
        });
        
    }

    public Connection connectDb() {
        Connection con = null;
        try {
            con = tryConnectDb();
        } catch (ClassNotFoundException e) {
            System.err.println("Hiba! A driver nem található!");
            System.err.println(e.getMessage());
        } catch(SQLException e) {
            System.err.println("Hiba! Az SQL utasítás végrehajtása sikertelen!");
            System.err.println(e.getMessage());
        }
        return con;
    }
    public Connection tryConnectDb() 
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        String url = "jdbc:mariadb://localhost:3306/hum";
        Class.forName("org.mariadb.jdbc.Driver");
        con = DriverManager.getConnection(url, "hum", "titok");
        System.out.println("Kapcsolódva");
        return con;
    }
    public void closeDb(Connection con) throws SQLException {
        con.close();
    }

    public int insertEmployee(Employee emp) {
        int id = 0;
        try {
            id = tryInsertEmployee(emp);
        } catch (SQLException e) {
            System.err.println("Hiba! A rekord beszúrása sikertelen!");
            System.err.println(e.getMessage());
        }
        return id;
    }
    private int tryInsertEmployee(Employee emp) 
            throws SQLException {
        Connection con = this.connectDb();
        String sql = "insert into employees" +
        "(name, city, salary) values "+
        "(?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(sql, 
            Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, emp.name);
        pstmt.setString(2, emp.city);
        pstmt.setDouble(3, emp.salary);
        System.out.println(pstmt.toString());
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        int id=0;
        if(rs.next()) {
            id = rs.getInt(1);
        }
        this.closeDb(con);
        return id;
    }

    public ArrayList<Employee> getEmployees() {
        ArrayList<Employee> empList = null;
        try {
            empList = tryGetEmployees();
        } catch (SQLException e) {
            System.err.println("Hiba! A rekordok lekérdezése sikertelen!");
        }
        return empList;
    }
    public ArrayList<Employee> tryGetEmployees() 
            throws SQLException {
        ArrayList<Employee> empList = new ArrayList<>();

        Connection con = connectDb();

        String sql = "select * from employees";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        empList = convertResToList(rs);
        closeDb(con);
        return empList;
    }

    public ArrayList<Employee> convertResToList(ResultSet rs) 
            throws SQLException {
        ArrayList<Employee> empList = new ArrayList<>();
        while(rs.next()) {
            Employee emp = new Employee(
                rs.getInt("id"), 
                rs.getString("name"),
                rs.getString("city"),
                rs.getDouble("salary")
            );
            empList.add(emp);
        }
        return empList;
    }


    public void deleteEmployee(int id) {
        try {
            tryDeleteEmployee(id);
        } catch (SQLException e) {
            System.err.println("Hiba! A rekord törlése során!");
        }
    }
    public void tryDeleteEmployee(int id) 
            throws SQLException  {
        Connection con = connectDb();
        String sql = "delete from employees where id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.execute();
    }

    public void updateEmployee(Employee emp) {
        try {
            tryUpdateEmployee(emp);
        } catch (SQLException e) {
            System.err.println("Hiba! a rekord frissítése sikertelen!");
            System.err.println(e.getMessage());
        }
    }
    public void tryUpdateEmployee(Employee emp) 
            throws SQLException {
        Connection con = connectDb();
        String sql = "update employees set " + 
                    "name=?, city=?, salary=? " +
                    "where id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, emp.name);
        pstmt.setString(2, emp.city);
        pstmt.setDouble(3, emp.salary);
        pstmt.setInt(4, emp.id);
        pstmt.execute();
        this.closeDb(con);
    }
}
