package com.xpn.foodinfo.repositories.image;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.xpn.foodinfo.models.Image;
import com.xpn.foodinfo.models.User;
import com.xpn.foodinfo.services.image.ImageUploadingService;
import com.xpn.foodinfo.services.user.UserService;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import durdinapps.rxfirebase2.RxFirebaseStorage;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import lombok.AllArgsConstructor;
import timber.log.Timber;


@AllArgsConstructor
public class FirebaseImageUploadingService implements ImageUploadingService {
    private final StorageReference imagesReference;
    private final DatabaseReference imagesDatabase;
    private final UserService userService;

    @Override
    public Single<Image> upload(String imageName, Uri uri) {
        return Single.defer(() -> {
            /// BASE_PATH/{userId}/imageName
            final User currentUser = userService.getCurrentUser();
            final StorageReference currentImageReference = imagesReference
                    .child(currentUser.getId())
                    .child(imageName);

            return RxFirebaseStorage.putFile(currentImageReference, uri)
                    .flatMap(snapshot -> Single.create((SingleOnSubscribe<Uri>) emitter -> currentImageReference.getDownloadUrl()
                            .addOnSuccessListener(emitter::onSuccess)
                            .addOnFailureListener(emitter::onError)))
                    .flatMap(downloadUri -> Single.just(new Image(imageName, currentUser.getId(), downloadUri.toString())));
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
}
