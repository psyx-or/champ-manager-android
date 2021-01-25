package org.fsgt38.fsgt38.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.databinding.BindingAdapter;

import java.util.function.Consumer;

/**
 * Adapters pour le data binding
 */
public class BindingAdapters {

    @BindingAdapter("itemSelected")
    public static void setItemSelectedListener(Spinner view, Consumer<Object> consumer) {
        view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                consumer.accept(view.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @BindingAdapter("itemSelected")
    public static void setItemSelectedListener(AutoCompleteTextView view, Consumer<Object> consumer) {
        view.setOnItemClickListener((parent, v, position, id) -> consumer.accept(parent.getItemAtPosition(position)));
    }

    @BindingAdapter("textChanged")
    public static void setTextChangedListener(EditText view, Consumer<Object> consumer) {
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                consumer.accept(s);
            }
        });
    }

    @BindingAdapter("checkChanged")
    public static void setCheckChangedListener(CheckBox view, Consumer<Object> consumer) {
        view.setOnCheckedChangeListener((v, isChecked) -> consumer.accept(isChecked));
    }
}