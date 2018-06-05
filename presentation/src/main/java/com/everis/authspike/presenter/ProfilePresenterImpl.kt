package com.everis.authspike.presenter

import com.everis.authspike.UIThread
import com.everis.authspike.view.view.ProfileView
import com.everis.data.repository.DatabaseDataRepository
import com.everis.data.repository.UserDataRepository
import com.everis.domain.interactor.UpdateUser
import com.everis.domain.interactor.UploadUserPicture
import rx.Subscriber

class ProfilePresenterImpl(private val view: ProfileView) : ProfilePresenter {

    private val uploadPicture: UploadUserPicture = UploadUserPicture(UIThread(), UserDataRepository())
    private val updateUser: UpdateUser = UpdateUser(UIThread(), DatabaseDataRepository())

    override fun uploadPicture() {
        if (view.getPicture() != null) {
            uploadPicture.bindParams(view.getUserId(), view.getPicture()!!)
            uploadPicture.execute(UploadPictureSubscriber())
        }

    }

    override fun updateUser() {
        updateUser.bindParams(view.getUserId(),view.getUrl())
        updateUser.execute(UpdateUserSubscriber())
    }


    override fun onPause() {

    }

    override fun onResume() {

    }

    override fun onStop() {
    }

    override fun onDestroy() {

    }


    private inner class UpdateUserSubscriber : Subscriber<Unit>() {

        override fun onCompleted() {
            view.hideLoading()
        }

        override fun onError(e: Throwable) {
            view.hideLoading()
        }

        override fun onNext(u: Unit) {
            view.updateSuccessful()
        }
    }

    private inner class UploadPictureSubscriber : Subscriber<String>() {

        override fun onCompleted() {
            view.hideLoading()
        }

        override fun onError(e: Throwable) {
            view.hideLoading()
        }

        override fun onNext(url: String) {
            view.displayPicture(url)
        }
    }
}