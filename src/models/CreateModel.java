package models;

public class CreateModel {
    boolean adding;
    int selected;
    public CreateModel() {
        this.adding = true;
    }

    public boolean isAdding() {
        return adding;
    }

    public void setAdding(boolean adding) {
        this.adding = adding;
    }
    
    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }    
}
