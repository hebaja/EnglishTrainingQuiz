package br.com.hebaja.englishtrainingquizzes.firebase;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.async.DeleteAllOldFileChecksumTask;
import br.com.hebaja.englishtrainingquizzes.daos.FileChecksumDao;
import br.com.hebaja.englishtrainingquizzes.database.EtqDatabase;
import br.com.hebaja.englishtrainingquizzes.model.FileChecksum;
import br.com.hebaja.englishtrainingquizzes.ui.activity.MainActivity;

public class EtqMessagingService extends FirebaseMessagingService {

    @SuppressLint("WrongThread")
    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            final FileChecksumDao dao = EtqDatabase.getInstance(getApplicationContext()).getFileChecksumDao();
            List<String> fileNames = new ArrayList<>();

            final String jsonArrayFile = remoteMessage.getData().get("files");

            final JsonParser parser = new JsonParser();
            if(jsonArrayFile != null) {
                final JsonElement parsedElement = parser.parse(jsonArrayFile);
                final JsonArray jsonArray = parsedElement.getAsJsonArray();

                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    final FileChecksum fileChecksum = new Gson().fromJson(jsonObject, FileChecksum.class);
                    fileNames.add(fileChecksum.getFileName());
                }
            }
            new DeleteAllOldFileChecksumTask(fileNames, dao).execute();
        }
    }
}