package com.zeus.multiuseapp.common;

/**
 * Created by Zeus on 4/12/2016.
 */
public interface ItemTouchHelperViewHolder {

    //update item view to indicate it's active state
    void OnItemSelected();

    //when state is cleared
    void OnItemClear();
}
