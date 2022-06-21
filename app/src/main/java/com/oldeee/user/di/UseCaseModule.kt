package com.oldeee.user.di

import com.oldeee.user.network.RemoteData
import com.oldeee.user.network.request.NaverSignInRequest
import com.oldeee.user.network.request.SignUpRequest
import com.oldeee.user.network.response.SignInResponseData
import com.oldeee.user.repository.CommonRepository
import com.oldeee.user.repository.DesignRepository
import com.oldeee.user.repository.ExpertRepository
import com.oldeee.user.repository.SignRepository
import com.oldeee.user.usercase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetAutoLoginUseCase(repo:SignRepository) = GetAutoLoginValue(repo)

    @Provides
    fun provideGetNoticeListUseCase(repo:CommonRepository) = GetNoticeListUseCase(repo)

    @Provides
    fun provideSetAutoLoginUseCase(repo:SignRepository) = SetAutoLoginValue(repo)

    @Provides
    fun provideGetDesignListUseCase(repository:DesignRepository) = GetDesignListUseCase(repository)

    @Provides
    fun provideSetTokenUseCase(repo:CommonRepository) = SetTokenUseCase(repo)

    @Provides
    fun provideSetSignUpUseCase(repo:SignRepository) = SetSignUpUseCase(repo)

    @Provides
    fun provideNaverSignInUseCase(repo:SignRepository) = SetNaverSignInUseCase(repo)

    @Provides
    fun provideExpertListUseCase(repo:ExpertRepository) = GetExpertListUseCase(repo)

    @Provides
    fun provideGetImageUseCase(repo:CommonRepository) = GetImageUseCase(repo)


//
//    class GetImageUseCase @Inject constructor(private val repo: CommonRepository) {
//        suspend operator fun invoke(path: String) = repo.getImageFromServer(path)
//    }
}