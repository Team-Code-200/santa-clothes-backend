package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "FIND_ORDER")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class FindOrder {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "send_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime sendDate;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String text;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "info_id", nullable = false)
    private Information information;

    @OneToOne
    @JoinColumn(name = "find_id", nullable = false)
    private Find find;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 정적 생성자 메소드
     */
    public static FindOrder createFindOrder(
            LocalDateTime sendDate,
            String text,
            Information information,
            Find find,
            User user
    ) {
        FindOrder findOrder = new FindOrder();
        findOrder.sendDate = sendDate;
        findOrder.text = text;
        findOrder.setInformation(information);
        findOrder.setFind(find);
        findOrder.setUser(user);
        return findOrder;
    }

    /**
     * 연관관계 편의 메소드
     */
    public void setInformation(Information information) {

        if (this.information != null) this.information.getFindOrders().remove(this);
        this.information = information;
        information.getFindOrders().add(this);
    }

    public void setFind(Find find) {

        if (this.find != null) this.find.setFindOrder(null);
        this.find = find;
        find.getFindOrder().setFind(find);
    }

    public void setUser(User user) {

        if (this.user != null) this.user.getFindOrders().remove(this);
        this.user = user;
        user.getFindOrders().add(this);
    }
}
