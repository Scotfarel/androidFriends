package ru.park.friends.newsreader.news.user;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.park.friends.newsreader.news.NewsAPI;

public class UserRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private MediatorLiveData<List<NewsAPI.Source>> subscriptions = new MediatorLiveData<>();

    public LiveData<List<NewsAPI.Source>> getUserSubscriptions() {

        firebaseFirestore.collection("subscriptions")
                .whereEqualTo("userID", firebaseAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<NewsAPI.Source> sources = new ArrayList();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewsAPI.Source source = new NewsAPI.Source();
                                source.id = document.getData().get("sourceID").toString();
                                source.name = document.getData().get("sourceName").toString();
                                sources.add(source);
                            }
                            subscriptions.postValue(sources);
                        } else {
                            Log.w("MY_APP", "Error getting documents.", task.getException());
                        }
                    }
                });

        return subscriptions;
    }

    public void subscribeOnSource(NewsAPI.Source source) {
        Map<String, Object> subscription = new HashMap<>();
        subscription.put("userID", firebaseAuth.getUid());
        subscription.put("sourceID", source.id);
        subscription.put("sourceName", source.name);

        firebaseFirestore.collection("subscriptions")
                .add(subscription)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("MY_APP", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MY_APP", "Error adding document: " + e);
                    }
                });
    }

    public void unsubscribeFromSource(NewsAPI.Source source) {
        firebaseFirestore.collection("subscriptions")
                .whereEqualTo("userID", firebaseAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<NewsAPI.Source> sources = new ArrayList();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("MY_APP", "Get source from firebase: " + document.getData().get("sourceID").toString());
                                if (document.getData().get("sourceID").toString().equals(source.id)) {
                                    Log.d("MY_APP", "Find!!!");
                                    deleteSource(document.getId());
                                    break;
                                }
                            }
                        } else {
                            Log.w("MY_APP", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void deleteSource(String documentID) {
        firebaseFirestore.collection("subscriptions")
                .document(documentID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("MY_APP", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("MY_APP", "Error deleting document", e);
                    }
                });
    }

    public void saveArticle(Context context, NewsAPI.Article article) {
        NewsDatabaseHelper databaseHelper = new NewsDatabaseHelper(context);
        databaseHelper.saveArticle(article);
    }

    public List<NewsAPI.Article> getAllSavedArticles(Context context){
        NewsDatabaseHelper databaseHelper = new NewsDatabaseHelper(context);
        Cursor cursor = databaseHelper.getAllSavedArticles();

        ArrayList<NewsAPI.Article> articles = new ArrayList();

        while (cursor.moveToNext()){
            NewsAPI.Article article = new NewsAPI.Article();
            article.source = new NewsAPI.Source();
            article.source.id = cursor.getString(0);
            article.source.name = cursor.getString(1);
            article.title = cursor.getString(2);
            article.author = cursor.getString(3);
            article.description = cursor.getString(4);
            article.url = cursor.getString(5);
            article.urlToImage = cursor.getString(6);
            article.content = cursor.getString(8);

            articles.add(article);
        }

        return articles;
    }
}
