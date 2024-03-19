package com.example.cooking.utils

class ApiConstants {

    companion object {
        const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    }

    object NetworkEndPoints{

        //Lookup a single random meal
        const val RANDOM_MEAL ="random.php"

        //Filter by category
        const val FILTER_CATEGORY = "filter.php"

        // Search meal by name
        const val SEARCH_BY_NAME = "search.php"

        //List all meals by first letter
        const val LIST_BY_CHRONOLOGY = "search.php?f=a"

        // Lookup full meal details by id
        const val LIST_BY_ID = "lookup.php"

        //List all categories
        const val LIST_BY_CATEGORY = "list.php?c=list"

        //List all areas
        const val LIST_BY_AREA = "list.php?a=list"

        //List all ingredients
        const val LIST_BY_INGREDIENT = "list.php?i=list"

    }
}