package dialogs;

import java.util.ArrayList;

import classes.Property;

/**
 * Interface used to create a custom GDXDialog with a Spinner containing a list of properties
 */
public interface GDXSpinnerDialog {
    /**
     * Sets the title of the dialog
     * @param title Title of the dialog
     * @return Same instance the method was called from
     */
    GDXSpinnerDialog setTitle(String title);

    /**
     * Sets the message of the dialog
     * @param message Message of the dialog
     * @return Same instance the method was called from
     */
    GDXSpinnerDialog setMessage(String message);

    /**
     * Adds the list to the spinner adapter
     * @param list ArrayList of Property
     * @param propertiesMortgaged true if list contains properties already mortgaged, false if otherwise
     * @return Same instance the method was called from
     */
    GDXSpinnerDialog setList(ArrayList<Property> list, boolean propertiesMortgaged);

    /**
     * Sets the text for the positive button
     * @param text Text for the positive button
     * @return Same instance the method was called from
     */
    GDXSpinnerDialog setOKButtonText(String text);

    /**
     * Sets the text for the negative button
     * @param text Text for the negative button
     * @return Same instance the method was called from
     */
    GDXSpinnerDialog setCancelButtonText(String text);

    /**
     * Sets the listener for the dialog
     * @param listener Listener of the dialog
     * @return Same instance the method was called from
     */
    GDXSpinnerDialog setSpinnerDialogListener(SpinnerDialogListener listener);

    /**
     * Function returning the selected item of the spinner
     * @return Selected item of the spinner as a String
     */
    String getSelectedItem();

    /**
     * Function returning the position of the selected item
     * @return Position of the selected item as Integer
     */
    int getSelectedPosition();

    /**
     * Builds the dialog
     * @return Same instance the method was called from
     */
    GDXSpinnerDialog build();

    /**
     * Shows the dialog
     * @return Same instance the method was called from
     */
    GDXSpinnerDialog show();

    /**
     * Dismisses the dialog
     * @return Same instance the method was called from
     */
    GDXSpinnerDialog dismiss();
}
