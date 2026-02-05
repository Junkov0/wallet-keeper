package com.walletkeeper.wallet_keeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WalletKeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletKeeperApplication.class, args);
	}

}
