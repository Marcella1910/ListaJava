package gaurink.marcella.lista.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

public class NewItemActivityViewModel extends ViewModel {

    Uri SelectPhotoLocation = null;

    public Uri getSelectPhotoLocation() {
        return SelectPhotoLocation;
    }

    public void setSelectPhotoLocation(Uri SelectPhotoLocation) {
        this.SelectPhotoLocation = SelectPhotoLocation;
    }
}
