package com.sparta.startup_be.favorite;

import com.sparta.startup_be.login.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/estates/{estateid}/like")
    public ResponseEntity<Boolean> pressLike(@PathVariable Long estateid,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userid = userDetails.getId();
        return favoriteService.pressLike(estateid, userid);
    }

    @PostMapping("/estates/{estateid}/unlike")
    public ResponseEntity<Boolean> unpresslike(@PathVariable Long estateid,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userid = userDetails.getId();
        return favoriteService.unpressLike(estateid, userid);
    }
}
