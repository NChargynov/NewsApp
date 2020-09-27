package com.example.newsapp.ui.main;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.newsapp.App;
import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.models.Article;
import java.util.List;
import static com.example.newsapp.utils.Config.API_KEY;

public class MainViewModel extends ViewModel {

    MutableLiveData<List<Article>> news = new MutableLiveData<>();
    LiveData<List<Article>> newsData = App.iNewsStorage.getAllLive();
    MutableLiveData<Exception> exception = new MutableLiveData<>();
    private static final String RU = "ru";
    private static final String US = "us";

    void receiveData(int page, int pageSize) {
        App.newsRepository.getNewsHeadlines(RU, API_KEY, page, pageSize,
                new INewsApiClient.NewsCallBack() {
                    @Override
                    public void onSuccess(List<Article> result) {
                        news.setValue(result);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        exception.setValue(e);
                    }
                });
    }
}
