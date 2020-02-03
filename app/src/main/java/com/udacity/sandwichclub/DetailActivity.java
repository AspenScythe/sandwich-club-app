package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichString(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

        TextView originTv = findViewById(R.id.origin_tv);
        String oLoc = sandwich.getPlaceOfOrigin();
        if (oLoc.isEmpty()) {
            originTv.setText(R.string.unknown_origin);
        } else {
            originTv.setText(oLoc);
        }

        TextView descriptionTv = findViewById(R.id.description_tv);
        descriptionTv.setText(sandwich.getDescription());

        TextView akaTv = findViewById(R.id.also_known_tv);
        List<String> akaList = sandwich.getAlsoKnownAs();
        if (!akaList.isEmpty()) {
            for (int i = 0; i < akaList.size(); i++) {
                akaTv.append(akaList.get(i));
                if (i < akaList.size() - 1) {
                    akaTv.append(", ");
                }
            }
        } else {
            akaTv.setText(R.string.no_alternate_names);
        }


        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = sandwich.getIngredients();
        for(int i=0; i < ingredientsList.size(); i++) {
            ingredientsTv.append(ingredientsList.get(i));
            if (i < ingredientsList.size() - 1) {
                ingredientsTv.append(", ");
            }
        }


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
