package com.example.hani.romancalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class DisplayScreenFragment extends Fragment {

    private TextView convertFromText, convertToText, displayTextView, memoryTextView;
    private ImageButton switchButton;
    private boolean isDecimalToRoman = true;
    private boolean displayingTranslation = false;
    private int memory = -1;
    private String operation = NO_OPERATION;

    public static String ADDITION = "addition";
    public static String SUBTRACTION = "subtraction";
    public static String NO_OPERATION = "no_operation";

    private DisplayFragmentListener listener; // activity which hosts fragment and implements interface

    public interface DisplayFragmentListener {
        void onSwitch(CharSequence input);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // https://www.youtube.com/watch?v=i22INe14JUc
        // onAttach is called at the beginning of fragment lifecycle
        if (context instanceof DisplayFragmentListener) {
            // Activity implements fragments listener interface
            listener = (DisplayFragmentListener) context;
        } else {
            // Activity does not implement interface
            throw new RuntimeException(context.toString() +
                    " must implement DisplayFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_screen, container, false);

        convertFromText = view.findViewById(R.id.convertFromText);

        convertToText = view.findViewById(R.id.convertToText);

        memoryTextView = view.findViewById(R.id.memoryText);

        displayTextView = view.findViewById(R.id.displayScreen);

        switchButton = view.findViewById(R.id.switchButton);

        SwitchListener switchListener = new SwitchListener();
        convertFromText.setOnClickListener(switchListener);
        convertToText.setOnClickListener(switchListener);
        switchButton.setOnClickListener(switchListener);

        return view;
    }

    private void switchTexts() {
        CharSequence fromText = convertFromText.getText();
        convertFromText.setText(convertToText.getText());
        convertToText.setText(fromText);
    }

    private class SwitchListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (isDecimalToRoman()) {
                getListener().onSwitch(MainActivity.ROMAN_TO_DECIMAL);
                setDecimalToRoman(false);
            } else {
                getListener().onSwitch(MainActivity.DECIMAL_TO_ROMAN);
                setDecimalToRoman(true);
            }
            switchTexts();
        }
    }


    public void updateDisplayText(CharSequence input) {
        if (displayTextView.getText().toString().equals("")) {
            displayTextView.setText(input);
        } else if (isDisplayingTranslation()) {
            displayTextView.setText(input);
            setDisplayingTranslation(false);
        } else if (isDecimalToRoman() && Roman.validInt(Integer.parseInt( // Decimal to Roman
                getCurrentDisplayText().toString() + input.toString()))) {
            displayTextView.append(input);
        } else if (!isDecimalToRoman() && Roman.validRoman(getCurrentDisplayText().toString() + input.toString())) {
            displayTextView.append(input); // Roman to decimal
        }
    }

    public void convertDisplayText() {
        if (displayingTranslation) return; // already displaying translation
        if (getCurrentDisplayText().toString().equals("")) return; // cannot translate 0
        if (isDecimalToRoman()) { // Decimal to Roman
            convertDecimalToRoman();
        } else { // Roman to Decimal
            convertRomanToDecimal();
        }
    }

    private void convertRomanToDecimal() {
        if (getOperation().equals(ADDITION)) {
            int sum = Roman.convertToInt(getCurrentDisplayText().toString()) + getMemory();
            //if (Roman.validInt(sum))
            setDisplayTextViewText(sum+"");
            //else setDisplayTextViewText(getString(R.string.invalid_result));
            clearMemory();
        } else if (getOperation().equals(SUBTRACTION)) {
            int difference = getMemory() - Roman.convertToInt(getCurrentDisplayText().toString());
            //if (Roman.validInt(difference))
            setDisplayTextViewText(difference+"");
            //else setDisplayTextViewText(getString(R.string.invalid_result));
            clearMemory();
        } else if (getOperation().equals(NO_OPERATION)) {
            setDisplayTextViewText(Roman.convertToInt(getCurrentDisplayText().toString())+"");
        } setDisplayingTranslation(true);
    }

    private void convertDecimalToRoman() {
        if (getOperation().equals(ADDITION)) {
            int sum = Integer.parseInt(getCurrentDisplayText().toString()) + getMemory();
            if (Roman.validInt(sum)) setDisplayTextViewText(Roman.convertToString(sum));
            else setDisplayTextViewText(getString(R.string.invalid_result));
            clearMemory();
        } else if (getOperation().equals(SUBTRACTION)) {
            int difference = getMemory() - Integer.parseInt(getCurrentDisplayText().toString());
            if (Roman.validInt(difference)) setDisplayTextViewText(Roman.convertToString(difference));
            else setDisplayTextViewText(getString(R.string.invalid_result));
            clearMemory();
        } else if (getOperation().equals(NO_OPERATION)) {
            setDisplayTextViewText(Roman.convertToString(Integer.parseInt(getCurrentDisplayText().toString())));
        } setDisplayingTranslation(true);
    }

    public void clearDisplayText() {
        displayTextView.setText(""); // Display "" on screen as default
        setDisplayingTranslation(false); // No longer displaying translation
    }

    public void storeTextForAddition() {
        int store;
        if (isDecimalToRoman()) store = Integer.parseInt(getCurrentDisplayText().toString()); // number to be stored
        else store = Roman.convertToInt(getCurrentDisplayText().toString());
        setMemory(store);
        setOperation(ADDITION);
        setMemoryTextViewText(store + " + ");
        clearDisplayText();
    }

    public void storeTextForSubtraction() {
        int store;
        if (isDecimalToRoman()) store = Integer.parseInt(getCurrentDisplayText().toString()); // number to be stored
        else store = Roman.convertToInt(getCurrentDisplayText().toString());
        setMemory(store);
        setOperation(SUBTRACTION);
        setMemoryTextViewText(store + " - ");
        clearDisplayText();
    }

    public boolean isDecimalToRoman() {
        return isDecimalToRoman;
    }

    public void setDecimalToRoman(boolean decimalToRoman) {
        this.isDecimalToRoman = decimalToRoman;
    }

    public TextView getDisplayTextView() {
        return displayTextView;
    }

    public void setDisplayTextViewText(CharSequence x) {
        getDisplayTextView().setText(x);
    }

    public CharSequence getCurrentDisplayText() {
        return getDisplayTextView().getText();
    }

    public boolean isDisplayingTranslation() {
        return displayingTranslation;
    }

    public void setDisplayingTranslation(boolean displayingTranslation) {
        this.displayingTranslation = displayingTranslation;
    }

    public DisplayFragmentListener getListener() {
        return listener;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public TextView getMemoryTextView() {
        return memoryTextView;
    }

    public void setMemoryTextViewText(CharSequence text) {
        getMemoryTextView().setText(text);
    }

    public void clearMemory() {
        setMemory(-1);
        setMemoryTextViewText("");
        setOperation(NO_OPERATION);
    }

    public boolean isMemoryEmpty() {
        return (getMemory() == -1);
    }
}