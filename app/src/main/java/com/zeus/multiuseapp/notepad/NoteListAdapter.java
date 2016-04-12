package com.zeus.multiuseapp.notepad;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.models.Note;

import java.util.List;

/**
 * Created by Zeus on 4/7/2016.
 */
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private List<Note> mNote;
    private Context mContext;

    public NoteListAdapter(Context context, List<Note> notes) {
        mContext = context;
        mNote = notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note selectedNote = mNote.get(position);
        holder.noteTitle.setText(selectedNote.getTitle());
        holder.noteCreated.setText(selectedNote.getModifiedDate());
    }

    @Override
    public int getItemCount() {
        return mNote.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView noteTitle, noteCreated;
        public ImageView handleView;

        public ViewHolder(View itemView) {
            super(itemView);

            noteTitle = (TextView) itemView.findViewById(R.id.noteListTitle);
            noteCreated = (TextView) itemView.findViewById(R.id.noteListCreated);
            handleView = (ImageView) itemView.findViewById(R.id.handle);

        }
    }
}
