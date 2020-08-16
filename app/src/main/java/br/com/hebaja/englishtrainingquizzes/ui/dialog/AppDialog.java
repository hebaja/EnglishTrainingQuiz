package br.com.hebaja.englishtrainingquizzes.ui.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import br.com.hebaja.englishtrainingquizzes.NavGraphDirections;
import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.ui.activity.MainActivity;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.QuizFragmentDirections;

import static br.com.hebaja.englishtrainingquizzes.Constants.GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUIT_DIALOG_QUESTION_CONSTANT;

public class AppDialog {

    private final Activity mainActivity;
    private final String question;
    private final String confirm;
    private final String cancel;
    private View view;

    public AppDialog(Activity activity, View view, String question, String confirm, String cancel) {
        this.mainActivity = activity;
        this.view = view;
        this.question = question;
        this.confirm = confirm;
        this.cancel = cancel;
    }

//    public AppDialog(Activity activity, String question, String confirm, String cancel) {
//        this.mainActivity = activity;
//        this.question = question;
//        this.confirm = confirm;
//        this.cancel = cancel;
//    }

    public AlertDialog buildDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setMessage(question)
                .setPositiveButton(confirm, (dialogInterface, i) -> {
                    if(question.equals(GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT)){
                        NavController controller = Navigation.findNavController(view);
                        NavDirections directions = QuizFragmentDirections.actionGlobalMenuLevels();
                        controller.navigate(directions);
                    }
                    if(question.equals(QUIT_DIALOG_QUESTION_CONSTANT)) {
                        mainActivity.finish();
                    }
                })
                .setNegativeButton(cancel, (dialogInterface, i) -> {
        });

        return builder.create();
    }

//    public AlertDialog buildDialogActivity() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
//        builder.setMessage(question)
//                .setPositiveButton(confirm, (dialogInterface, i) -> {
//                    if(question.equals(GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT)){
//                        NavController controller = Navigation.findNavController(mainActivity, R.id.main_activity_nav_host);
//                        NavDirections directions = NavGraphDirections.actionGlobalMenuLevels();
//                        controller.navigate(directions);
//
//
//                    }
//                    if(question.equals(QUIT_DIALOG_QUESTION_CONSTANT)) {
//                        mainActivity.finish();
//                    }
//                })
//                .setNegativeButton(cancel, (dialogInterface, i) -> {
//                });
//
//        return builder.create();
//    }
}

