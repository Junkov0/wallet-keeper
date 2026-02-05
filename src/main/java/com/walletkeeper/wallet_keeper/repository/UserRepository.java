package com.walletkeeper.wallet_keeper.repository;

import com.walletkeeper.wallet_keeper.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * 이메일을 통한 사용자 조회
   * @param email 조회할 이메일
   * @return 조회 된 사용자를 Optional로 감싸서 반환
   */
  Optional<User> findByEmail(String email);

  /**
   * 이메일 중복 여부 확인
   * @param email 확인할 이메일
   * @return 존재 여부 (true/false)
   */
  boolean existsByEmail(String email);

}
