Feature: Pet API interaction

  Todo - add Pet with multiple tags

  Scenario: User can add Pet to the store
    Given User created a Pet with the following data
      | id  | name | status    | urls      | category.id | category.name | tag.id | tag.name |
      | 212 | Yuna | available | url1,url2 | 1           | dog           | 0      | myTag    |
    When User added a Pet to the store
    Then The store contain Pet with id 212
    Then Pet with id 212 contains expected information

  Scenario: User can update existing Pet
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

  Scenario: User can update existing Pet with form data
    Given User created a Pet with the following data
      | id  | name | status    | urls | category.id | category.name | tag.id | tag.name |
      | 515 | Yam  | available | url1 | 1           | dog           | 0      | myTag    |
    When User added a Pet to the store
    Then The store contain Pet with id 515
    And User updated Pet with form data
      | name | status |
      | Yuna | sold   |
    Then Pet with id 515 contains expected information

  Scenario: User can find existing Pet by status
    Given User created a Pet with the following data
      | id  | name | status | urls | category.id | category.name | tag.id | tag.name |
      | 313 | ted  | sold   | url1 | 1           | dog           | 0      | myTag    |
    When User added a Pet to the store
    Then User can find Pet under status "sold"

  Scenario: User can delete existing Pet
    Given User created a Pet with the following data
      | id  | name | status    | urls | category.id | category.name | tag.id | tag.name |
      | 141 | tim  | available | url1 | 1           | dog           | 0      | myTag    |
    When User added a Pet to the store
    Then The store contain Pet with id 141
    When User deleted a Pet with id 141
    Then Pet with id 141 is not found