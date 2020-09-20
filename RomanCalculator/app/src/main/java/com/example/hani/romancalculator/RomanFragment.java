/**
 * @Author Hani Ly 073142739@gapps.yrdsb.ca
 */

package com.example.hani.romancalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RomanFragment extends Fragment {

    private Button romanButton1, romanButton5, romanButton10, romanButton50, romanButton100,
    romanButton500, romanButton1000, clearButton, plusButton, minusButton, equalButton;
    private Button[] romanButtons;

    private RomanFragmentListener listener;

    /**
     * Implemented by the activity which hosts RomanFragment. RomanFragment calls the following methods
     * on the activity based on events that occur in RomanFragment.
     */
    public interface RomanFragmentListener {
        void onRomanButtonClicked(CharSequence input);
        void onClearButtonClicked();
        void onRomanPlusButtonClicked();
        void onRomanMinusButtonClicked();
        void onRomanEqualButtonClicked();
    }

    /**
     * Called at the beginning of fragment's lifecycle. Throws RuntimeException if context
     * does not implement the RomanFragmentListener interface.
     * @param context - activity to host RomanFragment
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // https://www.youtube.com/watch?v=i22INe14JUc
        if (context instanceof RomanFragmentListener) {
            // Activity implements fragments listener interface
            listener = (RomanFragmentListener) context;
        } else {
            // Activity does not implement interface
            throw new RuntimeException(context.toString() +
                    " must implement RomanFragmentListener");
        }
    }

    /**
     * Listener for each button representing a roman character.
     */
    private class RomanButtonListener implements View.OnClickListener {
        CharSequence value; // roman value represented by button

        /**
         * Creates a listener for buttons representing roman characters.
         * @param value - roman character represented by button
         */
        public RomanButtonListener(CharSequence value) {
            this.value = value;
        }

        /**
         * Calls onRomanButtonClicked method on host activity and passes value represented
         * by roman button.
         * @param v
         */
        @Override
        public void onClick(View v) {
                getListener().onRomanButtonClicked(this.getValue());
        }

        /**
         * Gets value represented by the roman button the listener is currently listening to.
         * @return
         */
        public CharSequence getValue() {
            return value;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roman, container, false);

        romanButton1 = view.findViewById(R.id.romanButton1);
        romanButton1.setOnClickListener(new RomanButtonListener(romanButton1.getText()));

        romanButton5 = view.findViewById(R.id.romanButton5);
        romanButton5.setOnClickListener(new RomanButtonListener(romanButton5.getText()));

        romanButton10 = view.findViewById(R.id.romanButton10);
        romanButton10.setOnClickListener(new RomanButtonListener(romanButton10.getText()));

        romanButton50 = view.findViewById(R.id.romanButton50);
        romanButton50.setOnClickListener(new RomanButtonListener(romanButton50.getText()));

        romanButton100 = view.findViewById(R.id.romanButton100);
        romanButton100.setOnClickListener(new RomanButtonListener(romanButton100.getText()));

        romanButton500 = view.findViewById(R.id.romanButton500);
        romanButton500.setOnClickListener(new RomanButtonListener(romanButton500.getText()));

        romanButton1000 = view.findViewById(R.id.romanButton1000);
        romanButton1000.setOnClickListener(new RomanButtonListener(romanButton1000.getText()));

        // Create an array to store all buttons representing roman characters
        romanButtons = new Button[]{romanButton1, romanButton5, romanButton10,
                romanButton50, romanButton100, romanButton500, romanButton1000};

        clearButton = view.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onClearButtonClicked();
                enableAllRomanButtons();
            }
        });

        equalButton = view.findViewById(R.id.equalButton);
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onRomanEqualButtonClicked();
            }
        });

        plusButton = view.findViewById(R.id.plusButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onRomanPlusButtonClicked();
            }
        });

        minusButton = view.findViewById(R.id.minusButton);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onRomanMinusButtonClicked();
            }
        });

        plusButton.setEnabled(false);
        minusButton.setEnabled(false);
        plusButton.setBackgroundColor(getResources().getColor(R.color.disabledButtonBackground));
        minusButton.setBackgroundColor(getResources().getColor(R.color.disabledButtonBackground));
        setEnabledEqualButton(false);

        return view;
    }

    /**
     * Gets activity hosting this fragment. Activity implements the RomanFragmentListener interface.
     * @return listener of this fragment
     */
    public RomanFragmentListener getListener() {
        return listener;
    }

    /**
     * Enables and disables each button representing a roman character, based on the current text
     * on screen. If the current text on screen plus the value represented by a button is an invalid
     * value, the button will be disabled. Additionally, sets the background color of each button
     * according to the button's state.
     * @param currentInput - current text on screen
     */
    public void updateRomanButtonStates(CharSequence currentInput) {
        String currentDisplayText = currentInput.toString();
        for (Button button: getRomanButtons()) {
            if (Roman.validRoman(currentDisplayText+button.getText().toString())) {
                button.setEnabled(true);
                button.setBackgroundColor(getResources().getColor(R.color.buttonBackground));
            } else {
                button.setEnabled(false);
                button.setBackgroundColor(getResources().getColor(R.color.disabledButtonBackground));
            }
        }
    }

    /**
     * Enables all buttons representing roman characters, and sets their background color to the
     * default background color.
     */
    public void enableAllRomanButtons() {
        for (Button button: getRomanButtons()) {
            button.setEnabled(true);
            button.setBackgroundColor(getResources().getColor(R.color.buttonBackground));
        }
    }

    /**
     * Gets all buttons representing roman characters.
     * @return Array of all buttons representing roman characters
     */
    public Button[] getRomanButtons() {
        return romanButtons;
    }

    /**
     * Enables or disables the plus and minus buttons based on the boolean passed. Additionally,
     * sets the background color of the plus and minus buttons according to the state of the buttons.
     * @param x - true to enable the buttons, false to disable the buttons
     */
    public void enableAllOperationButtons(boolean x) {
        Button plusButton = getPlusButton();
        Button minusButton = getMinusButton();
        plusButton.setEnabled(x);
        minusButton.setEnabled(x);
        if (x) {
            plusButton.setBackgroundColor(getResources().getColor(R.color.buttonBackground));
            minusButton.setBackgroundColor(getResources().getColor(R.color.buttonBackground));
        } else {
            plusButton.setBackgroundColor(getResources().getColor(R.color.disabledButtonBackground));
            minusButton.setBackgroundColor(getResources().getColor(R.color.disabledButtonBackground));
        }
    }

    /**
     * Enables or disables the equal button based on the boolean passed. Additionally, sets the
     * background color of the equal button according to the state of the button.
     * @param x - true to enable equal button, false to disable equal button
     */
    public void setEnabledEqualButton(boolean x) {
        getEqualButton().setEnabled(x);
        if (x) getEqualButton().setBackgroundColor(getResources().getColor(R.color.buttonBackground));
        else getEqualButton().setBackgroundColor(getResources().getColor(R.color.disabledButtonBackground));
    }

    /**
     * Gets plus button.
     * @return plus button
     */
    public Button getPlusButton() {
        return plusButton;
    }

    /**
     * Gets minus button
     * @return minus button
     */
    public Button getMinusButton() {
        return minusButton;
    }

    /**
     * Gets equal button
     * @return equal button
     */
    public Button getEqualButton() {
        return equalButton;
    }
}
