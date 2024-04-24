package shorter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import shorter.domain.InvalidOriginalURLException;
import shorter.usecase.ShortLinkUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/shortlinks")
public class ShortLinkController {
    
    private final ShortLinkUseCase shortLinkUseCase;

    public ShortLinkController(ShortLinkUseCase shortLinkUseCase) {
        this.shortLinkUseCase = shortLinkUseCase;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateShortLinkResponse create(@RequestBody CreateShortLinkRequest request) {
        
        var newShortLink = this.shortLinkUseCase.create(request.originalURL());

        return CreateShortLinkResponse.fromShortLink(newShortLink);
    }

    @ExceptionHandler({ InvalidOriginalURLException.class })
    public ErrorResponse invalidOriginalURLHandler(InvalidOriginalURLException ex) {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, "");
    }
    
}
