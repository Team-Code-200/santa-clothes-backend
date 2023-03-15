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
}
