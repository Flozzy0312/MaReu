package fr.flozzy.mareu.utils;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

import fr.flozzy.mareu.R;

public class DeleteViewAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Cliquer sur le bouton spécifique";
    }

    @Override
    public void perform(UiController uiController, View view) {
        View button = view.findViewById( R.id.item_image_meeting_delete);
        // Maybe check for null
        button.performClick();
    }
}