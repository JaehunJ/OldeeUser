package com.oldee.user.usercase

import android.content.Context
import android.util.Size
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.oldee.user.R
import com.oldee.user.network.RemoteData
import com.oldee.user.network.request.*
import com.oldee.user.network.response.SignInResponseData
import com.oldee.user.repository.*
import okhttp3.MultipartBody
import javax.inject.Inject

class GetVersionInfoUseCase @Inject constructor(private val repo: CommonRepository) {
    suspend operator fun invoke() = repo.requestVersionInfo()
}

class SetTokenUseCase @Inject constructor(private val repo: CommonRepository) {
    operator fun invoke(data: SignInResponseData) {
        repo.setToken(data)
    }

    operator fun invoke(accessToken: String, refreshToken: String) {
        repo.setToken(accessToken, refreshToken)
    }
}

class GetAutoLoginValue @Inject constructor(private val repo: SignRepository) {
    operator fun invoke() = repo.getAutoLoginValue()
}

class GetUserData @Inject constructor(private val repo: UserRepository) {
    operator fun invoke() = repo.getUserData()
}

class SetUserData @Inject constructor(private val repo: UserRepository) {
    operator fun invoke(name: String, email: String, phone: String, snsId: String) =
        repo.setUserData(name, email, phone, snsId)
}

class PostWithdraw @Inject constructor(private val repo: SignRepository) {
    suspend operator fun invoke(data: WithdrawRequest) = repo.requestWithdraw(data)
}

class StartLogoutUseCase @Inject constructor(private val repo: SignRepository) {
    suspend operator fun invoke() = repo.logout()
}

class SetAutoLoginValue @Inject constructor(private val repo: SignRepository) {
    operator fun invoke(boolean: Boolean) = repo.setAutoLoginValue(boolean)
}

class SetNaverSignInUseCase @Inject constructor(private val repo: SignRepository) {
    suspend operator fun invoke(data: NaverSignInRequest, onError: (RemoteData.ApiError) -> Unit) =
        repo.requestNaverSignIn(data, onError)
}

class SetSignUpUseCase @Inject constructor(private val repo: SignRepository) {
    suspend operator fun invoke(data: SignUpRequest) = repo.requestSignUp(data)
}

class GetDesignListUseCase @Inject constructor(private val repository: DesignRepository) {
    suspend operator fun invoke(limit: Int, page: Int) = repository.requestDesignList(limit, page)
}

class GetDesignDetailUseCase @Inject constructor(private val repo: DesignRepository) {
    suspend operator fun invoke(id: Int) = repo.requestDesignDetail(id)
}

class PostAddCartUseCase @Inject constructor(private val repo: DesignRepository) {
    suspend operator fun invoke(onError: (RemoteData.ApiError) -> Unit, data: AddCartRequest) =
        repo.requestAddCart(onError, data)
}

class PostPaymentUseCase @Inject constructor(private val repo: DesignRepository) {
    suspend operator fun invoke(data: PaymentRequest) = repo.requestPayment(data)
}

//log
class GetPaymentListUseCase @Inject constructor(private val repo: DesignRepository) {
    suspend operator fun invoke(successYn: Int, limit: Int? = null, page: Int? = null) =
        repo.requestPaymentList(successYn, limit, page)
}

class GetPaymentDetailUseCase @Inject constructor(private val repo: DesignRepository) {
    suspend operator fun invoke(orderId: Int) = repo.requestPaymentDetail(orderId)
}

class GetAddressListUseCase @Inject constructor(private val repo: DesignRepository) {
    suspend operator fun invoke(code: Int) = repo.requestAddressList(code)
}

class GetAddressByIdUserCase @Inject constructor(private val repo: DesignRepository) {
    suspend operator fun invoke(id: Int) = repo.requestAddressById(id)
}

class PostAddressUseCase @Inject constructor(private val repo: DesignRepository) {
    suspend operator fun invoke(data: AddShippingAddressRequest) = repo.requestAddAddress(data)
}

class GetExpertListUseCase @Inject constructor(private val repo: ExpertRepository) {
    suspend operator fun invoke() = repo.requestExpertList()
}

class GetImageUseCase @Inject constructor(private val repo: CommonRepository) {
    suspend operator fun invoke(path: String) = repo.getImageFromServer(path)
}

class SetImageUseCase @Inject constructor(private val getImageUseCase: GetImageUseCase) {
    suspend operator fun invoke(
        context: Context,
        imageView: ImageView,
        path: String,
        useSkeleton: Boolean = true
    ) {
        if (useSkeleton) {
            val bitmap = getImageUseCase.invoke(path)

            Glide.with(context).load(bitmap).transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(
                    R.drawable.icon_empty_image
                ).error(R.drawable.icon_empty_image).into(imageView)
        } else {
            val bitmap = getImageUseCase.invoke(path)
            Glide.with(context).load(bitmap).transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.icon_empty_image).error(R.drawable.icon_empty_image)
                .into(imageView)
        }
    }

    suspend operator fun invoke(
        context: Context,
        imageView: ImageView,
        path: String,
        roundDp: Int,
        size: Size? = null,
        useSkeleton: Boolean = true,
    ) {
        if (useSkeleton) {
            val bitmap = getImageUseCase.invoke(path)

            if (size == null) {
                Glide.with(context).load(bitmap)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .transform(CenterCrop(), RoundedCorners(roundDp)).into(imageView)
            } else {
                Glide.with(context).load(bitmap)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .override(size.width, size.height)
                    .transform(CenterCrop(), RoundedCorners(roundDp)).into(imageView)
            }
        } else {
            val bitmap = getImageUseCase.invoke(path)
            Glide.with(context).load(bitmap).transition(DrawableTransitionOptions.withCrossFade())
                .transform(CenterCrop(), RoundedCorners(roundDp))
                .into(imageView)
        }
    }
}

class SetImageCircleUseCase @Inject constructor(private val getImageUseCase: GetImageUseCase) {
    suspend operator fun invoke(
        context: Context,
        imageView: ImageView,
        path: String,
        useSkeleton: Boolean = true
    ) {
        if (useSkeleton) {
            val bitmap = getImageUseCase.invoke(path)
            Glide.with(context).load(bitmap).apply(RequestOptions().circleCrop())
                .transition(DrawableTransitionOptions.withCrossFade()).into(imageView)
        } else {
            val bitmap = getImageUseCase.invoke(path)
            Glide.with(context).load(bitmap).apply(RequestOptions().circleCrop())
                .transition(DrawableTransitionOptions.withCrossFade()).into(imageView)
        }
    }
}

class PostImageUseCase @Inject constructor(private val repo: CommonRepository) {
    suspend operator fun invoke(list: List<MultipartBody.Part>) = repo.postImageToServer(list)
}

class GetNoticeListUseCase @Inject constructor(private val repo: CommonRepository) {
    suspend operator fun invoke() = repo.requestNoticeList()
}

//cart
class GetCartListUserCase @Inject constructor(private val repo: DesignRepository) {
    suspend operator fun invoke() = repo.requestCartList()
}

class DeleteCartListItemUseCase @Inject constructor(private val repo: DesignRepository) {
    suspend operator fun invoke(data: BasketItemDeleteRequest) = repo.requestCartDelete(data)
}


class SetHeartCheckUseCase @Inject constructor(private val repo: DesignRepository) {

}

class GetPaymentPageUseCase @Inject constructor(private val repo: DesignRepository) {
    suspend operator fun invoke(data: PaymentPageRequest) = repo.requestGetPaymentPage(data)
}

//class GetFaqListUseCase @Inject constructor(private val repo:)

class GetFooterUseCase @Inject constructor(private val repo:CommonRepository){
    suspend operator fun invoke() = repo.requestFooter()
}