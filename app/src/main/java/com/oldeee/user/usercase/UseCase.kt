package com.oldeee.user.usercase

import com.oldeee.user.network.RemoteData
import com.oldeee.user.network.request.NaverSignInRequest
import com.oldeee.user.network.request.SignUpRequest
import com.oldeee.user.network.response.SignInResponseData
import com.oldeee.user.repository.CommonRepository
import com.oldeee.user.repository.DesignRepository
import com.oldeee.user.repository.ExpertRepository
import com.oldeee.user.repository.SignRepository
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(private val repo: CommonRepository) {
    operator fun invoke(data: SignInResponseData) {
        repo.setToken(data)
    }
}

class GetAutoLoginValue @Inject constructor(private val repo: SignRepository) {
    operator fun invoke() = repo.getAutoLoginValue()
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

class PostReformRequestUseCase @Inject constructor(private val repo: DesignRepository) {

}

class GetExpertListUseCase @Inject constructor(private val repo: ExpertRepository) {
    suspend operator fun invoke() = repo.requestExpertList()
}

class GetImageUseCase @Inject constructor(private val repo: CommonRepository) {
    suspend operator fun invoke(path: String) = repo.getImageFromServer(path)
}

class GetNoticeListUseCase @Inject constructor(private val repo:CommonRepository){
    suspend operator fun invoke() = repo.requestNoticeList()
}

class SetHeartCheckUseCase @Inject constructor(private val repo: DesignRepository) {

}