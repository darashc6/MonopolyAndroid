package dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.darash.monopoly.R;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import classes.Property;
import dialogs.GDXSpinnerDialog;
import dialogs.SpinnerDialogListener;

/**
 * Class displaying a custom AlertDialog, in this case showing with a spinner
 * @author Darash
 */
public class AndroidDGXSpinnerDialog implements GDXSpinnerDialog {
    private Activity activity; //
    private TextView titleView;
    private TextView messageView;
    private String title="";
    private String message="";
    private String okLabel="";
    private String cancelLabel="";
    private Spinner spinner;
    private SpinnerDialogListener listener;
    private ArrayAdapter<String> adapterSpinner;
    private AlertDialog alertDialog;
    private boolean isBuild = false;

    public AndroidDGXSpinnerDialog(Activity activity) {
        this.activity=activity;
    }

    /**
     * Sets the title of the dialog
     * @param title Title of the dialog
     * @return Same instance the method was called from
     */
    @Override
    public GDXSpinnerDialog setTitle(String title) {
        this.title=title;
        return this;
    }

    /**
     * Sets the message of the dialog
     * @param message Message of the dialog
     * @return Same instance the method was called from
     */
    @Override
    public GDXSpinnerDialog setMessage(String message) {
        this.message=message;
        return this;
    }

    /**
     * Adds the list to the spinner adapter
     * @param list ArrayList of Property
     * @param propertiesMortgaged true if list contains properties already mortgaged, false if otherwise
     * @return Same instance the method was called from
     */
    @Override
    public GDXSpinnerDialog setList(ArrayList<Property> list, boolean propertiesMortgaged) {
        ArrayList<String> arrayProperties=new ArrayList<>();
        for (int i=0; i<list.size(); i++) {
            if (propertiesMortgaged) {
                arrayProperties.add(list.get(i).getName()+" - Valor: "+list.get(i).getRedeemPrice()+"€");
            } else {
                arrayProperties.add(list.get(i).getName()+" - Valor: "+list.get(i).getMortgagePrice()+"€");
            }
        }

        adapterSpinner=new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, arrayProperties);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return this;
    }

    /**
     * Sets the text for the positive button
     * @param text Text for the positive button
     * @return Same instance the method was called from
     */
    @Override
    public GDXSpinnerDialog setOKButtonText(String text) {
        this.okLabel=text;
        return this;
    }

    /**
     * Sets the text for the negative button
     * @param text Text for the negative button
     * @return Same instance the method was called from
     */
    @Override
    public GDXSpinnerDialog setCancelButtonText(String text) {
        this.cancelLabel=text;
        return this;
    }

    /**
     * Sets the listener for the dialog
     * @param listener Listener of the dialog
     * @return Same instance the method was called from
     */
    @Override
    public GDXSpinnerDialog setSpinnerDialogListener(SpinnerDialogListener listener) {
        this.listener=listener;
        return this;
    }

    /**
     * Function returning the selected item of the spinner
     * @return Selected item of the spinner as a String
     */
    @Override
    public String getSelectedItem() {
        return (String) spinner.getSelectedItem();
    }

    /**
     * Function returning the position of the selected item
     * @return Position of the selected item as Integer
     */
    @Override
    public int getSelectedPosition() {
        return spinner.getSelectedItemPosition();
    }

    /**
     * Builds the dialog
     * @return Same instance the method was called from
     */
    @Override
    public GDXSpinnerDialog build() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                View view= LayoutInflater.from(activity).inflate(R.layout.gdxdialog_spinner, null);
                builder.setView(view);

                titleView=view.findViewById(R.id.gdxDialogsEnterTitle);
                messageView=view.findViewById(R.id.gdxDialogsEnterMessage);
                spinner=view.findViewById(R.id.gdxDialogSpinner);
                titleView.setText(title);
                messageView.setText(message);
                spinner.setAdapter(adapterSpinner);
                builder.setCancelable(false);

                builder.setPositiveButton(okLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener!=null) {
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    listener.confirm();
                                }
                            });
                        }
                    }
                });

                builder.setNegativeButton(cancelLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.cancel();
                        if (listener!=null) {
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    listener.cancel();
                                }
                            });
                        }
                    }
                });

                alertDialog=builder.create();
                isBuild=true;
            }
        });

        while (!isBuild) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return this;
    }

    /**
     * Shows the dialog
     * @return Same instance the method was called from
     */
    @Override
    public GDXSpinnerDialog show() {
        if (alertDialog==null || !isBuild) {
            throw new RuntimeException(GDXSpinnerDialog.class.getSimpleName() + " has not been build. Use build() before" +
                    " show().");
        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
        return this;
    }

    /**
     * Dismisses the dialog
     * @return Same instance the method was called from
     */
    @Override
    public GDXSpinnerDialog dismiss() {
        if (alertDialog==null || !isBuild) {
            throw new RuntimeException(GDXSpinnerDialog.class.getSimpleName() + " has not been build. Use build() " +
                    "before dismiss().");
        }
        alertDialog.dismiss();
        return this;
    }
}
