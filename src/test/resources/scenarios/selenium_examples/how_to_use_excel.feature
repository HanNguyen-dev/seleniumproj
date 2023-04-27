Feature: How to use excel spreadsheet?
  Loading different data from different sources

  Scenario: Loading data from a step
    Given I am on the search page
    When I enter the following search terms
      | search_term_one  | strawberry |
      | search_term_two  | banana     |
    Then succeed

  Scenario Outline: Loading data from examples
    Given I am on the search page
    When I search for "<search_term_one>"
    When I search for "<search_term_two>"
    Then succeed
    Examples:
      | search_term_one | search_term_two  |
      | strawberry      | banana           |
