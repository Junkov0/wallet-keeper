package com.walletkeeper.wallet_keeper.entity;

import lombok.Getter;

@Getter
public enum CategoryType {
  FOOD("식비"),
  TRANSPORT("교통"),
  LIVING("주거/통신"),
  HEALTH("의료/건강"),
  CULTURE("문화/생활"),
  SAVING("저축/보험"),
  ETC("기타");

  private final String description;

  CategoryType(String description) {
    this.description = description;
  }
}
