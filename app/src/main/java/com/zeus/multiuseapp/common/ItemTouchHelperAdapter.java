package com.zeus.multiuseapp.common;

/**
 * Created by Zeus on 4/12/2016.
 */
public interface ItemTouchHelperAdapter {

    //this is called when everytime item is moved

    void OnItemMove(int fromPosition, int toPosition);

    //called when item is dismissed during swipe

    void OnItemDismissed(int position);
}
