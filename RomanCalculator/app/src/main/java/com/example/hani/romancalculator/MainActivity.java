/**
 * @Author Hani Ly 073142739@gapps.yrdsb.ca
 */

package com.example.hani.romancalculator;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements DisplayScreenFragment.DisplayFragmentListener,
        DecimalFragment.DecimalFragmentListener, RomanFragment.RomanFragmentListener {

    private FragmentManager fragmentManager; // Used to manage fragments
    private DisplayScreenFragment displayScreenFragment;
    private DecimalFragment decimalFragment;
    private RomanFragment romanFragment;

    // Used by display fragment to indicate which fragments to switch
    public static CharSequence DECIMAL_TO_ROMAN = "int_to_roman";
    public static CharSequence ROMAN_TO_DECIMAL = "roman_to_int";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        displayScreenFragment = new DisplayScreenFragment();
        decimalFragment = new DecimalFragment();
        romanFragment = new RomanFragment();

        fragmentManager.beginTransaction()
                .add(R.id.display_screen_container, displayScreenFragment)
                .commit();
        fragmentManager.beginTransaction()
                .add(R.id.keyboard_container, decimalFragment)
                .commit();
    }

    /**
     * Method is called by DisplayScreenFragment to tell MainActivity to switch fragments for
     * keyboard_container. Input indicates which fragment to switch to.
     * @param input - MainActivity.ROMAN_TO_DECIMAL to switch from RomanFragment to DecimalFragment,
     *              MainActivity.DECIMAL_TO_ROMAN for the opposite.
     */
    @Override
    public void onSwitch(CharSequence input) {
        if (input.equals(ROMAN_TO_DECIMAL)) {
            switchToRoman();
        } else if (input.equals(DECIMAL_TO_ROMAN)) {
            switchToDecimal();
        }
    }

    /**
     * Switches DecimalFragment in keyboard_container to RomanFragment. Additionally, clears text in
     * TextView in DisplayScreenFragment.
     */
    public void switchToRoman() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.keyboard_container, getRomanFragment());
        fragmentTransaction.commit();
        getDisplayScreenFragment().setDecimalToRoman(false);
        getDisplayScreenFragment().clearDisplayText();
    }

    /**
     * Switches RomanFragment in keyboard_container to DecimalFragment. Additionally, clears text in
     * TextView in DisplayScreenFragment.
     */
    public void switchToDecimal() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.keyboard_container, getDecimalFragment());
        fragmentTransaction.commit();
        getDisplayScreenFragment().setDecimalToRoman(true);
        getDisplayScreenFragment().clearDisplayText();
    }

    /**
     * Called by DecimalFragment when a button representing a decimal value is clicked. Value of button
     * clicked is sent to DisplayScreenFragment to update text. DecimalFragment enables/disables buttons
     * based on current text in DisplayScreenFragment to prevent invalid inputs. Plus and minus buttons
     * are also enabled or disabled depending on current text and memory in DisplayScreenFragment.
     * @param input - value of decimal button clicked
     */
    @Override
    public void onDecimalButtonClicked(CharSequence input) {
        getDisplayScreenFragment().updateDisplayText(input);
        getDecimalFragment().updateDecimalButtonStates(getDisplayScreenFragment().getCurrentDisplayText());
        getDecimalFragment().enableOperationButtons(getDisplayScreenFragment().isMemoryEmpty());
        getDecimalFragment().setEnabledEqualButton(true);
    }

    /**
     * Called by DecimalFragment when clear button is clicked. MainActivity tells DisplayScreenFragment
     * to clear display text.
     */
    @Override
    public void onClearButtonClicked() {
        getDisplayScreenFragment().clearDisplayText();
    }

    /**
     * Called by DecimalFragment when plus button is clicked. MainActivity tells DisplayScreenFragment
     * to clear and store current display text for addition later on. DecimalFragment enables all
     * decimal buttons and operation buttons.
     */
    @Override
    public void onDecimalPlusButtonClicked() {
        getDisplayScreenFragment().storeTextForAddition();
        getDecimalFragment().enableAllDecimalButtons();
        getDecimalFragment().enableOperationButtons(false);
        getDecimalFragment().setEnabledEqualButton(false);
    }

    /**
     * Called by DecimalFragment when minus button is clicked. MainActivity tells DisplayScreenFragment
     * to clear and store current display text for subtraction later on. DecimalFragment enables all
     * decimal buttons and operation buttons.
     */
    @Override
    public void onDecimalMinusButtonClicked() {
        getDisplayScreenFragment().storeTextForSubtraction();
        getDecimalFragment().enableAllDecimalButtons();
        getDecimalFragment().enableOperationButtons(false);
        getDecimalFragment().setEnabledEqualButton(false);
    }

    /**
     * Called by DecimalFragment when equal button is clicked. MainActivity tells DisplayScreenFragment
     * to convert current display text (and conduct any operations in memory) to Roman. All decimal
     * buttons and operation buttons are enabled afterwards. Decimal button 0 is disabled.
     */
    @Override
    public void onDecimalEqualButtonClicked() {
        getDisplayScreenFragment().convertDisplayText();
        getDecimalFragment().enableAllDecimalButtons();
        getDecimalFragment().enableOperationButtons(false);
        getDecimalFragment().disableButton0();
        getDecimalFragment().setEnabledEqualButton(false);
    }

    /**
     * Called by RomanFragment when a button representing a roman value is clicked. Value of button
     * clicked is sent to DisplayScreenFragment to update text. RomanFragment enables/disables buttons
     * based on current text in DisplayScreenFragment to prevent invalid inputs. Plus and minus buttons
     * are also enabled or disabled depending on current text and memory in DisplayScreenFragment.
     * @param input - value of decimal button clicked
     */
    @Override
    public void onRomanButtonClicked(CharSequence input) {
        getDisplayScreenFragment().updateDisplayText(input);
        getRomanFragment().updateRomanButtonStates(getDisplayScreenFragment().getCurrentDisplayText());
        getRomanFragment().enableAllOperationButtons(getDisplayScreenFragment().isMemoryEmpty());
        getRomanFragment().setEnabledEqualButton(true);
    }

    /**
     * Called by RomanFragment when plus button is clicked. MainActivity tells DisplayScreenFragment
     * to clear and store current display text for addition later on. RomanFragment enables all
     * decimal buttons and operation buttons.
     */
    @Override
    public void onRomanPlusButtonClicked() {
        getDisplayScreenFragment().storeTextForAddition();
        getRomanFragment().enableAllRomanButtons();
        getRomanFragment().enableAllOperationButtons(false);
        getRomanFragment().setEnabledEqualButton(false);
    }

    /**
     * Called by RomanFragment when minus button is clicked. MainActivity tells DisplayScreenFragment
     * to clear and store current display text for subtraction later on. RomanFragment enables all
     * decimal buttons and operation buttons.
     */
    @Override
    public void onRomanMinusButtonClicked() {
        getDisplayScreenFragment().storeTextForSubtraction();
        getRomanFragment().enableAllRomanButtons();
        getRomanFragment().enableAllOperationButtons(false);
        getRomanFragment().setEnabledEqualButton(false);
    }

    /**
     * Called by RomanFragment when equal button is clicked. MainActivity tells DisplayScreenFragment
     * to convert current display text (and conduct any operations in memory) to Decimal. All roman
     * buttons and operation buttons are enabled afterwards.
     */
    @Override
    public void onRomanEqualButtonClicked() {
        getDisplayScreenFragment().convertDisplayText();
        getRomanFragment().enableAllRomanButtons();
        getRomanFragment().enableAllOperationButtons(false);
        getRomanFragment().setEnabledEqualButton(false);
    }

    /**
     * Gets DisplayScreenFragment.
     * @return DisplayScreenFragment
     */
    public DisplayScreenFragment getDisplayScreenFragment() {
        return displayScreenFragment;
    }

    /**
     * Get DecimalFragment.
     * @return DecimalFragment
     */
    public DecimalFragment getDecimalFragment() {
        return decimalFragment;
    }

    /**
     * Get RomanFragment.
     * @return RomanFragment
     */
    public RomanFragment getRomanFragment() {
        return romanFragment;
    }
}
