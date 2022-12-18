package org.example.api;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.Is;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Slf4j
public class PracticeApiTests
{
    @Test
    public void get_all_posts()
    {
        //RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

        Response response =
                given()
                        .baseUri("https://jsonplaceholder.typicode.com/")
                        .when()
                        .get("posts/2");

        Assert.assertEquals(response.statusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        log.info("first item: {}", jsonPath.getString("title"));
        Assert.assertEquals(jsonPath.getString("title"), "qui est esse");
        //response.prettyPrint();
    }

    @Test
    public void get_all_posts_by_param()
    {
        Response response =
                given()
                        .baseUri("https://jsonplaceholder.typicode.com/")
                        .when()
                        .basePath("posts/{id}")
                        .pathParam("id", 2)
                        .get();

        Assert.assertEquals(response.statusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        log.info("first item: {}", jsonPath.getString("title"));
        Assert.assertEquals(jsonPath.getString("title"), "qui est esse");
        Assert.assertEquals(jsonPath.getString("id"), "2");
        //response.prettyPrint();
    }

    @Test
    public void get_comments_from_post_by_param()
    {
        Response response =
                given()
                        .baseUri("https://jsonplaceholder.typicode.com/")
                        .when()
                        .basePath("posts/{id}")
                        .pathParam("id", 2)
                        .get("comments");

        Assert.assertEquals(response.statusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        log.info("first item: {}", jsonPath.getString("title"));
        Assert.assertEquals(jsonPath.getString("title"), "qui est esse");
        Assert.assertEquals(jsonPath.getString("id"), "2");
        //response.prettyPrint();
    }

    @Test
    public void get_comments_from_post_by_postID()
    {
        Response response =
                given()
                        .baseUri("https://jsonplaceholder.typicode.com/")
                        .when()
                        .basePath("comments")
                        .param("postId", 1)
                        .log()
                        .everything()
                        .get();

        Assert.assertEquals(response.statusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        response.prettyPrint();
        Assert.assertEquals(jsonPath.getString("[0].email"), "Eliseo@gardner.biz");
    }

    @Test
    public void postRequest()
    {
        Response response = given()
                .baseUri("https://jsonplaceholder.typicode.com/")
                .header("Content-type", "application/json")
                .and()
                .basePath("/posts")
                .and()
                .body("{\"userId\": 119,\"title\": \"my title example\",\"body\": \"my bdy example\"}")
                .when()
                .post();

    response.prettyPrint();
    Assert.assertEquals(response.getStatusCode(), 201);
    Assert.assertEquals(response.jsonPath().getString("title"), "my title example");
    Assert.assertEquals(response.jsonPath().getString("body"), "my bdy example");
    Assert.assertTrue(response.jsonPath().getInt("id") > 0);
    }

    @Test
    public void putRequest()
    {
        Response response = given()
                .baseUri("https://jsonplaceholder.typicode.com/")
                .header("Content-type", "application/json")
                .and()
                .basePath("/posts/2")
                .and()
                .body("{\"userId\": 119,\"title\": \"my title updated\",\"body\": \"my body updated\"}")
                .when()
                .put();

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "my title updated");
        Assert.assertEquals(response.jsonPath().getString("body"), "my body updated");
        Assert.assertTrue(response.jsonPath().getInt("id") == 2);
    }

    @Test
    public void deleteRequest()
    {
        Response response = given()
                .baseUri("https://jsonplaceholder.typicode.com/")
                .header("Content-type", "application/json")
                .and()
                .basePath("/posts/2")
                .and()
                .body("{\"userId\": 119,\"title\": \"my title updated\",\"body\": \"my body updated\"}")
                .when()
                .delete();

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
        //Assert.assertEquals(response.jsonPath().getInt("id"), null);
    }

    @Test
    public void get_country_lat_and_long_from_city()
    {
        Response response =
                given()
                        .baseUri("http://api.citybik.es/v2/networks/")
                        .when()
                        .basePath("visa-frankfurt")
                        .log()
                        .everything()
                        .get();

        Assert.assertEquals(response.getStatusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        //response.prettyPrint();
        //Assert.assertEquals(jsonPath.getString("[0].email"), "Eliseo@gardner.biz");
        Assert.assertEquals(jsonPath.getString("network.location.country"), "DE");
        log.info(jsonPath.getString("network.location.latitude"));
        log.info(jsonPath.getString("network.location.longitude"));
    }


}
