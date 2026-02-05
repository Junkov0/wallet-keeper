package com.walletkeeper.wallet_keeper.repository;

import static org.assertj.core.api.Assertions.*;

import com.walletkeeper.wallet_keeper.domain.user.entity.User;
import com.walletkeeper.wallet_keeper.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.dao.DataIntegrityViolationException;

// JPA 관련 컴포넌트만 로드 -> 테스트 속도 높임 (슬라이스 테스트)
// 자동으로 롤백 수행
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // 내가 설정한 DB 그대로 사용
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("유저 저장 성공: 빌더를 통해 생성된 유저가 DB에 정상적으로 저장되어야 한다.")
  void saveUserSuccess() {

    User user = User.builder()
        .email("test@example.com")
        .password("password1")
        .nickname("준영")
        .targetBudget(500000)
        .build();

    User savedUser = userRepository.save(user);

    assertThat(savedUser.getId()).isNotNull();
    assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
    assertThat(savedUser.getCreatedAt()).isNotNull();
    assertThat(savedUser.getUpdatedAt()).isNotNull();
  }

  @Test
  @DisplayName("유저 저장 실패: 중복된 이메일로 가입 시 예외 발생")
  void saveUserDuplicateEmail() {

    String email = "duplicate@test.com";
    User user1 = User.builder()
        .email(email)
        .password("pw1")
        .nickname("유저1")
        .build();
    userRepository.save(user1);

    User user2 = User.builder()
        .email(email)
        .password("pw2")
        .nickname("유저2")
        .build();

    // 중복 저장 시 DataIntegrityViolationException 발생 검증
    // saveAndFlush()는 flush()를 강제로 DB에 날려야 제약 조건 위반 확인 가능하다.
    assertThatThrownBy(() -> userRepository.saveAndFlush(user2))
        .isInstanceOf(DataIntegrityViolationException.class);

  }
}
