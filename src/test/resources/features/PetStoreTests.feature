Feature: Pet API interaction

  Scenario: User can add pet to the store
    Given User created a Pet with id 212
    When User added a Pet to the store
    Then The store contain Pet with id 212
    Then Pet with id 212 contains expected information