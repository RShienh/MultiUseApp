package com.zeus.multiuseapp.listener;

import com.zeus.multiuseapp.models.Notes;

import java.util.List;

/**
 * Created by Zeus on 4/14/2016.
 */
public interface OnNoteListChangedListener {
    void OnNoteListChanged(List<Notes> notes);
}
