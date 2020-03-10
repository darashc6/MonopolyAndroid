package dialogs;

/**
 * Interface using as a listener for the spinner dialog buttons
 */
public interface SpinnerDialogListener {
    /**
     * Function used when the positive button is pressed
     */
    void confirm();

    /**
     * Function used when the negative button is pressed
     */
    void cancel();
}
