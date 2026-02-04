package com.walletkeeper.wallet_keeper.entity;

import lombok.Getter;

@Getter
public enum SourceType {
  MANUAL("수동"),
  RECEIPT_OCR("영수증 OCR"),
  CARD_OCR("카드 내역 OCR");

  private final String description;

  SourceType(String description) {
    this.description = description;
  }
}
