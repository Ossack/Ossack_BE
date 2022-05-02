package com.sparta.startup_be.service;


import com.sparta.startup_be.dto.FavoriteResponseDto;
import com.sparta.startup_be.model.Favorite;
import com.sparta.startup_be.model.Post;
import com.sparta.startup_be.model.User;
import com.sparta.startup_be.repository.FavoriteRepository;
import com.sparta.startup_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class FavoriteService {

    private  final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 좋아요 기능
    @Transactional
    public FavoriteResponseDto favoriteCheck(Long postId, String userEmail) {

        Post post = postRepository.findById(postId).orElse(null);
        if(post == null){
            return  new FavoriteResponseDto(400,"존재하지 않는 게시글 입니다");
        }

        User user = userRepository.findByUserEmail(userEmail).orElse(null);
        if(user == null){
            return  new FavoriteResponseDto(400,"존재하지 않는 회원입니다.");
        }

        Favorite favorite = favoriteRepository.findByUser_UserEmailAndPost_PostId(userEmail,postId).orElse(null);

        if(favorite == null){
            Favorite saveFavorite = new Favorite(post,user);
            favoriteRepository.save(saveFavorite);
            return new FavoriteResponseDto(200,null, true );
        } else {
            favoriteRepository.deleteById(favorite.getFavoriteId());
            return new FavoriteResponseDto(200,null, false);
        }
    }
}
