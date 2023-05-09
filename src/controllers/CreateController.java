package controllers;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTable;

import models.CreateModel;
import models.Database;
import models.Employee;
import views.CreateFrame;
import views.MainFrame;

public class CreateController {
    CreateFrame createFrame;
    MainFrame mainFrame;
    CreateModel createModel;

    public CreateController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.createFrame = new CreateFrame(this.mainFrame);
        this.createModel = new CreateModel();
        this.handleEvents();
    }
    public CreateFrame getCreateFrame() {
        return createFrame;
    }
    private void handleEvents() {
        JButton operatorButton = this.createFrame.getAddButton();
        operatorButton.addActionListener(e -> {            
            startOperating();
        });
    }
    private void startOperating() {
        
        if(this.createModel.isAdding()) {
            startAdding();
        }else {
            startUpdate();
        }

    }
    private void startAdding() {
        System.out.println("hozzáadás...");
        String nameStr = createFrame.getNamePanel().getValue();
        String cityStr = createFrame.getCityPanel().getValue();
        String salaryStr = createFrame.getSalaryPanel().getValue();
        Double salary = Double.parseDouble(salaryStr);
        Employee emp = new Employee(nameStr, cityStr, salary);
        Integer id = new Database().insertEmployee(emp);

        Vector<String> empStr = new Vector<>();
        empStr.add(id.toString());
        empStr.add(nameStr);
        empStr.add(cityStr);
        empStr.add(salaryStr);
        this.mainFrame.getModel().addRow(empStr);
        this.createFrame.setVisible(false);
    }
    private void startUpdate() {
        System.out.println("Frissítés árnyékeljárás...");        

        String idStr = createFrame.getIdPanel().getValue();
        String nameStr = createFrame.getNamePanel().getValue();
        String cityStr = createFrame.getCityPanel().getValue();
        String salaryStr = createFrame.getSalaryPanel().getValue();        
        Integer id = Integer.parseInt(idStr);
        Double salary = Double.parseDouble(salaryStr);

        Employee emp = new Employee(id, nameStr, cityStr, salary);

        new Database().updateEmployee(emp);

        int selected = this.createModel.getSelected();
        JTable table = this.mainFrame.getTable();
        table.getModel().setValueAt(nameStr, selected, 1);
        table.getModel().setValueAt(cityStr, selected, 2);
        table.getModel().setValueAt(salaryStr, selected, 3);
        this.createModel.setAdding(true);
        this.createFrame.setTitle("");
        this.createFrame.getIdPanel().setValue("");
        this.createFrame.getNamePanel().setValue("");
        this.createFrame.getCityPanel().setValue("");
        this.createFrame.getSalaryPanel().setValue("");
        this.createFrame.setVisible(false);
        
    }
}
