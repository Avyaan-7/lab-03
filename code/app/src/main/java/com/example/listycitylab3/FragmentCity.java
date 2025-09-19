package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class FragmentCity extends DialogFragment {

    public interface EditCityDialogListener {//Used AI and logcast to fix the issues in the code as the app was crashing
        void onCityEdited(City city);
    }

    private static City cityToEdit;
    private EditCityDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Used AI to get the layout used in line 37 and 38 again
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        EditText cityName = new EditText(getContext());
        cityName.setHint("City Name");
        EditText provinceName = new EditText(getContext());
        provinceName.setHint("Province");

        layout.addView(cityName);
        layout.addView(provinceName);

        return new AlertDialog.Builder(getContext())
                .setView(layout)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (d, w) -> {
                    if (cityToEdit != null) {
                        cityToEdit.setName(cityName.getText().toString().trim());
                        cityToEdit.setProvince(provinceName.getText().toString().trim()); //Used AI in this particular line
                        listener.onCityEdited(cityToEdit);
                    }
                })
                .create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        }
        else {
            throw new RuntimeException();
        }
    }
    public static void editCity(@NonNull AppCompatActivity activity, @NonNull City city) {
        cityToEdit = city;
        new FragmentCity().show(activity.getSupportFragmentManager(), "Edit City");
    }
}
