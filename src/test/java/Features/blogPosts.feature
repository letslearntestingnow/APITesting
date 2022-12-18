Feature: Get comments by post Id
  Scenario: comments by post Id
    Given I build request with baseuri "https://jsonplaceholder.typicode.com/"
    When I use path "comments"
    And I make a get request with "postId" value 1
    Then request should be completed with status 200 and first comment with email "Eliseo@gardner.biz"