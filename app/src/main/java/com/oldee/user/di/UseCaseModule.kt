package com.oldee.user.di

import com.oldee.user.repository.*
import com.oldee.user.usercase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetVersionInfoUserCase(repo:CommonRepository) = GetVersionInfoUseCase(repo)

    @Provides
    fun provideGetAutoLoginUseCase(repo:SignRepository) = GetAutoLoginValue(repo)

    @Provides
    fun provideGetNoticeListUseCase(repo:CommonRepository) = GetNoticeListUseCase(repo)

    @Provides
    fun provideSetUserDataUseCase(repo:UserRepository) = SetUserData(repo)

    @Provides
    fun provideGetUserDataUseCase(repo:UserRepository) = GetUserData(repo)

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
    fun providePostImageUseCase(repo:CommonRepository) = PostImageUseCase(repo)

    @Provides
    fun provideGetImageUseCase(repo:CommonRepository) = GetImageUseCase(repo)

    @Provides
    fun provideSetImageUseCase(getImageUseCase: GetImageUseCase) = SetImageUseCase(getImageUseCase)

    @Provides
    fun provideSetImageCircleUseCase(getImageUseCase: GetImageUseCase) = SetImageCircleUseCase(getImageUseCase)

    @Provides
    fun providePostAddCartUseCase(repo:DesignRepository) = PostAddCartUseCase(repo)

    @Provides
    fun provideGetCartListUseCase(repo:DesignRepository) = GetCartListUserCase(repo)

    @Provides
    fun provideCartItemDelete(repo:DesignRepository) = DeleteCartListItemUseCase(repo)

    @Provides
    fun provideGetPaymentListUseCase(repo:DesignRepository) = GetPaymentListUseCase(repo)

    @Provides
    fun providePaymentUseCase(repo:DesignRepository) = PostPaymentUseCase(repo)

    @Provides
    fun providePaymentDetailUseCase(repo: DesignRepository) = GetPaymentDetailUseCase(repo)

    @Provides
    fun provideGetAddressListUseCase(repo: DesignRepository) = GetAddressListUseCase(repo)

    @Provides
    fun providePostAddressUseCase(repo: DesignRepository) = PostAddressUseCase(repo)

    @Provides
    fun providesGetAddressById(repo:DesignRepository) = GetAddressByIdUserCase(repo)

    @Provides
    fun provideGetPaymentPage(repo: DesignRepository) = GetPaymentPageUseCase(repo)
}