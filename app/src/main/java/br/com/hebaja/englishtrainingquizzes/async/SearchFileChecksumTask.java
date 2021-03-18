package br.com.hebaja.englishtrainingquizzes.async;

import android.os.AsyncTask;

import br.com.hebaja.englishtrainingquizzes.daos.FileChecksumDao;
import br.com.hebaja.englishtrainingquizzes.model.FileChecksum;

public class SearchFileChecksumTask extends AsyncTask<Void, Void, FileChecksum> {

    private final FileChecksumDao dao;
    private final String fileName;
    private final WhenFileChecksumReadyListener listener;

    public SearchFileChecksumTask(FileChecksumDao dao, String fileName, WhenFileChecksumReadyListener listener) {
        this.dao = dao;
        this.fileName = fileName;
        this.listener = listener;
    }

    @Override
    protected FileChecksum doInBackground(Void... voids) {
        return dao.findByFileName(fileName);
    }

    @Override
    protected void onPostExecute(FileChecksum fileChecksum) {
        super.onPostExecute(fileChecksum);
        listener.whenReady(fileChecksum);
    }

    public interface WhenFileChecksumReadyListener {
        void whenReady(FileChecksum fileChecksum);
    }

}
