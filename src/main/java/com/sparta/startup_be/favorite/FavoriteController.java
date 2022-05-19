package com.sparta.startup_be.favorite;


import com.sparta.startup_be.exception.StatusMessage;
import com.sparta.startup_be.login.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/api/favorite/{estateid}")
    public ResponseEntity<StatusMessage> pressLike(@PathVariable Long estateid,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userid = userDetails.getUser().getId();
        return favoriteService.pressLike(estateid, userid);
    }

    @DeleteMapping("/api/favorite/{estateid}")
    public ResponseEntity<StatusMessage> unpresslike(@PathVariable Long estateid,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userid = userDetails.getUser().getId();
        return favoriteService.unpressLike(estateid,userid);
    }
}
