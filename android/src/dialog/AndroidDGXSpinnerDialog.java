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

import com.darash.monopoly.R;

import java.util.ArrayList;
import java.util.List;

import classes.Property;
import dialogs.GDXSpinnerDialog;

public class AndroidDGXSpinnerDialog implements GDXSpinnerDialog {
    private Activity activity;
    private TextView titleView;
    private TextView messageView;
    private String title="";
    private String message="";
    private String okLabel="";
    private Spinner spinner;
    private ArrayAdapter<String> adapterSpinner;
    private AlertDialog alertDialog;
    private boolean isBuild = false;

    public AndroidDGXSpinnerDialog(Activity activity) {
        this.activity=activity;
    }

    @Override
    public GDXSpinnerDialog setTitle(String title) {
        this.title=title;
        return this;
    }

    @Override
    public GDXSpinnerDialog setMessage(String message) {
        this.message=message;
        return this;
    }

    @Override
    public GDXSpinnerDialog setPropertyList(ArrayList<Property> list) {
        String[] arrayProperties=new String[list.size()];
        for (int i=0; i<list.size(); i++) {
            arrayProperties[i]=list.get(i).getName();
        }

        adapterSpinner=new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, arrayProperties);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return this;
    }

    @Override
    public GDXSpinnerDialog setOKButtonText(String text) {
        this.okLabel=text;
        return this;
    }

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
                        // TODO
                        Toast.makeText(activity, "Holaa", Toast.LENGTH_LONG).show();
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
