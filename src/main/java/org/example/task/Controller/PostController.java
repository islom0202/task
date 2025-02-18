package org.example.task.Controller;

import lombok.AllArgsConstructor;
import org.example.task.Dto.PostRequest;
import org.example.task.Entity.Post;
import org.example.task.Service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    // post ni create qilish uchun
    @PostMapping("/create/{userId}")
    public ResponseEntity<?> savePost(@PathVariable("userId") Long userId,
                                      @RequestBody PostRequest request) {
        try {
            return ResponseEntity.ok(postService.save(userId, request));
        } catch (Exception e) {
            System.err.println("Error during posting: " + e.getMessage());

            return ResponseEntity.badRequest().body("post failed: " + e.getMessage());
        }
    }

    // faqat shu postni yozgan user update qila oladi
    // faqat kerakli column update bolsa qolganlari ozgarishsiz qolishi uchun patch dan foydalandim
    @PatchMapping("/update/{postId}/{userId}")
    public ResponseEntity<?> updatePost(@PathVariable("postId") Long postId,
                                        @PathVariable("userId") Long userId,
                                        @RequestBody PostRequest request) {
        try {
            return ResponseEntity.ok(postService.updatePost(userId, postId, request));
        } catch (Exception e) {
            System.err.println("Error during updating: " + e.getMessage());

            return ResponseEntity.badRequest().body("updating failed: " + e.getMessage());
        }
    }

    //id orqali post ni olish
    @GetMapping("/{postId}")
    public ResponseEntity<?> findPostById(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.findById(postId));
    }

    // shu id dagi userning barcha postlarini olish
    @GetMapping("/post-list/{userId}")
    public ResponseEntity<List<Post>> postListByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(postService.postList(userId));
    }

    //faqat shu id dagi postni yozgan user delete qila oladi buning uchun user id ham kerak
    @DeleteMapping("/delete/{postId}/{userId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") Long postId,
                                        @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(postService.delete(postId, userId));
    }

}
