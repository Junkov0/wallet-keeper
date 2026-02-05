package com.walletkeeper.wallet_keeper.domain.expense.repository;

import com.walletkeeper.wallet_keeper.domain.expense.entity.CategoryType;
import com.walletkeeper.wallet_keeper.domain.expense.entity.Expense;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepositoryCustom {

  // 특정 유저의 내역을 카테고리, 가맹점, 기간 별로 동적 검색
  List<Expense> searchExpenses(Long userId, CategoryType category, String merchantName, LocalDateTime start, LocalDateTime end);
}
