Feature: Pet API interaction

  Scenario: User can add another pet to the store
    Given User created a Pet with the following data
      | id  | name | status    | urls      | category.id | category.name | tag.id | tag.name |
      | 212 | Yuna | available | url1,url2 | 1           | dog           | 0      | myTag    |
    When User added a Pet to the store
    Then The store contain Pet with id 212
    Then Pet with id 212 contains expected information