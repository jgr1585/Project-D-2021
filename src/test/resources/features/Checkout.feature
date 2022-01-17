Feature: Check-out
  In order to save time and money
  As a front-desk employee
  I want to perform check-out bookkeeping automatically

  Scenario: Check-out when all bills are sent/paid
    Given An active hotel stay with no unassigned payments
    When The check-out procedure is completed
    Then The hotel stay should end and the room be marked as free