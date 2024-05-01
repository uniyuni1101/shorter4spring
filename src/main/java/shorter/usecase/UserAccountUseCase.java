package shorter.usecase;

import org.springframework.stereotype.Service;

import shorter.domain.PasswordHash;
import shorter.domain.UserAccount;
import shorter.domain.UserAccountRepository;
import shorter.domain.UserID;

@Service
public class UserAccountUseCase {

    private final UserAccountRepository userAccountRepository;

    public UserAccountUseCase(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public void register(String userID, String password) {
        UserID id = new UserID(userID);
        boolean isExistAccount = userAccountRepository.findByID(id).isPresent();
        if (isExistAccount) {
            throw new AlreadyExistsUserAccountException();
        }
        
        var hash = PasswordHash.encode(password);

        UserAccount newAccount = new UserAccount(id, hash);
        userAccountRepository.save(newAccount);
    }
}
