package com.example.rxjavaudemy01.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.rxjavaudemy01.R;
import com.example.rxjavaudemy01.model.infoMain;
import com.example.rxjavaudemy01.view.Part01Activity;
import com.example.rxjavaudemy01.view.Part02Activity;
import com.example.rxjavaudemy01.view.Part03Activity;
import com.example.rxjavaudemy01.view.Part04Activity;
import com.example.rxjavaudemy01.view.Part05Activity;
import com.example.rxjavaudemy01.view.Part06Activity;
import com.example.rxjavaudemy01.view.Part07Activity;
import com.example.rxjavaudemy01.view.Part08Activity;
import com.example.rxjavaudemy01.view.Part09Activity;
import com.example.rxjavaudemy01.view.Part10Activity;
import com.example.rxjavaudemy01.view.Part11Activity;
import com.example.rxjavaudemy01.view.Part12Activity;
import com.example.rxjavaudemy01.view.Part13Activity;
import com.example.rxjavaudemy01.view.Part14Activity;
import com.example.rxjavaudemy01.view.Part15Activity;
import com.example.rxjavaudemy01.view.Part16Activity;
import com.example.rxjavaudemy01.view.Part17Activity;
import com.example.rxjavaudemy01.view.Part18Activity;
import com.example.rxjavaudemy01.view.Part19Activity;
import com.example.rxjavaudemy01.view.Part20Activity;
import com.example.rxjavaudemy01.view.Part23Activity;
import com.example.rxjavaudemy01.view.part22.Part22Activity;
import com.example.rxjavaudemy01.view.part21.Part21Activity;
import com.example.rxjavaudemy01.view.part24.Part24Activity;
import com.example.rxjavaudemy01.view.part25.Part25Activity;

import java.util.Collections;
import java.util.List;


public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {
    private LayoutInflater inflater;
    List<infoMain> data = Collections.emptyList();//in tori tarif mikonim ke agar list khali bood Error NullPointExeption nadagad
    private Context context;



    public AdapterMain(Context context, List<infoMain> data){
        inflater= LayoutInflater.from(context);//infalter moshakhas mikne ke kodom Amal tarsim koja bayad anjam shavad
        this.data=data;
        this.context=context;
    }


    @NonNull
    @Override
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent , false);
        ViewHolder holder = new ViewHolder(view);

        return holder;//ba return krdn be ViewHolder extends RecyclerView.ViewHolder ferstade mishavad
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {//Bind yni etesal ya match kardan va har bar ba tekrar shodan dastorat dakhel method amal mikonad
    infoMain cur = data.get(position);
    holder.title.setText(cur.title);
        //Typeface font = Typeface.createFromAsset(context.getAssets(), "AGhasem.ttf");
       // holder.title.setTypeface(font);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{//Karesh tanha etesal ba Component ha mibashad yni msl findviewbyId kar mikne
        TextView title;
        LinearLayout linear_main_row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_nav_title);
            linear_main_row = itemView.findViewById(R.id.linear_main_row);

            linear_main_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getPosition() == 0){
                        Intent intent = new Intent(context, Part01Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 1){
                        Intent intent = new Intent(context, Part02Activity.class);
                        context.startActivity(intent);
                    }

                    if (getPosition() == 2){
                        Intent intent = new Intent(context, Part03Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 3){
                        Intent intent = new Intent(context, Part04Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 4){
                        Intent intent = new Intent(context, Part05Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 5){
                        Intent intent = new Intent(context, Part06Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 6){
                        Intent intent = new Intent(context, Part07Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 7){
                        Intent intent = new Intent(context, Part08Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 8){
                        Intent intent = new Intent(context, Part09Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 9){
                        Intent intent = new Intent(context, Part10Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 10){
                        Intent intent = new Intent(context, Part11Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 11){
                        Intent intent = new Intent(context, Part12Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 12){
                        Intent intent = new Intent(context, Part13Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 13){
                        Intent intent = new Intent(context, Part14Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 14){
                        Intent intent = new Intent(context, Part15Activity.class);
                        context.startActivity(intent);
                    }

                    if (getPosition() == 15){
                        Intent intent = new Intent(context, Part16Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 16){
                        Intent intent = new Intent(context, Part17Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 17){
                        Intent intent = new Intent(context, Part18Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 18){
                        Intent intent = new Intent(context, Part19Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 19){
                        Intent intent = new Intent(context, Part20Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 20){
                        Intent intent = new Intent(context, Part21Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 21){
                        Intent intent = new Intent(context, Part22Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 22){
                        Intent intent = new Intent(context, Part23Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 23){
                        Intent intent = new Intent(context, Part24Activity.class);
                        context.startActivity(intent);
                    }
                    if (getPosition() == 24){
                        Intent intent = new Intent(context, Part25Activity.class);
                        context.startActivity(intent);
                    }

                }
            });


        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context,"This Position" + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }


}
