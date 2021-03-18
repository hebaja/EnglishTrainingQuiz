package br.com.hebaja.englishtrainingquizzes.async;

import android.os.AsyncTask;

import br.com.hebaja.englishtrainingquizzes.daos.FileChecksumDao;
import br.com.hebaja.englishtrainingquizzes.model.FileChecksum;

public class SaveFileChecksumTask extends AsyncTask<Void, Void, Void> {

    private final FileChecksum fileChecksum;
    private final FileChecksumDao dao;

    public SaveFileChecksumTask(FileChecksum fileChecksum, FileChecksumDao dao) {
        this.fileChecksum = fileChecksum;
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.save(fileChecksum);
        return null;
    }
}
