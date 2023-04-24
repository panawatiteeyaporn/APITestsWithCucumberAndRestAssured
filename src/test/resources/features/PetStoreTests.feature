Feature: Pet API interaction

  Scenario: User can add Pet to the store
    Given User created a Pet with the following data
      | id  | name | status    | urls      | category.id | category.name | tag.id | tag.name |
      | 212 | Yuna | available | url1,url2 | 1           | dog           | 0      | myTag    |
    When User added a Pet to the store
    Then The store contain Pet with id 212
    Then Pet with id 212 contains expected information

  Scenario: User can update existing pet
    Given User created a Pet with the following data
      | id  | name | status    | urls      | category.id | category.name | tag.id | tag.name |
      | 111 | Luna | available | url1,url2 | 1           | dog           | 0      | myTag    |
    When User added a Pet to the store
    Then The store contain Pet with id 111
    And User updated Pet data as follows
      | name | status | urls | category.id | category.name | tag.id | tag.name |
      | Yuna | sold   | url3 | 1           | dog           | 1      | yourTag  |
    Then User updated Pet data on the store
    Then Pet with id 111 contains expected information