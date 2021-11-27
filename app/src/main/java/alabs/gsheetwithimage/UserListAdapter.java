package alabs.gsheetwithimage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class UserListAdapter extends ArrayAdapter<String> {
    private final String[] uId;
    private final String[] uNames;
    private final String[] uImages;
    private final String[] uCelu;
    private final String[] uMails;
    private final String[] uFacturas;
    private final String[] uPuntos;
    private final String[] uAsesor;
    private Activity context;

    public UserListAdapter(Activity context, String[] uId, String[] uNames,
                           String[] uCelu, String[] uMails,String[] uFacturas,
                           String[] uPuntos,String[] uAsesor, String[] uImages ) {
        super(context, R.layout.list_row, uId);
        this.context = context;
        this.uId = uId;
        this.uNames = uNames;
        this.uCelu = uCelu;
        this.uMails= uMails;
        this.uFacturas=uFacturas;
        this.uPuntos=uPuntos;
        this.uAsesor=uAsesor;
        this.uImages = uImages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View listViewItem = inflater.inflate(R.layout.list_row, null, true);
        TextView textViewId = (TextView) listViewItem.findViewById(R.id.tv_uid);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tv_uname);


        textViewId.setText(uPuntos[position]);
        textViewName.setText(uNames[position]);


        return listViewItem;
    }
}