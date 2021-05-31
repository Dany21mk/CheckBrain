package space.mosk.checkbrain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

import space.mosk.checkbrain.ChooseTrue.ChooseTrueActivity;
import space.mosk.checkbrain.Geog.GeogActivity;
import space.mosk.checkbrain.Math.MathActivity;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {

    Context context;
    int[] images;
    String[] strTheme;
    Intent[] activityTheme;

    @NonNull
    @Override
    public ThemeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_theme,parent,false);
        ViewHolder viewHolder = new ViewHolder((view));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeAdapter.ViewHolder holder, int position) {
        holder.rowImage.setImageResource(images[position]);
        holder.rowBtn.setText(strTheme[position]);
        Random rnd = new Random();
        //holder.rowBtn.setBackgroundColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        holder.rowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityTheme[position].addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                view.getContext().startActivity(activityTheme[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView rowImage;
        AppCompatButton rowBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowImage = itemView.findViewById(R.id.btn_theme_img);
            rowBtn = itemView.findViewById(R.id.btn_theme);
        }
    }

    public ThemeAdapter(Context context, int[] images, String[] strTheme) {
        this.context = context;
        this.images = images;
        this.strTheme = strTheme;
        activityTheme = new Intent[]{new Intent(context, MathActivity.class), new Intent(context, ChooseTrueActivity.class), new Intent(context, AccountActivity.class), new Intent(context, GeogActivity.class), new Intent(context, AccountActivity.class), new Intent(context, AccountActivity.class), new Intent(context, AccountActivity.class), new Intent(context, AccountActivity.class)};
    }
}
