package br.com.hebaja.englishtrainingquizzes.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class QuitAppDialog extends AppCompatDialogFragment {

    public static final String QUIT_DIALOG_QUESTION_CONSTANT = "Do you really want to quit?";
    public static final String QUIT_ANSWER_CONSTANT = "Quit";
    public static final String QUIT_CANCEL_ANSWER_CONSTANT = "Cancel";
    private final Activity originActivity;

    public QuitAppDialog(Activity originActivity) {
        this.originActivity = originActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(QUIT_DIALOG_QUESTION_CONSTANT)
                .setPositiveButton(QUIT_ANSWER_CONSTANT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        originActivity.finish();
                    }
                })
                .setNegativeButton(QUIT_CANCEL_ANSWER_CONSTANT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return builder.create();
    }
}
