package br.com.hebaja.englishtrainingquizzes.ui.recyclerview.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.Task;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.ButtonsDisableViewModel;

public class ButtonOptionListAdapter extends RecyclerView.Adapter<ButtonOptionListAdapter.OptionViewHolder> {
    private final FragmentActivity mainActivity;
    private final List<Task.Option> options;
    private final ButtonsDisableViewModel buttonsDisableViewModel;
    private final LifecycleOwner viewLifeCycleOwner;
    private final ButtonOptionListener listener;
    private final List<Button> buttons = new ArrayList<>();

    public ButtonOptionListAdapter(FragmentActivity mainActivity,
                                   List<Task.Option> options,
                                   ButtonsDisableViewModel buttonsDisableViewModel,
                                   LifecycleOwner viewLifeCycleOwner,
                                   ButtonOptionListener listener) {
        this.mainActivity = mainActivity;
        this.options = options;
        this.buttonsDisableViewModel = buttonsDisableViewModel;
        this.viewLifeCycleOwner = viewLifeCycleOwner;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        final Button buttonCreated = (Button) LayoutInflater.from(mainActivity).inflate(R.layout.quiz_button_option_list_item, parent, false);
        buttons.add(buttonCreated);
        return new OptionViewHolder(buttonCreated);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OptionViewHolder holder, int position) {
        holder.binds(options, position, listener, buttons, buttonsDisableViewModel, viewLifeCycleOwner);
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public static class OptionViewHolder extends RecyclerView.ViewHolder {
        private final Button buttonCreated;

        private OptionViewHolder(View itemView) {
            super(itemView);
            this.buttonCreated = (Button) itemView;
        }

        public void binds(List<Task.Option> options, int position, ButtonOptionListener listener, List<Button> buttons, ButtonsDisableViewModel buttonsDisableViewModel, LifecycleOwner viewLifeCycleOwner) {
            buttonCreated.setText(options.get(position).getPrompt());
            buttonsDisableViewModel.get().observe(viewLifeCycleOwner, disabled -> {
                if(disabled) buttonCreated.setEnabled(false);
            });
            buttonCreated.setOnClickListener(view -> {
                listener.whenClicked(position);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    buttons.forEach(button -> button.setEnabled(false));
                }
                buttonsDisableViewModel.set();
            });
        }
    }

    public interface ButtonOptionListener {
        void whenClicked(int chosenOption);
    }
}
