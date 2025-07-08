package com.example.spinnerex.utils;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

public class ValidatorInputV2 implements TextWatcher {

    private static final String LOG_TAG = "ValidatorInput";

    public enum Type {TEXT, CORAZON, EMAIL, INCOME, CELLPHONE, PASSWORD, TEXTNUMBER, NUMBER, CURP, ADDRESSNUM}
    private TextInputLayout inputLayout;
    private String messageErrorRequired;
    private String messageErrorFormat;
    private Type typeInput = Type.TEXT;
    private int minCharacters;

    public ValidatorInputV2(TextInputLayout textInputLayout, String message, Type type, String messageFormat, int minimun) {
        inputLayout = textInputLayout;
        messageErrorRequired = message;
        typeInput = type;
        messageErrorFormat = messageFormat;
        minCharacters = minimun;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}