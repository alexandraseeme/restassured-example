package my.company;

import io.restassured.response.Response;
import my.company.controllers.PostController;
import my.company.models.Post;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PostsTest {

    @Test
    public void addNewPostTest() {
        Post newPost = new Post()
                .setTitle("This is my post")
                .setBody("This is post body")
                .setUserId(5);
        Post resultPost = new PostController().addNewPost(newPost);
        Assert.assertEquals(newPost, resultPost);
    }

    @Test
    public void updateExitingPostTest() {
        String newTitle = RandomStringUtils.randomAlphabetic(8);
        String newBody = RandomStringUtils.randomAlphabetic(25);
        Post resultPost = new PostController().updatePost(99, newTitle, newBody);
        Assert.assertEquals(newTitle, resultPost.getTitle());
    }

    @Test
    public void deleteExistingPostTest(){
        Response r = new PostController().deletePost(99);
        Assert.assertEquals(200, r.getStatusCode());
    }

    @Test
    public void deleteUnexistingPostTest(){
        Response r = new PostController().deletePost(150);
        Assert.assertEquals(404, r.getStatusCode());
    }

    @Test
    public void getAllPostsForUserTest(){
        List<Post> list =  new PostController().getAllPostsByUserId(1);
        Assert.assertTrue(list.size()>0);
    }

    @Test
    public void getPostByExisitingIdTest(){
        Post post =  new PostController().getPostById(1);
        Assert.assertNotNull(post.getTitle());
    }

    @Test
    public void getPostByUnexisitingIdTest(){
        Post post =  new PostController().getPostById(2500);
        Assert.assertNull(post.getTitle());
    }
}
