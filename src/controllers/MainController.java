package controllers;

import javax.swing.JTable;

import models.Database;
import models.Employee;
import views.CreateFrame;
import views.MainFrame;

public class MainController {

    MainFrame mainFrame;
    Database database;
    CreateController createController;

    public MainController() {        
        this.mainFrame = new MainFrame();
        this.database = new Database();    
        this.createController = new CreateController(mainFrame);
        this.handleEvent();
    }
    private void handleEvent() {
        this.mainFrame.getAddButton().addActionListener(e -> {

            this.startAdd();
        });
        this.mainFrame.getDelButton().addActionListener(e -> {
            this.startDel();
        });
        this.mainFrame.getEditButton().addActionListener(e -> {
            this.startEdit();
        });
    }
    private void startAdd() {        
        CreateFrame createFrame = this.createController.getCreateFrame();
        createFrame.setVisible(true);
    }
    private void startDel() {
        JTable table = this.mainFrame.getTable();
        int row = table.getSelectedRow();

        String value = (String) table.getModel().getValueAt(row, 0);
        int id = Integer.parseInt(value);
        this.mainFrame.getModel().removeRow(row);
        this.database.deleteEmployee(id);        
    }
    private void startEdit() {
        this.createController.createModel.setAdding(false);

        JTable table = this.mainFrame.getTable();
        int row = table.getSelectedRow();

        //TODO: Státusz sorba vagy párbeszédablakba,
        //írjuk ki, hogy nincs kijelölve semmi
        if(row == -1 ) return;

        String idStr = (String) table.getModel().getValueAt(row, 0);
        String nameStr = (String) table.getModel().getValueAt(row, 1);
        String cityStr = (String) table.getModel().getValueAt(row, 2);
        String salaryStr = (String) table.getModel().getValueAt(row, 3);
        Employee emp = new Employee(
            Integer.parseInt(idStr), 
            nameStr, 
            cityStr, 
            Double.parseDouble(salaryStr)
            );
        CreateFrame createFrame = this.createController.getCreateFrame();
        createFrame.setTitle("Szerkesztés");
        createFrame.setEmployee(emp);
        createFrame.setVisible(true);
    }
}
