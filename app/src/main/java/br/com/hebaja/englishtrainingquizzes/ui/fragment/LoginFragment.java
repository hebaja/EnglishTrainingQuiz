package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.model.User;
import br.com.hebaja.englishtrainingquizzes.retrofit.SearchUser;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText username = view.findViewById(R.id.login_edit_text_username);
        EditText password = view.findViewById(R.id.login_edit_text_password);
        Button button = view.findViewById(R.id.login_button);

        String usernameKey = "henrique";
        String passwordKey = "123";

        

        Future<User> futureUser = new SearchUser().getUser();
        try {
            User user = futureUser.get();
            if(user != null) {
                Log.i("user", "onViewCreated: " + user.getUsername());
                Log.i("user", "onViewCreated: " + user.getPassword());
            } else {
                Log.i("user", "onViewCreated: user is null");
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        button.setOnClickListener(v -> {



            if(username.getText().toString().equals(usernameKey) && password.getText().toString().equals(passwordKey)) {
                Toast.makeText(getContext(), "User logged in", Toast.LENGTH_SHORT).show();
                goToMenuLevelsFragment(view);
            } else {
                Toast.makeText(getContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
            }


        });

    }

    private void goToMenuLevelsFragment(View view) {
        NavController controller = Navigation.findNavController(view);
        NavDirections directions = LoginFragmentDirections.actionLoginToMenuLevels();
        controller.navigate(directions);
    }
}
