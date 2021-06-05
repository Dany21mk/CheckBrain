package space.mosk.checkbrain;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

import space.mosk.checkbrain.Games.Game1Activity;
import space.mosk.checkbrain.Games.Game2Activity;
import space.mosk.checkbrain.Games.Game3Activity;
import space.mosk.checkbrain.Games.GameActivity;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    Context context;
    int[] images;
    String[] strTheme;
    Intent[] activityTheme;

    @NonNull
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_service,parent,false);
        ViewHolder viewHolder = new ViewHolder((view));
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull GameAdapter.ViewHolder holder, int position) {
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

    public GameAdapter(Context context, int[] images, String[] strTheme) {
        this.context = context;
        this.images = images;
        this.strTheme = strTheme;
        activityTheme = new Intent[]{new Intent(context, Game1Activity.class), new Intent(context, Game2Activity.class), new Intent(context, Game3Activity.class)};
    }
}
