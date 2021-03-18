package br.com.hebaja.englishtrainingquizzes.async;

import android.os.AsyncTask;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.daos.FileChecksumDao;
import br.com.hebaja.englishtrainingquizzes.firebase.EtqMessagingService;
import br.com.hebaja.englishtrainingquizzes.model.FileChecksum;

public class DeleteAllOldFileChecksumTask extends AsyncTask<Void, Void, Void> {

    private final List<String> files;
    private final FileChecksumDao dao;

    public DeleteAllOldFileChecksumTask(List<String> files, FileChecksumDao dao) {
        this.files = files;
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if(files != null) {
            dao.deleteList(files);
        }
        return null;
    }
}
