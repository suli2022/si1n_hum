package controllers;

import javax.swing.JTable;

import models.CreateModel;
import models.Database;
import views.CreateFrame;
import views.MainFrame;

public class MainController {

    MainFrame mainFrame;
    Database database;
    CreateModel createModel;
    public MainController() {        
        this.mainFrame = new MainFrame();
        this.database = new Database();
        this.createModel = new CreateModel();
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
        CreateController createController = new CreateController(mainFrame);
        CreateFrame createFrame = createController.getCreateFrame();
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
        this.createModel.setAdding(false);

        JTable table = this.mainFrame.getTable();
        int row = table.getSelectedRow();

        String idStr = (String) table.getModel().getValueAt(row, 0);
        String nameStr = (String) table.getModel().getValueAt(row, 1);
        String cityStr = (String) table.getModel().getValueAt(row, 2);
        String salaryStr = (String) table.getModel().getValueAt(row, 3);
        System.out.println("city: " +cityStr);


        CreateController createController = new CreateController(mainFrame);
        CreateFrame createFrame = createController.getCreateFrame();
        createFrame.setTitle("Szerkesztés");
        createFrame.setVisible(true);


        
    }
}
