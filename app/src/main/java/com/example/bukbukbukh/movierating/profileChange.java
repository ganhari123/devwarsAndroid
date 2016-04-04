package com.example.bukbukbukh.movierating;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Created by Ganapathy Hari Narayan on 2/15/16.
 * A class to display a dialog related to profile changes
 * Yet to be implemented completely
 */
public class ProfileChange extends DialogFragment {

    //String usern;

    /**
     *
     * @param title The title of the dialog
     * @param username The username passed into dialog box
     * @return profileChange dialog
     */
    public static ProfileChange newInstance(int title, String username) {
        final ProfileChange frag = new ProfileChange();
        final Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        final int title = getArguments().getInt("title");
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflater.inflate(R.layout.change_password_layout, null));
        builder.setTitle(title)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
