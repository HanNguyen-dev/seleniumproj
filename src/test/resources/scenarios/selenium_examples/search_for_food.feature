Feature: Search for food
  It is lunch let find something to eat

  Scenario: Search for Food Trucks
    Given I am on the Google search page
    When I search for "food trucks"
    Then the page should start with "food trucks"
