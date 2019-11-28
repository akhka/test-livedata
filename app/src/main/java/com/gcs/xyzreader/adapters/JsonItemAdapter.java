package com.gcs.xyzreader.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gcs.xyzreader.DetailsActivity;
import com.gcs.xyzreader.R;
import com.gcs.xyzreader.data.ItemContract;
import com.gcs.xyzreader.models.XYZJson;
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
        final ViewHolder vh = new ViewHolder(view);
        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        ((Activity) context).getWindow().setEnterTransition(fade);
        ((Activity) context).getWindow().setExitTransition(fade);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Intent.ACTION_VIEW, ItemContract.Items.buildItemUri(getItemId(vh.getAdapterPosition())));
                intent.putExtra("xyzobject", getItemId(vh.getAdapterPosition()));*/
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, vh.itemImage, vh.itemImage.getTransitionName());
                /*context.startActivity(intent);*/

                /*XYZJson object = new XYZJson(list.get(vh.getAdapterPosition()).getId(),
                        list.get(vh.getAdapterPosition()).getTitle(),
                        list.get(vh.getAdapterPosition()).getAuthor(),
                        list.get(vh.getAdapterPosition()).getBody(),
                        list.get(vh.getAdapterPosition()).getThumb(),
                        list.get(vh.getAdapterPosition()).getPhoto(),
                        list.get(vh.getAdapterPosition()).getPublishedDate());*/

                //Bundle extras = new Bundle();
                //extras.putParcelable("object", object);
                //extras.putParcelable("option", options.toBundle());


                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id", list.get(vh.getAdapterPosition()).getId());
                context.startActivity(intent, options.toBundle());


            }
        });
        return vh;
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
