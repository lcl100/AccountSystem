package AccountSystem.bean;

public class Record {
    private int userId;// 用户序号
    private int recordId;// 记录序号
    private String recordType;// 类型（收入/支出）
    private float recordMoney;// 金额
    private String recordClassification;// 分类（收入分类【工资/补贴/奖金】/支出分类【饮食/服饰/交通/住宿/文娱/生活用品】）
    private String recordMemo;// 备注
    private String recordDate;// 日期

    public Record() {

    }

    public Record(int recordId) {
        this.recordId = recordId;
    }

    public Record(int userId, String recordType, float recordMoney, String recordClassification, String recordMemo, String recordDate) {
        this.userId = userId;
        this.recordType = recordType;
        this.recordMoney = recordMoney;
        this.recordClassification = recordClassification;
        this.recordMemo = recordMemo;
        this.recordDate = recordDate;
    }

    public Record(int userId, int recordId, String recordType, float recordMoney, String recordClassification, String recordMemo, String recordDate) {
        this.userId = userId;
        this.recordId = recordId;
        this.recordType = recordType;
        this.recordMoney = recordMoney;
        this.recordClassification = recordClassification;
        this.recordMemo = recordMemo;
        this.recordDate = recordDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public float getRecordMoney() {
        return recordMoney;
    }

    public void setRecordMoney(float recordMoney) {
        this.recordMoney = recordMoney;
    }

    public String getRecordClassification() {
        return recordClassification;
    }

    public void setRecordClassification(String recordClassification) {
        this.recordClassification = recordClassification;
    }

    public String getRecordMemo() {
        return recordMemo;
    }

    public void setRecordMemo(String recordMemo) {
        this.recordMemo = recordMemo;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
