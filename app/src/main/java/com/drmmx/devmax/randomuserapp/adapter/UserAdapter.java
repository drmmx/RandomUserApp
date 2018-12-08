package com.drmmx.devmax.randomuserapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drmmx.devmax.randomuserapp.ui.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import com.drmmx.devmax.randomuserapp.R;
import com.drmmx.devmax.randomuserapp.model.Result;

import static com.drmmx.devmax.randomuserapp.util.Utils.*;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<Result> results;

    public UserAdapter(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.nameLastName.setText(new StringBuilder(firstUpperCase(results.get(position).getName().getFirst()))
                .append(" ").append(firstUpperCase(results.get(position).getName().getLast())));

        Picasso.get().load(results.get(position).getPicture().getMedium()).fit().into(holder.circleImageView);

        //Load DetailActivity and send data
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(FIRST_NAME, results.get(position).getName().getFirst());
                intent.putExtra(LAST_NAME, results.get(position).getName().getLast());
                intent.putExtra(GENDER, results.get(position).getGender());
                intent.putExtra(DATE_OF_BIRTH, results.get(position).getDob().getDate());
                intent.putExtra(USER_AGE, results.get(position).getDob().getAge());
                intent.putExtra(USER_PHONE_NUMBER, results.get(position).getPhone());
                intent.putExtra(USER_EMAIL, results.get(position).getEmail());
                intent.putExtra(IMAGE_URL, results.get(position).getPicture().getLarge());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    //Add pagination users
    public void addUsers(List<Result> results) {
        this.results.addAll(results);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameLastName;
        CircleImageView circleImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameLastName = itemView.findViewById(R.id.textNameLastName);
            circleImageView = itemView.findViewById(R.id.circleImageView);
        }
    }
}
