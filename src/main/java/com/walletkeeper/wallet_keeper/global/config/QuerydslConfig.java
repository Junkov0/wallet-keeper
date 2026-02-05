package com.walletkeeper.wallet_keeper.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuerydslConfig {

  /**
   * 스프링이 관리하는 EntityManager를 안전하게 주입
   */
  @PersistenceContext
  private EntityManager entityManager;

  /**
   * JPAQueryFactory를 스프링 빈 등록
   * 어디서든 주입받아 사용 가능하다.
   */
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }
}
