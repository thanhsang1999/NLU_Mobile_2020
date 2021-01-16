package com.example.mobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mobile.activity.LogInActivity;

public class ExitConfirmDialogFragment extends DialogFragment {
    public static final String ARG_TITLE = "ExitConfirmDialogFragment.Title";
    public static final String ARG_MESSAGE = "ExitConfirmDialogFragment.Message";


    public ExitConfirmDialogFragment() {
        

    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = args.getString(ARG_TITLE);
        String message = args.getString(ARG_MESSAGE);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)

                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ExitConfirmDialogFragment.this.getActivity().finish();
                    }
                })
                .setPositiveButton("Log In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ExitConfirmDialogFragment.this.getActivity(), LogInActivity.class);
                        ExitConfirmDialogFragment.this.getActivity().startActivity(intent);
                        ExitConfirmDialogFragment.this.getActivity().finish();
                    }
                });
        AlertDialog arlertDialog=alertDialog.create();
        arlertDialog.setCanceledOnTouchOutside(true);
        return arlertDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
