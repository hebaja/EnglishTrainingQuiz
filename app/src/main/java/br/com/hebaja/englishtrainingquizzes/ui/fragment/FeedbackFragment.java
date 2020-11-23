package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.hebaja.englishtrainingquizzes.R;

import static br.com.hebaja.englishtrainingquizzes.Constants.EMAIL_SELECTOR_KEY;
import static br.com.hebaja.englishtrainingquizzes.Constants.ON_EMPTY_MESSAGE_FEEDBACK_MESSAGE;
import static br.com.hebaja.englishtrainingquizzes.Constants.RECIPIENT_EMAIL;
import static br.com.hebaja.englishtrainingquizzes.Constants.START_EMAIL_CLIENT_ACTIVITY_MESSAGE;
import static br.com.hebaja.englishtrainingquizzes.Constants.SUBJECT_EMAIL;

public class FeedbackFragment extends Fragment {

    private EditText editTextInput;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextInput = view.findViewById(R.id.feedback_message_edit_text);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_options_send_feedback, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.feedback_send) {
            sendMail();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendMail() {
        String[] myEmail = {RECIPIENT_EMAIL};
        String subject = SUBJECT_EMAIL;
        String message = editTextInput.getText().toString();

        Intent emailSelectorIntent = configureSelectorToEmailClients();
        final Intent intent = configureEmailClientsIntent(myEmail, subject, message, emailSelectorIntent);
        assertMessageFeedbackIsNotEmpty(message, intent);
    }

    private void assertMessageFeedbackIsNotEmpty(String message, Intent intent) {
        if(!message.isEmpty()) {
            startActivity(Intent.createChooser(intent, START_EMAIL_CLIENT_ACTIVITY_MESSAGE));
        } else {
            Toast.makeText(getContext(), ON_EMPTY_MESSAGE_FEEDBACK_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private Intent configureEmailClientsIntent(String[] myEmail, String subject, String message, Intent emailSelectorIntent) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, myEmail);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setSelector(emailSelectorIntent);
        return intent;
    }

    private Intent configureSelectorToEmailClients() {
        Intent emailSelectorIntent = new Intent(Intent.ACTION_SENDTO);
        emailSelectorIntent.setData(Uri.parse(EMAIL_SELECTOR_KEY));
        return emailSelectorIntent;
    }
}
