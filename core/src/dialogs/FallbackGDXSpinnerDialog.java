package dialogs;

import java.util.ArrayList;

import classes.Property;

public class FallbackGDXSpinnerDialog implements GDXSpinnerDialog {
    @Override
    public GDXSpinnerDialog setTitle(String title) {
        return this;
    }

    @Override
    public GDXSpinnerDialog setMessage(String message) {
        return this;
    }

    @Override
    public GDXSpinnerDialog setList(ArrayList<Property> list) {
        return this;
    }

    @Override
    public GDXSpinnerDialog setOKButtonText(String text) {
        return this;
    }

    @Override
    public GDXSpinnerDialog setCancelButtonText(String text) {
        return this;
    }

    @Override
    public GDXSpinnerDialog setSpinnerDialogListener(SpinnerDialogListener listener) {
        return this;
    }

    @Override
    public String getSelectedItem() {
        return "";
    }

    @Override
    public int getSelectedPosition() {
        return 0;
    }

    @Override
    public GDXSpinnerDialog build() {
        return this;
    }

    @Override
    public GDXSpinnerDialog show() {
        return this;
    }

    @Override
    public GDXSpinnerDialog dismiss() {
        return this;
    }
}
