package com.sasuke.rentkart.util;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.rule.NotEmptyRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 3/13/2018.
 */

public abstract class ValidationListener implements Validator.ValidationListener {

    abstract public Context getContext();

    @Override
    public void onValidationFailed(List<ValidationError> list) {
        List<ValidationError> filteredError = new ArrayList<>();
        filteredError.addAll(list);

        for (ValidationError error : list) {
            View view = error.getView();
            if (view instanceof EditText) {
                if (((EditText) view).getText().toString().trim().length() == 0) {
                    boolean isRequired = false;
                    for (Rule rule : error.getFailedRules()) {
                        if (rule instanceof NotEmptyRule) {
                            isRequired = true;
                        }
                    }
                    if (!isRequired) {
                        filteredError.remove(error);
                    }
                }
            }
        }
        if (filteredError.size() == 0) {
            onValidationSucceeded();
            return;
        }

        for (ValidationError error : filteredError) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
