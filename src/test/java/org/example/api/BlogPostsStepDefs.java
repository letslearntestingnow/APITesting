package org.example.api;

import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.Is;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BlogPostsStepDefs
{
    RequestSpecification request;
    Response response;

    @Given("I build request with baseuri {string}")
    public void i_build_request_with_baseuri(String baseUri) {
        // Write code here that turns the phrase above into concrete actions
        request = given()
                .baseUri(baseUri);
    }
    @When("I use path {string}")
    public void i_use_path(String path) {
        // Write code here that turns the phrase above into concrete actions
        request.basePath(path);
    }
    @When("I make a get request with {string} value {int}")
    public void i_make_a_get_request_with_value(String parameter, Integer queryValue) {
        // Write code here that turns the phrase above into concrete actions
        response = request.queryParam(parameter, queryValue)
                .log()
                .everything()
                .get();
    }
    @Then("request should be completed with status {int} and first comment with email {string}")
    public void request_should_be_completed_with_status_and_first_comment_with_email(Integer status, String email) {
        // Write code here that turns the phrase above into concrete actions
        var jsonPath = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(), status);
        Assert.assertEquals(jsonPath.getString("[0].email"), "Eliseo@gardner.biz");
    }

}
