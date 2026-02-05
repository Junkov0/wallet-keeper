package com.walletkeeper.wallet_keeper.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = false, length = 255)
  private String password;

  @Column(nullable = false, length = 50)
  private String nickname;

  @Column(name = "target_budget")
  private Integer targetBudget;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  /*
    User 객체를 읽기 쉬운 방식으로 안전하게 생성해주는 생성기

    User user = User.builder()
    .email("test@test.com")
    .password("encodedPw")
    .nickname("준영")
    .targetBudget(500000)
    .build();

    이런식으로 빌더 패턴이 가능해진다.
   */
  @Builder
  public User(String email, String password, String nickname, Integer targetBudget) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.targetBudget = targetBudget;
  }
}
