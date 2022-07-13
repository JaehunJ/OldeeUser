package com.oldee.user.ui.design.add

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import android.widget.ImageView
import androidx.core.net.toFile
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.oldee.user.base.BaseViewModel
import com.oldee.user.custom.getFileName
import com.oldee.user.custom.getImageBody
import com.oldee.user.network.request.AddCartRequest
import com.oldee.user.network.request.AddCartRequestData
import com.oldee.user.network.request.AddCartRequestImage
import com.oldee.user.network.response.AddCartResponseData
import com.oldee.user.network.response.DesignDetailData
import com.oldee.user.usercase.PostAddCartUseCase
import com.oldee.user.usercase.PostImageUseCase
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

    init {
        imageData.value = mutableListOf()
    }

    fun setImage(imageView: ImageView, uri: Uri) {
        Glide.with(imageView.context).load(uri).into(imageView)
    }

    fun addPhoto(newData: Uri) {
        val oldList = imageData.value ?: mutableListOf()

        oldList.add(newData)
//        n/*ewData.forEach {
//            oldList.add(it)
//        }*/

        imageData.postValue(oldList.toMutableList())
    }

    fun isListContains(newData: Uri):Boolean{
        val match = imageData.value?.find { it == newData }

        return match != null
    }

    fun deletePhoto(index:Int){
        val oldList = imageData.value?: mutableListOf()

        if(oldList.isNotEmpty()){
            oldList.removeAt(index)
        }

        imageData.postValue(oldList.toMutableList())
    }

    fun isValidate(onError: (String) -> Unit): Boolean {
        val imageList = imageData.value

        if(imageList.isNullOrEmpty()){
            onError.invoke("준비물사진을 등록해주세요.")
            return false
        }

        val itemId = checkedReformItemId.value
        if(itemId == null){
            onError.invoke("리폼을 위해 준비할 준비물을 선택해 주세요.")
            return false
        }


        val text = detailInfo.value
        if(text.isNullOrEmpty()){
            onError.invoke("상세내용을 작성해주세요.")
            return false
        }

        val reformCode = reformData?.reformCode

        return imageList != null && imageList.isNotEmpty() && itemId != null && !text.isNullOrEmpty() && !reformCode.isNullOrEmpty()
    }

    fun requestAddCart(context: Context, onError:(String)->Unit) {
        remote {
            val images = postLocalImage(context, onError)

            if(images.isEmpty()){
                onError.invoke("이미지 업로드에 실패 했습니다.")
                return@remote
            }else{
                val data = getRequestData(images)

                var error = false
                val result = postAddCartUseCase.invoke({
                    it.errorMessage?.let { sr ->
                        if (sr.contains("discontinued")) {
                            error = true
                            onError.invoke(sr)
                        }
                    }
                }, data)

                if (error) return@remote

                result?.let {
                    res.postValue(it.data)
                }
            }
        }
    }

    suspend fun postLocalImage(context: Context, onError: (String) -> Unit): List<String> {
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
                        if (it.imageName.isNullOrEmpty()) {
                            onError.invoke("image item name is null")
                        }
                        serverPathList.add(it.imageName)
                    }

                    return serverPathList
                } else {
                    onError.invoke("image list is null")
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

    fun getFileSize(context: Context, uri:Uri):Long{
        val cursor = context.contentResolver.query(uri, null, null, null,null)

        cursor?.let{
            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            it.moveToFirst()
            return it.getLong(sizeIndex)
        }

        return 0
    }
}