package shorter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import shorter.usecase.AlreadyExistsUserAccountException;
import shorter.usecase.UserAccountUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/user")
public class UserAccountController {

    private final UserAccountUseCase userAccountUseCase;

    public UserAccountController(UserAccountUseCase userAccountUseCase) {
        this.userAccountUseCase = userAccountUseCase;
    }
    
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUserAccount(@RequestBody RegisterUserAccountRequest request) {
        userAccountUseCase.register(request.id(), request.password());
        
    }

    @ExceptionHandler( {AlreadyExistsUserAccountException.class} )
    public ErrorResponse alreadyExistsUserAccountHandler(AlreadyExistsUserAccountException ex) {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, null);
    }
    
}
