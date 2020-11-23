package br.com.hebaja.englishtrainingquizzes.ui.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import br.com.hebaja.englishtrainingquizzes.NavGraphDirections;
import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.ButtonNext;
import br.com.hebaja.englishtrainingquizzes.model.WarningView;
import br.com.hebaja.englishtrainingquizzes.ui.activity.MainActivity;
import br.com.hebaja.englishtrainingquizzes.ui.fragment.QuizFragmentDirections;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ButtonNextViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.PositionViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ScoreViewModel;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.WarningViewModel;

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

    public AlertDialog buildDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setMessage(question)
                .setPositiveButton(confirm, (dialogInterface, i) -> {
                    if(question.equals(GO_TO_MAIN_MENU_DIALOG_QUESTION_CONSTANT)){
                        NavController controller = Navigation.findNavController(view);
                        NavDirections directions = QuizFragmentDirections.actionGlobalMenuLevels();
                        controller.navigate(directions);

                        PositionViewModel positionViewModel = new ViewModelProvider((ViewModelStoreOwner) mainActivity).get(PositionViewModel.class);
                        positionViewModel.reset().observe((LifecycleOwner) mainActivity, positionState -> {

                        });

                        ScoreViewModel scoreViewModel = new ViewModelProvider((ViewModelStoreOwner) mainActivity).get(ScoreViewModel.class);
                        scoreViewModel.reset().observe((LifecycleOwner) mainActivity, scoreState -> {

                        });

                        WarningViewModel warningViewModel = new ViewModelProvider((ViewModelStoreOwner) mainActivity).get(WarningViewModel.class);
                        warningViewModel.reset().observe((LifecycleOwner) mainActivity, warningView -> {});

                        ButtonNextViewModel buttonNextViewModel = new ViewModelProvider((ViewModelStoreOwner) mainActivity).get(ButtonNextViewModel.class);
                        buttonNextViewModel.reset().observe((LifecycleOwner) mainActivity, buttonNext -> {});

                    }
                    if(question.equals(QUIT_DIALOG_QUESTION_CONSTANT)) {
                        mainActivity.finish();
                    }
                })
                .setNegativeButton(cancel, (dialogInterface, i) -> {
        });

        return builder.create();
    }
}

