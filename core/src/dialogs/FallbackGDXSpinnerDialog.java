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
    public GDXSpinnerDialog setPropertyList(ArrayList<Property> list) {
        return this;
    }

    @Override
    public GDXSpinnerDialog setOKButtonText(String text) {
        return this;
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
