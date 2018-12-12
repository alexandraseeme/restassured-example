package my.company.controllers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import my.company.models.Post;
import my.company.util.PropertyLoader;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PostController {

    private RequestSpecification requestSpecification;

    public static final String BASE_URL = PropertyLoader.loadProperty("base.url");
    public static final String POSTS_ENDPOINT = PropertyLoader.loadProperty("posts.path");

    public PostController() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL).build();
    }

    public Post addNewPost(Post post) {
        return given(requestSpecification)
                .body(post)
                .post(POSTS_ENDPOINT).as(Post.class);
    }

    public Post updatePost(int postId, String title, String body) {
        Post newPost = new Post();
        if (title != null)
            newPost.setTitle(title);
        if (body != null)
            newPost.setBody(body);
        return given(requestSpecification)
                .pathParam("id", postId)
                .body(newPost)
                .when()
                .put(POSTS_ENDPOINT + "/{id}")
                .then().log().everything().extract().body().jsonPath().getObject("", Post.class);
    }

    public Response deletePost(int postId) {
        return given(requestSpecification)
                .pathParam("id", postId)
                .when()
                .delete(POSTS_ENDPOINT + "/{id}");
    }

    public List<Post> getAllPostsByUserId(int userId) {
        return given(requestSpecification)
                .queryParam("userId", userId)
                .get(POSTS_ENDPOINT)
                .then().log().all()
                .extract().body()
                .jsonPath().getList("", Post.class);
    }

    public Post getPostById(int id) {
        return given(requestSpecification)
                .pathParam("id", id)
                .when()
                .get(POSTS_ENDPOINT + "/{id}")
                .then().log().all()
                .extract().body()
                .jsonPath().getObject("", Post.class);
    }
}
