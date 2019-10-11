package com.pos.sdkdemo.trade.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.math.BigInteger;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BillBean {
    /**
     * 批次号
     */
    @Id(autoincrement = false)
    @Property(nameInDb = "BATCH_NO")
    private Long batchNo;

    /**
     * 借记 入账
     * */
    private String dAmount = "0";
    /**
     * 贷记 出账
     * */
    private String cAmount = "0";

    private int debitNum = 0;
    
    private int creditNum = 0;

    private boolean isSettlement = true;//默认参与结算信息汇总

    private String isFlat = "";//是否对账平

    @Transient
    private BigInteger debitAmount = new BigInteger("000000000000");

    @Transient
    private BigInteger creditAmount = new BigInteger("000000000000");
    @Generated(hash = 1151621211)
    public BillBean(Long batchNo, String dAmount, String cAmount, int debitNum,
            int creditNum, boolean isSettlement, String isFlat) {
        this.batchNo = batchNo;
        this.dAmount = dAmount;
        this.cAmount = cAmount;
        this.debitNum = debitNum;
        this.creditNum = creditNum;
        this.isSettlement = isSettlement;
        this.isFlat = isFlat;
    }

    @Generated(hash = 562884989)
    public BillBean() {
    }
    public void dAmonutAdd(BigInteger b) {
        this.debitAmount = this.debitAmount.add(b);
        this.dAmount = debitAmount.toString();
    }

    public void cAmonutAdd(BigInteger b) {
        this.creditAmount = this.creditAmount.add(b);
        this.cAmount = creditAmount.toString();
    }

    public void debitNumAdd() {
        this.debitNum++;
    }

    public void creditNumAdd() {
        this.creditNum++;
    }

    public Long getBatchNo() {
        return this.batchNo;
    }

    public void setBatchNo(Long batchNo) {
        this.batchNo = batchNo;
    }

    public String getDAmount() {
        return this.dAmount;
    }

    public void setDAmount(String dAmount) {
        this.dAmount = dAmount;
    }

    public String getCAmount() {
        return this.cAmount;
    }

    public void setCAmount(String cAmount) {
        this.cAmount = cAmount;
    }

    public int getDebitNum() {
        return this.debitNum;
    }

    public void setDebitNum(int debitNum) {
        this.debitNum = debitNum;
    }

    public int getCreditNum() {
        return this.creditNum;
    }

    public void setCreditNum(int creditNum) {
        this.creditNum = creditNum;
    }

    public boolean getIsSettlement() {
        return this.isSettlement;
    }

    public void setIsSettlement(boolean isSettlement) {
        this.isSettlement = isSettlement;
    }

    public String getIsFlat() {
        return this.isFlat;
    }

    public void setIsFlat(String isFlat) {
        this.isFlat = isFlat;
    }
}
