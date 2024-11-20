package org.example.task.Service;

import lombok.AllArgsConstructor;
import org.example.task.Dto.PostRequest;
import org.example.task.Entity.Post;
import org.example.task.Entity.Users;
import org.example.task.Repository.PostRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final UsersService usersService;

    public Post save(Long userId, PostRequest request) {
        Users user = usersService.findById(userId);
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        return postRepo.save(post);
    }

    public Post updatePost(Long userId, Long postId, PostRequest request) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("not found"));
        if (userId == post.getAuthor().getId()) {
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
            post.setUpdatedAt(LocalDateTime.now());
            return postRepo.save(post);
        } else throw new RuntimeException("permisson denied");
    }

    public Post findById(Long postId) {
        return postRepo.findById(postId).orElseThrow(
                () -> new RuntimeException("post not found")
        );
    }

    public Post findByUserId(Long userId) {
        return postRepo.findByAuthor_Id(userId);
    }

    public List<Post> postList(Long userId) {
        return postRepo.findAllByAuthor_Id(userId);
    }

    public String delete(Long postId, Long userId) {
        Post post = findById(postId);
        if (Objects.equals(userId, post.getAuthor().getId())) {
            postRepo.deleteById(postId);
            return postId + " deleted";
        }
        else throw new RuntimeException("permission denied");
    }
}
