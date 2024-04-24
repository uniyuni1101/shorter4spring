package shorter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import shorter.domain.InvalidShortLinkIDException;
import shorter.domain.ShortLinkID;
import shorter.usecase.NotFoundShortLinkException;
import shorter.usecase.RedirectUseCase;



@RestController
@RequestMapping("/{id}")
public class RedirectController {

    private final RedirectUseCase redirectUseCase;

    public RedirectController(RedirectUseCase redirectUseCase) {
        this.redirectUseCase = redirectUseCase;
    }

    @GetMapping("")
    public ResponseEntity<Void> redirectShortLink(@PathVariable("id") String shortLinkID, RedirectAttributes redirectAttrs) {
        var originalURL = this.redirectUseCase.redirect(new ShortLinkID(shortLinkID));
        ResponseEntity<Void> response = ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalURL.value())).body(null);
        return response;
    }

    // 入力されたIDがShortLinkIDの規則に則ってない場合にエラーレスポンスを返す
    @ExceptionHandler({ InvalidShortLinkIDException.class })
    public ErrorResponse invalidShortLinkIDHandler(InvalidShortLinkIDException ex) {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, "");
    }

    // 入力されたIDが存在しない場合にIndexページに移行する
    @ExceptionHandler({ NotFoundShortLinkException.class })
    public ResponseEntity<Void> notFoundShortLinkHandler(NotFoundShortLinkException ex) {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create("/")).body(null);
    }

    
}
