package com.example.bukbukbukh.movierating;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Ganapathy Hari Narayan on 1/25/16.
 * This is implemented and basically shows login details, depening on the string that is passed in
 */
public class LoginStatus extends DialogFragment {

    /**
     *
     * @param title The title of the dialog box
     * @return The Dialog Entry
     */
    public static LoginStatus newInstance(int title) {
        final LoginStatus frag = new LoginStatus();
        final Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        final int title = getArguments().getInt("title");

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
