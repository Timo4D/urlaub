package hs.aalen.urlaub.rating;

import hs.aalen.urlaub.member.Member;
import hs.aalen.urlaub.vacationWish.VacationWish;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "vacation_wish_id")
    private VacationWish vacationWish;

    private int score;

    public Rating() {
    }

    public Rating(Member member, VacationWish vacationWish, int score) {
        this.member = member;
        this.vacationWish = vacationWish;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public VacationWish getVacationWish() {
        return vacationWish;
    }

    public int getScore() {
        return score;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setVacationWish(VacationWish vacationWish) {
        this.vacationWish = vacationWish;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Rating{" + "id=" + id + ", member=" + member + ", vacationWish=" + vacationWish + ", score=" + score + '}';
    }
}
