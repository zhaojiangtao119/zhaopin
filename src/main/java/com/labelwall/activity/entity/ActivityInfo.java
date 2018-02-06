package com.labelwall.activity.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Administrator
 */
public class ActivityInfo extends BaseBean {
    private Integer id;
    private String theme;// �����
    private String content;// �����
    private Integer free;// �Ƿ���ѣ�Ĭ����� Ĭ��0
    private String creattime;// ����ʱ��
    private String starttime;// �������ʼʱ��
    private String endtime;// �������ֹʱ��
    private Integer num_limt;// ������������
    private String location;// �ص�
    private String school;// ѧУ
    private Integer schoolId;
    private String type;// ����
    private String style;// ��ʽ
    private String posterURL;// ����ͼƬ
    private Integer status;// ��Ƿ�ʼ��� Ĭ��0
    private BigDecimal amount;// ���ѽ�� Ĭ��0
    private String customContent;// �Զ��屨������
    private String detailStartTime;// ����--���ʼʱ��
    private String detailEndTime;// ����--�����ʱ��
    private String detailContent;// ����--�����
    private String city;
    private String county;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getNum_limt() {
        return num_limt;
    }

    public void setNum_limt(Integer num_limt) {
        this.num_limt = num_limt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getCustomContent() {
        return customContent;
    }

    public void setCustomContent(String customContent) {
        this.customContent = customContent;
    }

    public String getDetailStartTime() {
        return detailStartTime;
    }

    public void setDetailStartTime(String detailStartTime) {
        this.detailStartTime = detailStartTime;
    }

    public String getDetailEndTime() {
        return detailEndTime;
    }

    public void setDetailEndTime(String detailEndTime) {
        this.detailEndTime = detailEndTime;
    }

    public String getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(String detailContent) {
        this.detailContent = detailContent;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
}
