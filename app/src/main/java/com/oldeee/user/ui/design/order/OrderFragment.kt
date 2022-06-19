package com.oldeee.user.ui.design.order

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldeee.user.BuildConfig
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentOrderBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding, OrderVIewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_order
    override val viewModel: OrderVIewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    var currentPhotoPath: String? = null
    var photoUri: Uri? = null
    private val pictureActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        pushImageData(it.resultCode, it.data)
    }

    lateinit var photoAdapter: OrderAddPhotoAdapter

    override fun initView(savedInstanceState: Bundle?) {
//        binding.ivBack.setOnClickListener {
//            findNavController().popBackStack()
//        }
    }

    override fun initDataBinding() {

    }

    override fun initViewCreated() {

    }

    fun showFileSelector() {
        val state = Environment.getExternalStorageState()
        if (!TextUtils.equals(state, Environment.MEDIA_MOUNTED))
            return

        //create camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        //add image capture event
        cameraIntent.resolveActivity(requireContext().packageManager)?.let {
            createImageFile()?.let { f ->
                photoUri = FileProvider.getUriForFile(
                    this.requireContext(),
                    BuildConfig.APPLICATION_ID + ".fileprovider",
                    f
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                cameraIntent.putExtra("mode", 0)
            }
        }

        //create galley intent
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = MediaStore.Images.Media.CONTENT_TYPE
            data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            putExtra(Intent.EXTRA_MIME_TYPES, "image/jpeg")
        }

        Intent.createChooser(intent, "사진 업로드 방법 선택").run {
            putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
            pictureActivityResultLauncher.launch(this)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    fun pushImageData(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val list = mutableListOf<Uri>()
            //use camera
            if (data == null) {
                list.add(Uri.fromFile(File(currentPhotoPath ?: "")))
            } else {
                val selectedImage = data.data

                selectedImage?.let {
                    val path = it.path
                    path?.let { p ->
                        val extension = p.contains("gif")

                        if (!extension) {
                            list.add(it)
                        } else {
                            activityFuncFunction.showToast("gif파일은 선택하실수 없습니다.")
                        }
                    }
                }
            }

            if (list.isEmpty()) {
                Log.e("#debug", "image list is null")
                return
            }

            viewModel.addPhoto(list)
        }
    }
}