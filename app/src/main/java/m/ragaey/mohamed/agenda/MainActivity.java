package m.ragaey.mohamed.agenda;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Adabter adabter;
    ArrayList<Dataclass> booksarrayList;
    EditText bookname_filed;
    Button search_btn;
    ProgressBar progressBar;
    ImageView agendaimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookname_filed = findViewById(R.id.book_name_filed);
        search_btn = findViewById(R.id.search_btn);
        progressBar = findViewById(R.id.progressbar);
        agendaimage =findViewById(R.id.book_image);

        progressBar.setVisibility(View.INVISIBLE);

        listView = findViewById(R.id.books_listview);

        booksarrayList = new ArrayList<>();

        adabter = new Adabter(getApplicationContext(),0,booksarrayList);

        listView.setAdapter(adabter);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String bookname = bookname_filed.getText().toString();
                if (bookname.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"please enter book title", Toast.LENGTH_SHORT).show();
                }else
                    {
                        String GOOGLE_BOOKS_API =
                                "https://www.googleapis.com/books/v1/volumes?q=" +bookname ;

                        BooksAsyncTask booksAsyncTask = new BooksAsyncTask();
                        booksAsyncTask.execute(GOOGLE_BOOKS_API);

                        bookname_filed.setText("");
                    }
            }
        });
    }
    public class BooksAsyncTask extends AsyncTask <String,Void,ArrayList<Dataclass>>

    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Dataclass> doInBackground(String... strings)
        {
            if (strings.length < 1 || strings[0] == null )
            {
                return null;
            }
            ArrayList<Dataclass> books =Utils.fetchBooksData(strings [0]);

            return books;
        }

        @Override
        protected void onPostExecute(ArrayList<Dataclass> dataclasses)
      {
          adabter.clear();
          if (dataclasses != null && !dataclasses.isEmpty())
        {
            progressBar.setVisibility(View.INVISIBLE);
            booksarrayList.addAll(dataclasses);
        }

      }
    }
}

