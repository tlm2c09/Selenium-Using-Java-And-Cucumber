Feature: Login Tests 1

  @TEST-1
  Scenario: Login scenarios 1
    Given the user generates random value for "RandomEmail"

    And the user goes to the url "@TD:login_page"
    When the user enters username "@TD:RandomEmail" and password "@TD:standard_user_password"
    When the user clicks the "button" "login"
    Then the user "sees" the element "error message" has texts "Epic sadface: Username and password do not match any user in this service"

    When the user starts a new browser "locked user"
    Given the user goes to the url "@TD:login_page"
    When the user enters username "@TD:locked_out_user_username" and password "@TD:locked_out_user_password"
    When the user clicks the "button" "login"
    Then the user "sees" the element "error message" has texts "Epic sadface: Sorry, this user has been locked out."

    And the user switches to the browser "initial"
    When the user enters username "@TD:standard_user_username" and password "@TD:standard_user_password"
    When the user clicks the "button" "login"
    Then the user is at the inventory page

    And the user switches to the browser "locked user"
    Then the user "sees" the following texts
      | Accepted usernames are: |
      | Password for all users: |
      | error_user              |

    And the user logins with username "@TD:performance_glitch_user_username" and password "@TD:performance_glitch_user_password"
    And the user "sees" the following "buttons" in the inventory page
      | menu          |
      | shopping cart |

  @TEST-2
  Scenario: Login scenarios 2
    Given the user goes to the url "@TD:login_page"
    When the user enters username "@TD:locked_out_user_username" and password "@TD:locked_out_user_password"
    When the user clicks the "button" "login"
    Then the user "sees" the element "error message" has texts "Epic sadface: Sorry, this user has been locked out."
    And the user waits for 15 "seconds" in the current page

  @TEST-3
  Scenario: Login scenarios 3
    And the user goes to the url "@TD:login_page"
    And the user logins with username "@TD:performance_glitch_user_username" and password "@TD:performance_glitch_user_password"
    And the user "sees" the following "buttons" in the inventory page
      | menu          |
      | shopping cart |
    And the user waits for 15 "seconds" in the current page

  @TEST-4
  Scenario: Login scenarios 4
    And the user goes to the url "@TD:login_page"
    And the user logins with username "@TD:performance_glitch_user_username" and password "@TD:performance_glitch_user_password"
    And the user "sees" the following "buttons" in the inventory page
      | menu          |
      | shopping cart |
    And the user waits for 15 "seconds" in the current page

  @TEST-5
  Scenario: Login scenarios 5
    And the user goes to the url "@TD:login_page"
    And the user logins with username "@TD:performance_glitch_user_username" and password "@TD:performance_glitch_user_password"
    And the user "sees" the following "buttons" in the inventory page
      | menu          |
      | shopping cart |
    And the user waits for 15 "seconds" in the current page

  @TEST-6
  Scenario: Login scenarios 6
    And the user goes to the url "@TD:login_page"
    And the user logins with username "@TD:performance_glitch_user_username" and password "@TD:performance_glitch_user_password"
    And the user "sees" the following "buttons" in the inventory page
      | menu          |
      | shopping cart |
    And the user waits for 15 "seconds" in the current page

  @TEST-7
  Scenario: Login scenarios 7
    And the user goes to the url "@TD:login_page"
    And the user logins with username "@TD:performance_glitch_user_username" and password "@TD:performance_glitch_user_password"
    And the user "sees" the following "buttons" in the inventory page
      | menu          |
      | shopping cart |
    And the user waits for 15 "seconds" in the current page

  @TEST-8
  Scenario: Login scenarios 8
    And the user goes to the url "@TD:login_page"
    And the user logins with username "@TD:performance_glitch_user_username" and password "@TD:performance_glitch_user_password"
    And the user "sees" the following "buttons" in the inventory page
      | menu          |
      | shopping cart |
    And the user waits for 15 "seconds" in the current page

  @TEST-9
  Scenario: Login scenarios 9
    And the user goes to the url "@TD:login_page"
    And the user logins with username "@TD:performance_glitch_user_username" and password "@TD:performance_glitch_user_password"
    And the user "sees" the following "buttons" in the inventory page
      | menu          |
      | shopping cart |
    And the user waits for 15 "seconds" in the current page

  @TEST-10
  Scenario: Login scenarios 10
    And the user goes to the url "@TD:login_page"
    And the user logins with username "@TD:performance_glitch_user_username" and password "@TD:performance_glitch_user_password"
    And the user "sees" the following "buttons" in the inventory page
      | menu          |
      | shopping cart |
    And the user waits for 15 "seconds" in the current page