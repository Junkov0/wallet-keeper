package com.walletkeeper.wallet_keeper.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "expenses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal amount;

  @Column(name = "merchant_name", length = 100)
  private String merchantName;

  @Column(length = 255)
  private String content;

  @Column(name = "expense_date", nullable = false)
  private LocalDateTime expenseDate;

  // 문자열로 저장
  @Enumerated(EnumType.STRING)
  // DB 타입 명시
  @Column(nullable = false, columnDefinition = "category_type")
  private CategoryType category;

  @Enumerated(EnumType.STRING)
  @Column(name = "source_type", nullable = false, columnDefinition = "source_provider")
  private SourceType sourceType;

  // Hibernate 6 최신 JSONB 매핑 방식
  // "자 이 String은 JSON 타입으로 DB에 넘겨야 하는 String이다."
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "ai_analysis")
  private String aiAnalysis;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Builder
  public Expense(User user, BigDecimal amount, String merchantName, String content,
      LocalDateTime expenseDate, CategoryType category, SourceType sourceType,
      String aiAnalysis) {
    this.user = user;
    this.amount = amount;
    this.merchantName = merchantName;
    this.content = content;
    this.expenseDate = expenseDate;
    this.category = category;
    this.sourceType = sourceType;
    this.aiAnalysis = aiAnalysis;
  }

  // 소프트 삭제
  public void softDelete() {
    this.deletedAt = LocalDateTime.now();
  }

}
