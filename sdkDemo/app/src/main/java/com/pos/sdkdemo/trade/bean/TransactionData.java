package com.pos.sdkdemo.trade.bean;

import com.pos.sdkdemo.trade.Config;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Arrays;

@Entity
public class TransactionData {
    /**
     * ID
     */
    @Id
    private Long id;

    private int transType = -1;

    /**
     * receive online response status code
     */
    private String _39 = "";
    /**
     * 交易状态 是否已撤销,'-'已撤销,'+'有效记录
     */
    private String status = "";
    /**
     * 结算类型, 1入账、2出账、其它都是结算无关
     */
    private String settleType = "";
    /**
     * 脱线上送状态, '+'未上送,'*'已上送,'!'失败
     */
    private String uploadStatus = "";
    /**
     * 批上送上送状态, '+'未上送,'*'已上送,'!'失败
     */
    private String batchStatus = "+";
    /**
     * transName
     */
    private String name = "";
    /**
     * 流水号
     */
    private String trace = "";
    /**
     * 批次号
     */
    private String batch = "";
    /**
     * 过程码
     */
    private String procCode = "";
    /**
     * 主帐号/转出帐号
     */
    private String pan = "";
    /**
     * 交易金额(单位：分)
     */
    private String amount = "";
    /**
     * 卡有效期
     */
    private String expDate = "";
    /**
     * 结算日期
     */
    private String settleDate = "";
    /**
     * 卡序列号
     */
    private String cardSn = "";
    /**
     * 22域 服务点输入方式码  02 刷卡 05 插卡 07非接
     */
    private String serviceCode = "";
    /**
     * pin最大长度(26域)
     */
    private String maxPinLen = "";
    /**
     * 货币代码
     */
    private String currency = Config.CurrencyCode;
    /**
     * 受理机构标志码
     */
    private String aIICode = "";
    /**
     * 2磁道，脱机交易用
     */
    private String track2 = "";
    /**
     * 3磁道，脱机交易用
     */
    private String track3 = "";
    /**
     * 53域，脱机交易上送
     */
    private String field53 = "";
    /**
     * 55域，IC卡交易数据
     */
    private String field55 = "";
    /**
     * 61域，脱机交易用
     */
    private String field61 = "";
    /**
     * 63域，脱机交易用，escape表明当前字段保存需要对特殊字符转义
     */
    private String field63 = "";
    /**
     * trans date
     */
    private String date = "";
    /**
     * trans time
     */
    private String time = "";
    /**
     * 年份F
     */
    private String year = "";
    /**
     * 参考号
     */
    private String referenceNo = "";
    /**
     * 授权码
     */
    private String authCode = "";
    /**
     * 原交易号
     */
    private String oldTrace = "";
    /**
     * 原交易授权码
     */
    private String oldAuthCode = "";
    /**
     * 卡机构
     */
    private String cardId = "";
    /**
     * 发卡行保留信息
     */
    private String issuerMsg = "";
    /**
     * 银联保留信息
     */
    private String cupMsg = "";
    /**
     * 受理机构保留信息
     */
    private String aquMsg = "";
    /**
     * 发卡行ID
     */
    private String issuerId = "";
    /**
     * 收单行ID
     */
    private String acqId = "";
    /**
     * 交易中心代码
     */
    private String posCenter = "";
    /**
     * 操作员
     */
    private String operator = "";
    /**
     * 备注
     */
    private String remark = "";

    /**
     * 电子签名数据上送状态, 'n'未上送,'s'已上送,'f'失败
     */
    private String signDataStatus = "";
    /**
     * 电子签名已发送的次数
     */
    private String signDataSend = "";
    /**
     * 交易特征码
     */
    private String specialCode = "";
    /**
     * 电子签字数据
     */
    private byte[] signData;
    /**
     * 电子签名已发送的次数
     */
    private String scriptResult = "";
    /**
     * 电子签字图片路径
     */
    private String signPath = "";
    /**
     * 电子签字上送IC卡数据
     */
    private String signICData;
    private String sign_phone;
    private String sign_balance;
    /**
     * 是否非接qps交易
     */
    private String isQPSTrans;
    /**
     * 原参考号
     */
    private String oldReference;
    /**
     * 原交易日期
     */
    private String oldDate;
    /**
     * emvIcdata
     */
    private String emvIcData;
    /**
     * 是否需要打印（打印完后就是不需要）
     */
    private boolean needPrint;
    /**
     * ic卡脚本 是否上送
     */
    private boolean scriptFlag = false;
    /**
     * 免密标志
     */
    private boolean noPINFlag = false;
    /**
     * 免签标志
     */
    private boolean noSignFlag = false;
    /**
     * 时间戳
     */
    private long timeStamp = 0;

    @Generated(hash = 52890511)
    public TransactionData(Long id, int transType, String _39, String status, String settleType,
            String uploadStatus, String batchStatus, String name, String trace, String batch,
            String procCode, String pan, String amount, String expDate, String settleDate,
            String cardSn, String serviceCode, String maxPinLen, String currency, String aIICode,
            String track2, String track3, String field53, String field55, String field61,
            String field63, String date, String time, String year, String referenceNo, String authCode,
            String oldTrace, String oldAuthCode, String cardId, String issuerMsg, String cupMsg,
            String aquMsg, String issuerId, String acqId, String posCenter, String operator,
            String remark, String signDataStatus, String signDataSend, String specialCode,
            byte[] signData, String scriptResult, String signPath, String signICData, String sign_phone,
            String sign_balance, String isQPSTrans, String oldReference, String oldDate,
            String emvIcData, boolean needPrint, boolean scriptFlag, boolean noPINFlag,
            boolean noSignFlag, long timeStamp) {
        this.id = id;
        this.transType = transType;
        this._39 = _39;
        this.status = status;
        this.settleType = settleType;
        this.uploadStatus = uploadStatus;
        this.batchStatus = batchStatus;
        this.name = name;
        this.trace = trace;
        this.batch = batch;
        this.procCode = procCode;
        this.pan = pan;
        this.amount = amount;
        this.expDate = expDate;
        this.settleDate = settleDate;
        this.cardSn = cardSn;
        this.serviceCode = serviceCode;
        this.maxPinLen = maxPinLen;
        this.currency = currency;
        this.aIICode = aIICode;
        this.track2 = track2;
        this.track3 = track3;
        this.field53 = field53;
        this.field55 = field55;
        this.field61 = field61;
        this.field63 = field63;
        this.date = date;
        this.time = time;
        this.year = year;
        this.referenceNo = referenceNo;
        this.authCode = authCode;
        this.oldTrace = oldTrace;
        this.oldAuthCode = oldAuthCode;
        this.cardId = cardId;
        this.issuerMsg = issuerMsg;
        this.cupMsg = cupMsg;
        this.aquMsg = aquMsg;
        this.issuerId = issuerId;
        this.acqId = acqId;
        this.posCenter = posCenter;
        this.operator = operator;
        this.remark = remark;
        this.signDataStatus = signDataStatus;
        this.signDataSend = signDataSend;
        this.specialCode = specialCode;
        this.signData = signData;
        this.scriptResult = scriptResult;
        this.signPath = signPath;
        this.signICData = signICData;
        this.sign_phone = sign_phone;
        this.sign_balance = sign_balance;
        this.isQPSTrans = isQPSTrans;
        this.oldReference = oldReference;
        this.oldDate = oldDate;
        this.emvIcData = emvIcData;
        this.needPrint = needPrint;
        this.scriptFlag = scriptFlag;
        this.noPINFlag = noPINFlag;
        this.noSignFlag = noSignFlag;
        this.timeStamp = timeStamp;
    }

    @Generated(hash = 316941837)
    public TransactionData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTransType() {
        return this.transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public String get_39() {
        return this._39;
    }

    public void set_39(String _39) {
        this._39 = _39;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUploadStatus() {
        return this.uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getBatchStatus() {
        return this.batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrace() {
        return this.trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getPan() {
        return this.pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExpDate() {
        return this.expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getSettleDate() {
        return this.settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getCardSn() {
        return this.cardSn;
    }

    public void setCardSn(String cardSn) {
        this.cardSn = cardSn;
    }

    public String getServiceCode() {
        return this.serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getMaxPinLen() {
        return this.maxPinLen;
    }

    public void setMaxPinLen(String maxPinLen) {
        this.maxPinLen = maxPinLen;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAIICode() {
        return this.aIICode;
    }

    public void setAIICode(String aIICode) {
        this.aIICode = aIICode;
    }

    public String getTrack2() {
        return this.track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    public String getTrack3() {
        return this.track3;
    }

    public void setTrack3(String track3) {
        this.track3 = track3;
    }

    public String getField53() {
        return this.field53;
    }

    public void setField53(String field53) {
        this.field53 = field53;
    }

    public String getField55() {
        return this.field55;
    }

    public void setField55(String field55) {
        this.field55 = field55;
    }

    public String getField61() {
        return this.field61;
    }

    public void setField61(String field61) {
        this.field61 = field61;
    }

    public String getField63() {
        return this.field63;
    }

    public void setField63(String field63) {
        this.field63 = field63;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReferenceNo() {
        return this.referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getAuthCode() {
        return this.authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getOldTrace() {
        return this.oldTrace;
    }

    public void setOldTrace(String oldTrace) {
        this.oldTrace = oldTrace;
    }

    public String getOldAuthCode() {
        return this.oldAuthCode;
    }

    public void setOldAuthCode(String oldAuthCode) {
        this.oldAuthCode = oldAuthCode;
    }

    public String getCardId() {
        return this.cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getIssuerMsg() {
        return this.issuerMsg;
    }

    public void setIssuerMsg(String issuerMsg) {
        this.issuerMsg = issuerMsg;
    }

    public String getCupMsg() {
        return this.cupMsg;
    }

    public void setCupMsg(String cupMsg) {
        this.cupMsg = cupMsg;
    }

    public String getAquMsg() {
        return this.aquMsg;
    }

    public void setAquMsg(String aquMsg) {
        this.aquMsg = aquMsg;
    }

    public String getIssuerId() {
        return this.issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getAcqId() {
        return this.acqId;
    }

    public void setAcqId(String acqId) {
        this.acqId = acqId;
    }

    public String getPosCenter() {
        return this.posCenter;
    }

    public void setPosCenter(String posCenter) {
        this.posCenter = posCenter;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSettleType() {
        return this.settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public String getSignDataStatus() {
        return this.signDataStatus;
    }

    public void setSignDataStatus(String signDataStatus) {
        this.signDataStatus = signDataStatus;
    }

    public String getSignDataSend() {
        return this.signDataSend;
    }

    public void setSignDataSend(String signDataSend) {
        this.signDataSend = signDataSend;
    }

    public String getSpecialCode() {
        return this.specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
    }

    public byte[] getSignData() {
        return this.signData;
    }

    public void setSignData(byte[] signData) {
        this.signData = signData;
    }

    public String getScriptResult() {
        return this.scriptResult;
    }

    public void setScriptResult(String scriptResult) {
        this.scriptResult = scriptResult;
    }

    public String getSignPath() {
        return this.signPath;
    }

    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }

    public String getSignICData() {
        return this.signICData;
    }

    public void setSignICData(String signICData) {
        this.signICData = signICData;
    }

    public String getSign_phone() {
        return this.sign_phone;
    }

    public void setSign_phone(String sign_phone) {
        this.sign_phone = sign_phone;
    }

    public String getSign_balance() {
        return this.sign_balance;
    }

    public void setSign_balance(String sign_balance) {
        this.sign_balance = sign_balance;
    }

    public String getIsQPSTrans() {
        return this.isQPSTrans;
    }

    public void setIsQPSTrans(String isQPSTrans) {
        this.isQPSTrans = isQPSTrans;
    }

    public String getOldReference() {
        return this.oldReference;
    }

    public void setOldReference(String oldReference) {
        this.oldReference = oldReference;
    }

    public String getOldDate() {
        return this.oldDate;
    }

    public void setOldDate(String oldDate) {
        this.oldDate = oldDate;
    }

    public String getEmvIcData() {
        return this.emvIcData;
    }

    public void setEmvIcData(String emvIcData) {
        this.emvIcData = emvIcData;
    }

    public boolean getNeedPrint() {
        return this.needPrint;
    }

    public void setNeedPrint(boolean needPrint) {
        this.needPrint = needPrint;
    }

    public boolean getScriptFlag() {
        return this.scriptFlag;
    }

    public void setScriptFlag(boolean scriptFlag) {
        this.scriptFlag = scriptFlag;
    }

    public boolean getNoPINFlag() {
        return this.noPINFlag;
    }

    public void setNoPINFlag(boolean noPINFlag) {
        this.noPINFlag = noPINFlag;
    }

    public boolean getNoSignFlag() {
        return this.noSignFlag;
    }

    public void setNoSignFlag(boolean noSignFlag) {
        this.noSignFlag = noSignFlag;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getProcCode() {
        return this.procCode;
    }

    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }

    @Override
    public String toString() {
        return "TransactionData{" +
                "id=" + id +
                ", transType=" + transType +
                ", _39='" + _39 + '\'' +
                ", status='" + status + '\'' +
                ", uploadStatus='" + uploadStatus + '\'' +
                ", batchStatus='" + batchStatus + '\'' +
                ", name='" + name + '\'' +
                ", trace='" + trace + '\'' +
                ", batch='" + batch + '\'' +
                ", procCode='" + procCode + '\'' +
                ", pan='" + pan + '\'' +
                ", amount='" + amount + '\'' +
                ", expDate='" + expDate + '\'' +
                ", settleDate='" + settleDate + '\'' +
                ", cardSn='" + cardSn + '\'' +
                ", serviceCode='" + serviceCode + '\'' +
                ", maxPinLen='" + maxPinLen + '\'' +
                ", currency='" + currency + '\'' +
                ", aIICode='" + aIICode + '\'' +
                ", track2='" + track2 + '\'' +
                ", track3='" + track3 + '\'' +
                ", field53='" + field53 + '\'' +
                ", field55='" + field55 + '\'' +
                ", field61='" + field61 + '\'' +
                ", field63='" + field63 + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", year='" + year + '\'' +
                ", referenceNo='" + referenceNo + '\'' +
                ", authCode='" + authCode + '\'' +
                ", oldTrace='" + oldTrace + '\'' +
                ", oldAuthCode='" + oldAuthCode + '\'' +
                ", cardId='" + cardId + '\'' +
                ", issuerMsg='" + issuerMsg + '\'' +
                ", cupMsg='" + cupMsg + '\'' +
                ", aquMsg='" + aquMsg + '\'' +
                ", issuerId='" + issuerId + '\'' +
                ", acqId='" + acqId + '\'' +
                ", posCenter='" + posCenter + '\'' +
                ", operator='" + operator + '\'' +
                ", remark='" + remark + '\'' +
                ", settleType='" + settleType + '\'' +
                ", signDataStatus='" + signDataStatus + '\'' +
                ", signDataSend='" + signDataSend + '\'' +
                ", specialCode='" + specialCode + '\'' +
                ", signData=" + Arrays.toString(signData) +
                ", scriptResult='" + scriptResult + '\'' +
                ", signPath='" + signPath + '\'' +
                ", signICData='" + signICData + '\'' +
                ", sign_phone='" + sign_phone + '\'' +
                ", sign_balance='" + sign_balance + '\'' +
                ", isQPSTrans='" + isQPSTrans + '\'' +
                ", oldReference='" + oldReference + '\'' +
                ", oldDate='" + oldDate + '\'' +
                ", emvIcData='" + emvIcData + '\'' +
                ", needPrint=" + needPrint +
                ", scriptFlag=" + scriptFlag +
                ", noPINFlag=" + noPINFlag +
                ", noSignFlag=" + noSignFlag +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
