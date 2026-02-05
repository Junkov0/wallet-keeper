package com.walletkeeper.wallet_keeper.domain.receipt.entity;

import com.walletkeeper.wallet_keeper.domain.expense.entity.Expense;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "receipts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Receipt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 영수증 지출 내역 없이는 단독으로 존재 X
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "expense_id", nullable = false)
  private Expense expense;

  @Column(name = "s3_key", length = 255, nullable = false)
  private String s3Key;

  @Column(name = "original_filename", length = 255, nullable = false)
  private String originalFileName;

  @Column(name = "file_size", nullable = false)
  private Long fileSize;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Builder
  public Receipt(Expense expense, String s3Key, String originalFileName, Long fileSize) {
    this.expense = expense;
    this.s3Key = s3Key;
    this.originalFileName = originalFileName;
    this.fileSize = fileSize;
  }
}
