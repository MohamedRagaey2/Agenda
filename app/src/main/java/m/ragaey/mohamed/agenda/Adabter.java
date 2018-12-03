package m.ragaey.mohamed.agenda;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adabter extends ArrayAdapter<Dataclass> {

    ImageView book_image;
    TextView book_name,book_author;
    public Adabter(@NonNull Context context, int resource, ArrayList<Dataclass> books)
    {
        super(context, resource,books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView ==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }
        Dataclass dataclass = getItem(position);

        book_image =convertView.findViewById(R.id.book_image);
        book_name =convertView.findViewById(R.id.book_name);
        book_author =convertView.findViewById(R.id.book_author);

        if (dataclass != null)
        {
            book_name.setText(dataclass.getBookname());
            book_author.setText(dataclass.getBookauthor());

            Picasso
                    .get()
                    .load(dataclass.getImageurl())
                    .placeholder(R.drawable.book)
                    .into(book_image);
        }

        return convertView;

    }
}
