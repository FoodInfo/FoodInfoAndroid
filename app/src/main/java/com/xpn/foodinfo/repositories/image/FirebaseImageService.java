package com.xpn.foodinfo.repositories.image;

import android.net.Uri;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;
import com.xpn.foodinfo.models.Image;
import com.xpn.foodinfo.models.User;
import com.xpn.foodinfo.services.image.ImageService;
import com.xpn.foodinfo.util.DateTimeUtil;

import java.util.Date;
import java.util.List;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import durdinapps.rxfirebase2.RxFirebaseStorage;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class FirebaseImageService implements ImageService {
    private final StorageReference imagesStorage;
    private final DatabaseReference imagesDatabase;

    @Override
    public Single<Image> upload(User currentUser, String imageName, Date dateCaptured, Uri uri) {
        return Single.defer(() -> {
            /// BASE_PATH/{userId}/imageName
            final StorageReference currentImageReference = imagesStorage
                    .child(currentUser.getId())
                    .child(imageName);

            return RxFirebaseStorage.putFile(currentImageReference, uri)
                    .flatMap(snapshot -> Single.create((SingleOnSubscribe<Uri>) emitter -> currentImageReference.getDownloadUrl()
                            .addOnSuccessListener(emitter::onSuccess)
                            .addOnFailureListener(emitter::onError)))
                    .flatMap(downloadUri -> {
                        String isoDate = DateTimeUtil.dateToISO(dateCaptured);
                        return Single.just(new Image(imageName, currentUser.getId(), downloadUri.toString(), isoDate));
                    });
        });
    }

    @Override
    public Completable add(Image image) {
        return Completable.defer(() -> {
            DatabaseReference targetLocation = imagesDatabase.push();
            return RxFirebaseDatabase.setValue(targetLocation, image);
        });
    }

    @Override
    public Completable remove(Image image) {
        return null;
    }

    @Override
    public Single<List<Image>> getImages(User user) {
        return Single.defer(() -> {
            Query userImagesQuery = imagesDatabase.orderByChild("uploaderId").equalTo(user.getId());

            return RxFirebaseDatabase.observeSingleValueEvent(userImagesQuery)
                    .map(DataSnapshot::getChildren)
                    .toSingle()
                    .flatMap(list -> Observable.fromIterable(list)
                            .map(dataSnapshot -> dataSnapshot.getValue(Image.class))
                            .toList()
                    );
        });
    }
}
