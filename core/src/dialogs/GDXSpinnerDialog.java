package dialogs;

import java.util.ArrayList;

import classes.Property;

public interface GDXSpinnerDialog {
    GDXSpinnerDialog setTitle(String title);
    GDXSpinnerDialog setMessage(String message);
    GDXSpinnerDialog setPropertyList(ArrayList<Property> list, boolean isMortgaging);
    GDXSpinnerDialog setOKButtonText(String text);
    GDXSpinnerDialog setCancelButtonText(String text);
    GDXSpinnerDialog setSpinnerDialogListener(SpinnerDialogListener listener);
    String getSelectedItem();
    int getSelectedPosition();
    GDXSpinnerDialog build();
    GDXSpinnerDialog show();
    GDXSpinnerDialog dismiss();
}
