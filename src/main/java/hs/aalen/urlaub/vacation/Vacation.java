package hs.aalen.urlaub.vacation;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.vacationWish.VacationWish;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Vacation {

    //-----------global declarations--------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //primary key for Vacation-class

    private String title;
    private int timePeriod; //time period in days; for example 14 days
    private Date startDate;

    private boolean isActive;

    //--------entity-relation-annotation----------------------------------
    @OneToMany(mappedBy = "vacation")
    private final List<VacationWish> wishes = new ArrayList<>();

    @ManyToMany()
    private List<Member> memberAccess = new ArrayList<>();

    @OneToMany(mappedBy = "vacation")
    private List<VacationWish> vacationWishes;

    //---------constructors-----------------------------------
    public Vacation() {
    }

    public Vacation(
            long id,
            String title,
            int timePeriod,
            Date startDate
    ) {
        this.id = id;
        this.title = title;
        this.timePeriod = timePeriod;
        this.startDate = startDate;
    }

    //----------Getters and Setters------------------------------
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<Member> getMemberAccess() {
        return memberAccess;
    }

    public void setMemberAccess(List<Member> memberAccess) {
        this.memberAccess = memberAccess;
    }

    public void addMemberAccess(Member access) {
        this.memberAccess.add(access);
    }

    public void removeMemberAccess(Member access) {
        this.memberAccess.remove(access);
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
