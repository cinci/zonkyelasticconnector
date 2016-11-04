package com.jctechcloud.entity;

import java.util.Date;
import java.util.List;

/**
 * Loan entity
 * <p>
 * Created by jcincera on 15/06/16.
 */
public class Loan {
    private Long id;
    private String name;
    private String story;
    private String purpose;
    private List<Photo> photos;
    private String nickName;
    private Integer termInMonths;
    private Double interestRate;
    private String rating;
    private Double amount;
    private Double remainingInvestment;
    private Double investmentRate;
    private Boolean covered;
    private Date datePublished;
    private Boolean published;
    private Date deadline;
    private Integer investmentsCount;
    private Integer questionsCount;
    private String region;
    private String regionName;
    private RegionGeo regionGeo;
    private String mainIncomeType;
    private Date internalDateCreated;
    private Long internalBulkId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getTermInMonths() {
        return termInMonths;
    }

    public void setTermInMonths(Integer termInMonths) {
        this.termInMonths = termInMonths;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRemainingInvestment() {
        return remainingInvestment;
    }

    public void setRemainingInvestment(Double remainingInvestment) {
        this.remainingInvestment = remainingInvestment;
    }

    public Double getInvestmentRate() {
        return investmentRate;
    }

    public void setInvestmentRate(Double investmentRate) {
        this.investmentRate = investmentRate;
    }

    public Boolean getCovered() {
        return covered;
    }

    public void setCovered(Boolean covered) {
        this.covered = covered;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getInvestmentsCount() {
        return investmentsCount;
    }

    public void setInvestmentsCount(Integer investmentsCount) {
        this.investmentsCount = investmentsCount;
    }

    public Integer getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(Integer questionsCount) {
        this.questionsCount = questionsCount;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public RegionGeo getRegionGeo() {
        return regionGeo;
    }

    public void setRegionGeo(RegionGeo regionGeo) {
        this.regionGeo = regionGeo;
    }

    public String getMainIncomeType() {
        return mainIncomeType;
    }

    public void setMainIncomeType(String mainIncomeType) {
        this.mainIncomeType = mainIncomeType;
    }

    public Date getInternalDateCreated() {
        return internalDateCreated;
    }

    public void setInternalDateCreated(Date internalDateCreated) {
        this.internalDateCreated = internalDateCreated;
    }

    public Long getInternalBulkId() {
        return internalBulkId;
    }

    public void setInternalBulkId(Long internalBulkId) {
        this.internalBulkId = internalBulkId;
    }
}
