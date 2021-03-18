package br.com.hebaja.englishtrainingquizzes.retrofit.service;

import br.com.hebaja.englishtrainingquizzes.model.FileChecksum;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChecksumService {

    @GET("api/file_checksum")
    Call<FileChecksum> getFileChecksum(@Query("file") String file);

}
