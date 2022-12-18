package org.example.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/Features/blogPosts.feature",
        glue = {"org.example.api"},
        plugin = {"pretty", "html:target/cucumber"},
        publish = true
)
public class BlogPostsTestRunner extends AbstractTestNGCucumberTests
{
}
