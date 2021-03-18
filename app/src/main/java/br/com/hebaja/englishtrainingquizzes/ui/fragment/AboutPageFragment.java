package br.com.hebaja.englishtrainingquizzes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsSupportFragment;

import br.com.hebaja.englishtrainingquizzes.R;

import static br.com.hebaja.englishtrainingquizzes.utils.Constants.ABOUT_PAGE_APP_DESCRIPTION;

public class AboutPageFragment extends Fragment {

    public static final String APP_NAME = "English Training Quizzes";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LibsSupportFragment fragment = new LibsBuilder()
                .withFields(R.string.class.getFields())
                .withActivityTitle(String.valueOf(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR))
                .withAboutAppName(APP_NAME)
                .withAboutVersionShownName(true)
                .withAboutVersionShownCode(true)
                .withAboutDescription(ABOUT_PAGE_APP_DESCRIPTION)
                .withAboutIconShown(true)
                .withShowLoadingProgress(false)
                .supportFragment();
        requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.about_page_parent, fragment).commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_page, container, false);
    }
}