package br.com.hebaja.englishtrainingquizzes.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.hebaja.englishtrainingquizzes.R;

public class FeedbackActivity extends AppCompatActivity {

    public static final String RECIPIENT_EMAIL = "hebaja@gmail.com";
    public static final String SUBJECT_EMAIL = "Feedback from EnglishQuizzes Training user";
    public static final String START_EMAIL_CLIENT_ACTIVITY_MESSAGE = "Choose an email client";
    public static final String ON_EMPTY_MESSAGE_FEEDBACK_MESSAGE = "You can't send empty feedback";
    public static final String EMAIL_SELECTOR_KEY = "mailto:";
    private EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle("Feedback");
        editTextInput = findViewById(R.id.feedback_activity_message_edit_text);
        TextView instructionsTextView = findViewById(R.id.feedback_activity_instructions_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            instructionsTextView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback_send_feedback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_feedback_activity_send) {
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
            finish();
        } else {
            Toast.makeText(this, ON_EMPTY_MESSAGE_FEEDBACK_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private Intent configureEmailClientsIntent(String[] myEmail, String subject, String message, Intent emailSelectorIntent) {
//        final Intent intent = new Intent(Intent.ACTION_SEND);
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