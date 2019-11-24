package com.gcs.testuielements.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gcs.testuielements.R;
import com.gcs.testuielements.models.XYZJson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JsonItemAdapter extends RecyclerView.Adapter<JsonItemAdapter.ViewHolder> {

    private static final String TAG = "JsonItemAdapter";
    private Context context;

    private List<XYZJson> list;

    public JsonItemAdapter(Context context, List<XYZJson> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.json_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (list.get(position).getThumb() != null){
            try{
                holder.bitmap = new AsyncTask<Void, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Void... voids) {
                        try{
                            return Picasso.get().load(list.get(position).getThumb()).get();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute().get();
                if (holder.bitmap != null){
                    holder.itemImage.setImageBitmap(holder.bitmap);
                    holder.bitmap = null;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        holder.itemTitle.setText(list.get(position).getTitle());
        holder.itemAuthor.setText(list.get(position).getAuthor());


    }

    @Override
    public int getItemCount() {
        if (list.size() == 0 || list == null){
            return 0;
        }
        else {
            return list.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_imageView)
        ImageView itemImage;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_author)
        TextView itemAuthor;

        public Bitmap bitmap = null;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
