package com.nkdroid.day;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class SearchResultActivity extends AppCompatActivity {

    DictionaryDatabase dictionaryDatabase = new DictionaryDatabase(this);

    TextView textView1;
    ListView lsResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        textView1 = (TextView) findViewById(R.id.tx1);
        lsResult = (ListView) findViewById(R.id.lvS);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            //handle a click on a search suggestion; launch activity to show word
            Intent wordIntent = new Intent(this, WordActivity.class);
            wordIntent.setData(intent.getData());
            startActivity(wordIntent);

        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            //handles a search query
            String query = intent.getStringExtra(SearchManager.QUERY);
            showResults(query);
        }
    }

    /**
     * Searches the dictionary and displays results for the given query.
     *
     * @param query The search query
     */
    private void showResults(String query) {
        Cursor cursor = managedQuery(DictionaryProvider.CONTENT_URI, null, null, new String[]{query}, null);
        if (cursor == null) {
            //There are no results
            textView1.setText(getString(R.string.no_result, new Object[]{query}));
        } else {
            //Display the number of results
            int count = cursor.getCount();
            String countString = getResources().getQuantityString(R.plurals.search_results, count, new Object[]{count, query});
            textView1.setText(countString);

            //Specify the columns we want to display in the result
            String[] from = new String[]{DictionaryDatabase.KEY_WORD,
                    DictionaryDatabase.KEY_DEFINITION};

            //Specify the corresponding layout elements where we want the columns to go
            int[] to = new int[]{R.id.word, R.id.definition};

            //create a simple cursor adapter for the definitions and apply theme to the ListView
            SimpleCursorAdapter words = new SimpleCursorAdapter(this, R.layout.result, cursor, from, to);

            lsResult.setAdapter(words);

            //Define the on-click listener for the list items
            lsResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Build the Intent used to open WordActivity with a specific word Uri
                    Intent wordIntent = new Intent(getApplicationContext(), WordActivity.class);
                    Uri data = Uri.withAppendedPath(DictionaryProvider.CONTENT_URI, String.valueOf(id));
                    wordIntent.setData(data);
                    startActivity(wordIntent);
                }
            });

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.ic_search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_search:
                onSearchRequested();
                return true;
            default:
                return false;
        }
    }

}
