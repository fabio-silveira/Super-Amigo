package fabio.silveira.superamigo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fabio.silveira.superamigo.R;
import fabio.silveira.superamigo.model.Dog;

public class DogAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Dog> dogs;

    private static ClickListener clickListener;

    public DogAdapter(Context context,ArrayList<Dog>dogs){
        this.context = context;
        this.dogs = dogs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.row,parent,false);

        ViewHolder holder = new ViewHolder(v);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {


        ViewHolder h = (ViewHolder)viewHolder;
        Dog d = dogs.get(position);

        h.tvName.setText("Nome: "+d.getName());
        h.tvGender.setText("Genero: "+d.getGender());
        h.tvAge.setText("Idade: "+d.getAge());
        h.tvSize.setText("Porte: "+d.getSize());
    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        final TextView tvName;
        final TextView tvGender;
        final TextView tvAge;
        final TextView tvSize;

        public ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvName = itemView.findViewById(R.id.row_tv_name);
            tvGender = itemView.findViewById(R.id.row_tv_gender);
            tvAge = itemView.findViewById(R.id.row_tv_age);
            tvSize = itemView.findViewById(R.id.row_tv_size);
        }
        //OnClick
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v,getAdapterPosition());
        }
        //OnLongClick
        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(v,getAdapterPosition());
            return true;
        }
    }//Close ViewHolder

    public void setOnItemClickListener(ClickListener clickListener){
        DogAdapter.clickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(View v, int position);
        void onItemLongClick(View v, int position);
    }
}//Class
