package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    /**
     * This method parses JSON from an array of JSON strings and returns an array of Sandwiches
     *
     * @param sandwiches Array of JSON strings with sandwich info
     *
     * @return Array of Sandwich objects
     */
    public static Sandwich[] makeSandwichesFromJsonString(String[] sandwiches) {
        // Make an array to hold all the Sandwich items in sandwich_details
        Sandwich[] sandwichList = new Sandwich[sandwiches.length];

        // Iterate through the json making yummy sandwich items
        for (int s=0; s < sandwiches.length; s++) {
            try {
                sandwichList[s] = parseSandwichString(sandwiches[s]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return sandwichList;

    }

    /**
     * This method parses JSON from a JSON string and returns a Sandwich
     *
     * @param sandwich A JSON string with sandwich info
     *
     * @return A Sandwich object
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Sandwich parseSandwichString(String sandwich)
            throws JSONException {
        JSONObject tempSandObject = new JSONObject(sandwich);
        JSONObject sandNameObject = tempSandObject.getJSONObject("name");
        String sMainName = sandNameObject.optString("mainName",
                "R.string.unknown_value");

        // Can't we all just play nicely and be arrays instead of JSON arrays?
        JSONArray jAlsoKnownAs = sandNameObject.getJSONArray("alsoKnownAs");
        List<String> sAlsoKnownAs = new ArrayList<>();
        for(int i=0; i < jAlsoKnownAs.length(); i++) {
            sAlsoKnownAs.add(jAlsoKnownAs.getString(i));
        }

        String sPlaceOfOrigin = tempSandObject.optString("placeOfOrigin",
                "R.string.unknown_value");
        String sDescription = tempSandObject.optString("description",
                "R.string.unknown_value");
        String sImage = tempSandObject.optString("image",
                "R.string.unknown_value");

        JSONArray jIngredients = tempSandObject.getJSONArray("ingredients");
        List<String> sIngredients = new ArrayList<>();
        for(int i=0; i < jIngredients.length(); i++) {
            sIngredients.add(jIngredients.getString(i));
        }

        return new Sandwich(sMainName, sAlsoKnownAs, sPlaceOfOrigin,
                sDescription, sImage, sIngredients);
    }
}
