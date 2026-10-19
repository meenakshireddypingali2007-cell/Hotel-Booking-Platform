package com.stayhaven.repo;
import com.stayhaven.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface AccountRepository extends JpaRepository<Account,Long>{ Optional<Account> findByEmail(String email); long countByRole(String role); }
