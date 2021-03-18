package br.com.hebaja.englishtrainingquizzes.ui.dialog;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import static br.com.hebaja.englishtrainingquizzes.utils.Constants.GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.LOGOFF_DIALOG_QUESTION_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.QUIT_DIALOG_QUESTION_CONSTANT;

public class AppDialog {

    private final String question;
    private final String confirm;
    private final String cancel;
    private final WhenActionChosenListener listener;

    public AppDialog(String question, String confirm, String cancel, WhenActionChosenListener listener) {
        this.question = question;
        this.confirm = confirm;
        this.cancel = cancel;
        this.listener = listener;
    }

    public AlertDialog buildDialog(FragmentActivity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(question)
                .setPositiveButton(confirm, (dialogInterface, i) -> {
                    if(question.equals(GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT)){
                        listener.whenActionChosen();
                    }
                    if(question.equals(QUIT_DIALOG_QUESTION_CONSTANT)) {
                        listener.whenActionChosen();
                    }
                    if(question.equals(LOGOFF_DIALOG_QUESTION_CONSTANT)) {
                        listener.whenActionChosen();
                    }
                })
                .setNegativeButton(cancel, (dialogInterface, i) -> {
        });

        return builder.create();
    }

    public interface WhenActionChosenListener {
        void whenActionChosen();
    }
}