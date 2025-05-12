import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectManager {
    public SubjectManager() {
    }

    public List<Subject> loadSubjectsFromDB(){
        String sql = "SELECT subjectname, workload, absents, absentslimit FROM subjects";
        List<Subject> subjects = new ArrayList<Subject>();
        try(Connection con = DB.connect();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            String name;
            int workload;
            int absents;
            int absentsLimit;
            while (rs.next()) {
                name = rs.getString("subjectname");
                workload = rs.getInt("workload");
                absents = rs.getInt("absents");
                absentsLimit = rs.getInt("absentslimit");
                Subject s = new Subject(name, workload, absents, absentsLimit);
                subjects.add(s);
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Failed to load subjects: " + e.getMessage());
        }
        return subjects;
    }

    public void deleteFromDB(Subject subject) {
        String sql = "DELETE FROM subjects WHERE subjectname = ?";
        try (Connection con = DB.connect();
             PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, subject.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Failed to delete subject from database: " + e.getMessage());
        }
    }

    public void saveToDb(Subject subject) {
        String sql = "INSERT INTO subjects (subjectname, workload, absents, absentslimit) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (subjectname) DO UPDATE " +
                "SET workload = EXCLUDED.workload, absents = EXCLUDED.absents, absentslimit = EXCLUDED.absentslimit";
        try (Connection con = DB.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, subject.getName());
            ps.setInt(2, subject.getWorkload());
            ps.setInt(3, subject.getAbsents());
            ps.setInt(4, subject.getAbsentsLimit());
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Failed to save subject to database: " + e.getMessage());
        }
    }
}
