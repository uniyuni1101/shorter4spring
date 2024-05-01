package shorter.domain;

import java.util.Optional;

public interface UserAccountRepository {
    Optional<UserAccount> findByID(UserID id);
    void save(UserAccount account);
}
