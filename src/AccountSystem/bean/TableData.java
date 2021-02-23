package AccountSystem.bean;

import javafx.beans.property.SimpleStringProperty;

public class TableData {
    private final SimpleStringProperty id;
    private final SimpleStringProperty type;
    private final SimpleStringProperty money;
    private final SimpleStringProperty classification;
    private final SimpleStringProperty memo;

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getMoney() {
        return money.get();
    }

    public SimpleStringProperty moneyProperty() {
        return money;
    }

    public void setMoney(String money) {
        this.money.set(money);
    }

    public String getClassification() {
        return classification.get();
    }

    public SimpleStringProperty classificationProperty() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification.set(classification);
    }

    public String getMemo() {
        return memo.get();
    }

    public SimpleStringProperty memoProperty() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo.set(memo);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    private final SimpleStringProperty date;

    public TableData(String id, String type, String money, String classification, String memo, String date) {
        this.id = new SimpleStringProperty(id);
        this.type = new SimpleStringProperty(type);
        this.money = new SimpleStringProperty(money);
        this.classification = new SimpleStringProperty(classification);
        this.memo = new SimpleStringProperty(memo);
        this.date = new SimpleStringProperty(date);
    }

}
