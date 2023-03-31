package io.wisoft.capstonedesign.domain.findorder.persistence;

import io.wisoft.capstonedesign.domain.information.persistence.Information;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.find.persistence.Find;
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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "find_id", nullable = false)
    private Find find;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 정적 생성자 메소드
     */
    public static FindOrder createFindOrder(
            final String text,
            final Information information,
            final Find find,
            final User user
    ) {
        FindOrder findOrder = new FindOrder();
        findOrder.sendDate = LocalDateTime.now();
        findOrder.text = text;
        findOrder.setInformation(information);
        findOrder.setFind(find);
        findOrder.setUser(user);
        return findOrder;
    }

    /**
     * 연관관계 편의 메소드
     */
    public void setInformation(final Information information) {

        if (this.information != null) this.information.getFindOrders().remove(this);
        this.information = information;
        information.getFindOrders().add(this);
    }

    public void setFind(final Find find) {

        if (this.find != null) this.find.setFindOrder(null);
        this.find = find;
        find.setFindOrder(this);
    }

    public void setUser(final User user) {

        if (this.user != null) this.user.getFindOrders().remove(this);
        this.user = user;
        user.getFindOrders().add(this);
    }

    /**
     * 주문 내역 기타 사항 수정
     */
    public void update(final String text) {
        this.text = text;
    }
}
