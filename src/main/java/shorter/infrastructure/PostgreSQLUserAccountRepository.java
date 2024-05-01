package shorter.infrastructure;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;

import shorter.domain.PasswordHash;
import shorter.domain.UserAccount;
import shorter.domain.UserAccountRepository;
import shorter.domain.UserID;


@Repository
public class PostgreSQLUserAccountRepository implements UserAccountRepository {

    private final UserDetailsManager manager;

    public PostgreSQLUserAccountRepository(UserDetailsManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<UserAccount> findByID(UserID id) {
        UserDetails user;
        try {
            user = manager.loadUserByUsername(id.toString());
        } catch (UsernameNotFoundException e) {
            System.out.println(e);
            return Optional.ofNullable(null);
        }

        var hash = new PasswordHash(user.getPassword());
        UserAccount userAccount = new UserAccount(id, hash);
        return Optional.of(userAccount);
    }

    @Override
    public void save(UserAccount account) {
        UserDetails saveUser = User.builder().username(account.id().toString()).password(account.hash().hash()).authorities("USER").build();
        if (manager.userExists(account.id().toString())) {
            manager.updateUser(saveUser);
        } else {
            manager.createUser(saveUser);
        }
    }

}
