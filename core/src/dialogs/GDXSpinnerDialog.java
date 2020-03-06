package dialogs;

import java.util.ArrayList;

import classes.Property;

public interface GDXSpinnerDialog {
    GDXSpinnerDialog setTitle(String title);
    GDXSpinnerDialog setMessage(String message);
    GDXSpinnerDialog setPropertyList(ArrayList<Property> list);
    GDXSpinnerDialog setOKButtonText(String text);
    // GDXSpinnerDialog setSpinnerDialogListener(SpinnerDialog)
    GDXSpinnerDialog build();
    GDXSpinnerDialog show();
    GDXSpinnerDialog dismiss();
}
