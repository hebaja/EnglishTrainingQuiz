package br.com.hebaja.englishtrainingquizzes.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import br.com.hebaja.englishtrainingquizzes.ui.activity.MenuActivity;

import static br.com.hebaja.englishtrainingquizzes.Constants.GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUIT_DIALOG_QUESTION_CONSTANT;

public class ConfirmActionDialog extends AppCompatDialogFragment {

    private final String question;
    private final String confirm;
    private final String cancel;

    private final Activity originActivity;

    public ConfirmActionDialog(String question, String confirm, String cancel, Activity originActivity) {
        this.question = question;
        this.confirm = confirm;
        this.cancel = cancel;
        this.originActivity = originActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(question)
                .setPositiveButton(confirm, (dialogInterface, i) -> {
                    if (question.equals(GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT)) {
                        originActivity.startActivity(new Intent(originActivity, MenuActivity.class));
                    }
                    originActivity.finish();
                })
                .setNegativeButton(cancel, (dialogInterface, i) -> {
                });
        return builder.create();
    }
}
