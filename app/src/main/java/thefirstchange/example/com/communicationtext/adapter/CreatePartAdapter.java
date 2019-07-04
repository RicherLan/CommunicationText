package thefirstchange.example.com.communicationtext.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.zip.Inflater;

import thefirstchange.example.com.communicationtext.R;

public class CreatePartAdapter extends RecyclerView.Adapter<CreatePartAdapter.ViewHolder>{

    private Context mContext;
    private Inflater inflater;
    private int number;
    String[] partName;

    public CreatePartAdapter(Context context,int number){
        this.mContext=context;
        this.number=number;
        partName=new String[number+1];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_create_part,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ViewHolder viewHolder=(ViewHolder)holder;
        int t=position+1;
        viewHolder.editText.setHint("填写第"+t+"分部的名称");
        viewHolder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // Toast.makeText(mContext,"111",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // Toast.makeText(mContext,"2",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                partName[position]=viewHolder.editText.getText().toString().trim();

            }
        });

    }

    @Override
    public int getItemCount() {
        return number;
    }

    public String[] getPartName() {
        return partName;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        EditText editText;

        public ViewHolder(View itemView) {
            super(itemView);
            editText=(EditText)itemView.findViewById(R.id.input_part_name);
        }
    }
}
