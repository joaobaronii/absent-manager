import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private DefaultListModel<Subject> model = new DefaultListModel<>();
    JList<Subject> visualList;
    SubjectManager manager;

    public GUI() {
        JFrame absentFrame = new JFrame("Faltas!");
        absentFrame.setSize(600, 400);

        manager = new SubjectManager();

        visualList = new JList<>(model);
        JScrollPane scroll = new JScrollPane(visualList);

        JButton addSubject = new JButton("Add Subject");
        addSubject.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog("Subject Name:");
            if (nome != null && !nome.isBlank()) {
                try {
                    Subject newSubject = new Subject(nome);
                    model.addElement(newSubject);
                    manager.saveToDb(newSubject);
                } catch (RuntimeException ex) {
                    if (!model.isEmpty()) {
                        model.removeElementAt(model.size() - 1);
                    }
                    JOptionPane.showMessageDialog(absentFrame, "Failed to add Subject" + ex.getMessage());
                }
            }
        });

        JButton removeSubject = new JButton("Remove Subject");
        removeSubject.addActionListener(e -> {
            Subject selected = visualList.getSelectedValue();
            if (selected != null) {
                try {
                    model.removeElement(selected);
                    manager.deleteFromDB(selected);
                } catch (RuntimeException ex) {
                    model.addElement(selected);
                    JOptionPane.showMessageDialog(absentFrame, "Failed to remove Subject" + ex.getMessage());
                }
            } else
                JOptionPane.showMessageDialog(absentFrame, "No subject selected!");
        });

        JButton addAbsent = new JButton("Missed class");
        addAbsent.addActionListener(e -> {
            Subject selected = visualList.getSelectedValue();
            if (selected != null) {
                try {
                    selected.missedClass();
                    manager.saveToDb(selected);
                    visualList.repaint();
                } catch (RuntimeException ex) {
                    selected.excusedClass();
                    visualList.repaint();
                    JOptionPane.showMessageDialog(absentFrame, "Failed to update absences" + ex.getMessage());
                }
            } else
                JOptionPane.showMessageDialog(absentFrame, "No subject selected!");
        });

        JButton removeAbsent = new JButton("Remove absent");
        removeAbsent.addActionListener(e -> {
            Subject selected = visualList.getSelectedValue();
            if (selected != null) {
                try {
                    selected.excusedClass();
                    manager.saveToDb(selected);
                    visualList.repaint();
                } catch (RuntimeException ex) {
                    selected.excusedClass();
                    visualList.repaint();
                    JOptionPane.showMessageDialog(absentFrame, "Failed to update absences" + ex.getMessage());
                }
            } else
                JOptionPane.showMessageDialog(absentFrame, "No subject selected!");
        });

        JPanel absentPanel = new JPanel();
        absentFrame.getContentPane().add(scroll, BorderLayout.CENTER);
        absentPanel.add(addSubject);
        absentPanel.add(removeSubject);
        absentPanel.add(addAbsent);
        absentPanel.add(removeAbsent);
        absentFrame.getContentPane().add(absentPanel, BorderLayout.SOUTH);

        initializeSubjects();
        absentFrame.setVisible(true);
    }

    private void initializeSubjects() {
        for (Subject s : manager.loadSubjectsFromDB()) {
            model.addElement(s);
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}
