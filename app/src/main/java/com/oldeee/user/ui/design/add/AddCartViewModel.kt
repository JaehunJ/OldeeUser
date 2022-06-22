package com.oldeee.user.ui.design.add

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import androidx.core.net.toFile
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.custom.getFileName
import com.oldeee.user.custom.getImageBody
import com.oldeee.user.network.request.AddCartRequest
import com.oldeee.user.network.request.AddCartRequestData
import com.oldeee.user.network.request.AddCartRequestImage
import com.oldeee.user.network.response.AddCartResponseData
import com.oldeee.user.network.response.DesignDetailData
import com.oldeee.user.usercase.PostAddCartUseCase
import com.oldeee.user.usercase.PostImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class AddCartViewModel @Inject constructor(
    private val postImageUseCase: PostImageUseCase,
    private val postAddCartUseCase: PostAddCartUseCase
) : BaseViewModel() {
    var detailInfo = MutableLiveData<String>()

    val imageData = MutableLiveData<MutableList<Uri>>()

    var reformData: DesignDetailData? = null
    val checkedReformItemId = MutableLiveData<Int>()

    val invokeError = MutableLiveData<Boolean>()
    val res = MutableLiveData<AddCartResponseData>()


    fun setImage(imageView: ImageView, uri: Uri) {
        Glide.with(imageView.context).load(uri).into(imageView)
    }

    fun addPhoto(newData: List<Uri>) {
        val oldList = imageData.value ?: mutableListOf()
        newData.forEach {
            oldList.add(it)
        }

        imageData.postValue(oldList.toMutableList())
    }

    fun isValidate(): Boolean {
        val imageList = imageData.value
        val itemId = checkedReformItemId.value
        val text = detailInfo.value
        val reformCode = reformData?.reformCode

        return imageList != null && imageList.isNotEmpty() && itemId != null && !text.isNullOrEmpty() && !reformCode.isNullOrEmpty()
    }

    fun requestAddCart(context: Context) {
        remote {
            val images = postLocalImage(context)
            val data = getRequestData(images)

            var error = false
            val result = postAddCartUseCase.invoke({
                it.errorMessage?.let { sr ->
                    if (sr.contains("discontinued")) {
                        error = true
                        invokeError.postValue(true)
                    }
                }
            }, data)

            if (error) return@remote

            result?.let {
                res.postValue(it.data)
            }
        }
    }

    suspend fun postLocalImage(context: Context): List<String> {
        val imageUriList = imageData.value
        imageUriList?.let { iul ->
            if (iul.isEmpty()) return listOf()

            val imageFileList = getImageListByFile(context, iul)
            val multiBody = getImageBody("files", imageFileList)

            val res = postImageUseCase.invoke(multiBody)

            res?.let {
                val imageList = it.data

                if (imageList.isNotEmpty()) {
                    val serverPathList = mutableListOf<String>()
                    imageList.forEach {
                        serverPathList.add(it.imageName)
                    }

                    return serverPathList
                }
            }
        }

        return listOf()
    }

    /**
     * 파일을 앱 저장소로 복사
     *
     * @param context
     * @param contentsUri
     * @return
     */
    fun copyToScopeStorage(context: Context, contentsUri: Uri): File? {
        if (contentsUri.scheme!! == "file") {
            return contentsUri.toFile()
        }

        val parcelFileDiscripor = context.contentResolver?.openFileDescriptor(contentsUri, "r")

        parcelFileDiscripor?.let {
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val inputStream = FileInputStream(it.fileDescriptor)
            val file = File(storageDir, context.contentResolver.getFileName(contentsUri))
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)

            return file
        }

        return null
    }

    fun getImageListByFile(context: Context, list: List<Uri>): List<File> {
        val temp = mutableListOf<File>()
        list.forEach {
            val file = copyToScopeStorage(context, it)
            file?.let {
                temp.add(it)
            }
        }

        return temp.toList()
    }

    fun getRequestData(imagePaths: List<String>): AddCartRequest {
        val list = mutableListOf<AddCartRequestImage>()
        imagePaths.forEach {
            list.add(AddCartRequestImage(it))
        }
        return AddCartRequest(
            listOf(
                AddCartRequestData(
                    reformData?.reformCode ?: "",
                    checkedReformItemId.value ?: 0,
                    detailInfo.value ?: "",
                    list
                )
            )
        )
    }
}