/**
 * @Author Hani Ly 073142739@gapps.yrdsb.ca
 */

package com.example.hani.romancalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DecimalFragment extends Fragment {

    private Button button0, button1, button2, button3, button4, button5, button6,
    button7, button8, button9, clearButton, plusButton, minusButton, equalButton;
    private Button[] decimalButtons;

    private DecimalFragmentListener listener;

    /**
     * Implemented by the activity which hosts DecimalFragment. DecimalFragment calls the following methods
     * on the activity based on events that occur in DecimalFragment.
     */
    public interface DecimalFragmentListener {
        void onDecimalButtonClicked(CharSequence input);
        void onClearButtonClicked();
        void onDecimalPlusButtonClicked();
        void onDecimalMinusButtonClicked();
        void onDecimalEqualButtonClicked();
    }

    /**
     * Called at the beginning of fragment's lifecycle. Throws RuntimeException if context
     * does not implement the DecimalFragmentListener interface.
     * @param context - activity to host DecimalFragment
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // https://www.youtube.com/watch?v=i22INe14JUc
        // onAttach is called at the beginning of fragment lifecycle
        if (context instanceof DecimalFragmentListener) {
            // Activity implements fragments listener interface
            listener = (DecimalFragmentListener) context;
        } else {
            // Activity does not implement interface
            throw new RuntimeException(context.toString() +
                    " must implement DecimalFragmentListener");
        }
    }

    /**
     * Listener for each button representing a decimal value.
     */
    private class DecimalButtonListener implements View.OnClickListener {

        CharSequence value; // The input that the button represents

        public DecimalButtonListener(CharSequence value) {
            this.value = value;
        }

        @Override
        public void onClick(View v) {
            listener.onDecimalButtonClicked(this.getValue());
        }

        public CharSequence getValue() {
            return value;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_decimal, container, false);

        button0 = view.findViewById(R.id.button0);
        button0.setOnClickListener(new DecimalButtonListener(button0.getText()));

        button1 = view.findViewById(R.id.button1);
        button1.setOnClickListener(new DecimalButtonListener(button1.getText()));

        button2 = view.findViewById(R.id.button2);
        button2.setOnClickListener(new DecimalButtonListener(button2.getText()));

        button3 = view.findViewById(R.id.button3);
        button3.setOnClickListener(new DecimalButtonListener(button3.getText()));

        button4 = view.findViewById(R.id.button4);
        button4.setOnClickListener(new DecimalButtonListener(button4.getText()));

        button5 = view.findViewById(R.id.button5);
        button5.setOnClickListener(new DecimalButtonListener(button5.getText()));

        button6 = view.findViewById(R.id.button6);
        button6.setOnClickListener(new DecimalButtonListener(button6.getText()));

        button7 = view.findViewById(R.id.button7);
        button7.setOnClickListener(new DecimalButtonListener(button7.getText()));

        button8 = view.findViewById(R.id.button8);
        button8.setOnClickListener(new DecimalButtonListener(button8.getText()));

        button9 = view.findViewById(R.id.button9);
        button9.setOnClickListener(new DecimalButtonListener(button9.getText()));

        decimalButtons = new Button[]{button0, button1, button2, button3, button4,
                button5, button6, button7, button8, button9};

        clearButton = view.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onClearButtonClicked();
                defaultDecimalButtonStates();
            }
        });

        equalButton = view.findViewById(R.id.equalButton);
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onDecimalEqualButtonClicked();
            }
        });

        plusButton = view.findViewById(R.id.plusButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onDecimalPlusButtonClicked();
            }
        });

        minusButton = view.findViewById(R.id.minusButton);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onDecimalMinusButtonClicked();
            }
        });

        plusButton.setEnabled(false);
        minusButton.setEnabled(false);
        plusButton.setBackgroundColor(getResources().getColor(R.color.disabledButtonBackground));
        minusButton.setBackgroundColor(getResources().getColor(R.color.disabledButtonBackground));

        defaultDecimalButtonStates(); // Enables all buttons except button 0

        return view;
    }

    /**
     * Gets all buttons representing decimal values.
     * @return Array of all buttons representing decimal values.
     */
    public Button[] getDecimalButtons() {
        return decimalButtons;
    }

    /**
     * Enables and disables each button representing a roman character, based on the current text
     * on screen. If the current text on screen plus the value represented by a button is an invalid
     * value, the button will be disabled. Additionally, sets the background color of each button
     * according to the button's state.
     * @param currentInput - current text on screen
     */
    public void updateDecimalButtonStates(CharSequence currentInput) {
        boolean validInput;
        Button[] buttons = getDecimalButtons();
        String currentDisplayText = currentInput.toString();
        for (Button button : buttons) {
            validInput = Roman.validInt(
                    Integer.parseInt(currentDisplayText + button.getText().toString())
            );
            if (validInput) {
                button.setEnabled(true);
                button.setBackgroundColor(getResources().getColor(R.color.buttonBackground));
            } else {
                button.setEnabled(false);
                button.setBackgroundColor(getResources().getColor(R.color.disabledButtonBackground));
            }
        }
    }

    /**
     * Enables all buttons representing decimal values. Additionally, sets the background color of
     * each button to the default color.
     */
    public void enableAllDecimalButtons() {
        for (Button button: getDecimalButtons()) {
            button.setEnabled(true);
            button.setBackgroundColor(getResources().getColor(R.color.buttonBackground));
        }
    }

    /**
     * Enables all buttons representing decimal values except for button 0. Sets background color
     * of the buttons accordingly.
     */
    private void defaultDecimalButtonStates() {
        Button[] buttons = getDecimalButtons();
        for (Button button: buttons) {
            button.setEnabled(true);
            button.setBackgroundColor(getResources().getColor(R.color.buttonBackground));
        } buttons[0].setEnabled(false);
        buttons[0].setBackgroundColor(getResources().getColor(R.color.disabledButtonBackground));
        setEnabledEqualButton(false);
    }

    /**
     * Gets activity hosting this fragment. Activity implements the DecimalFragmentListener interface.
     * @return listener of this fragment
     */
    public DecimalFragmentListener getListener() {
        return listener;
    }

    /**
     * Enables or disables the plus and minus buttons based on the boolean passed. Additionally,
     * sets the background color of the buttons according to the state of the buttons.
     * @param x - true to enable the buttons, false to disable the buttons
     */
    public void enableOperationButtons(boolean x) {
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
     * Disables button representing integer value 0, and sets button's background color to
     * disabledButtonBackground color.
     */
    public void disableButton0() {
        getButton0().setEnabled(false);
        getButton0().setBackgroundColor(getResources().getColor(R.color.disabledButtonBackground));
    }

    /**
     * Enables or disables the equal button based on the boolean passed. Additionally, sets the
     * background color of the equal button according to the state of the buttons.
     * @param x - true to enable equal button, false to disable equal button
     */
    public void setEnabledEqualButton(boolean x) {
        getEqualButton().setEnabled(x);
        if (x) getEqualButton().setBackgroundColor(getResources().getColor(R.color.buttonBackground));
        else getEqualButton().setBackgroundColor(getResources().getColor(R.color.disabledButtonBackground));
    }

    /**
     * Get plus button
     * @return plus button
     */
    public Button getPlusButton() {
        return plusButton;
    }

    /**
     * Get minus button
     * @return minus button
     */
    public Button getMinusButton() {
        return minusButton;
    }

    /**
     * Get equal button
     * @return equal button
     */
    public Button getEqualButton() {
        return equalButton;
    }

    /**
     * Get button representing integer value of 0.
     * @return button 0
     */
    public Button getButton0() {
        return button0;
    }
}
