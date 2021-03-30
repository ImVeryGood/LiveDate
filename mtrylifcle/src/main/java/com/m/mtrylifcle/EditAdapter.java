package com.m.mtrylifcle;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * createDate:2020/11/30
 *
 * @author:spc
 * @describe：
 */
public class EditAdapter extends RecyclerView.Adapter<EditAdapter.EditViewHolder> {
    private List<String> list;
    private Context mContext;
    private LayoutInflater inflater;
    private textChanged textChanged;

    public EditAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<String> data) {
        list = data;
        notifyDataSetChanged();
    }

    public void setTextChanged(textChanged textChanged) {
        this.textChanged = textChanged;
    }

    @NonNull
    @Override
    public EditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        EditViewHolder holder = new EditViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final EditViewHolder holder, final int position) {
        final TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (holder.editText.hasFocus()) {
                    textChanged.textChanges(position, editable.toString());
                }
            }
        };
        holder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                Log.d("SSSSSSSSS", "onFocusChange: position="+position+"isEdit="+holder.editText.hasFocus());
                if (hasFocus) {
                    holder.editText.addTextChangedListener(watcher);
                } else {
                    holder.editText.removeTextChangedListener(watcher);
                }
            }
        });
      // holder.editText.setText(list.get(position));
        Log.d("SSSSSSSSS", "onBindViewHolder: position="+position+"isEdit="+holder.editText.hasFocus());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class EditViewHolder extends RecyclerView.ViewHolder {
        private EditText editText;

        public EditViewHolder(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.edit);
        }
    }

    public interface textChanged {
        void textChanges(int position, String str);
    }
} 