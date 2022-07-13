package com.oldee.user.ui.design.add

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.bumptech.glide.Glide
import com.oldee.user.BuildConfig
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.custom.setWonText
import com.oldee.user.data.PrepareItemMappingStringList
import com.oldee.user.databinding.FragmentAddCartBinding
import com.oldee.user.databinding.LayoutOrderCheckPrepareItemBinding
import com.oldee.user.ui.design.detail.PrepareItem
import com.oldee.user.ui.dialog.TwoButtonDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val IMAGE_MAX_SIZE = 20000000

@AndroidEntryPoint
class AddCartFragment :
    BaseFragment<FragmentAddCartBinding, AddCartViewModel, AddCartFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_add_cart
    override val viewModel: AddCartViewModel by viewModels()
    override val navArgs: AddCartFragmentArgs by navArgs()

    var currentPhotoPath: String? = null
    var photoUri: Uri? = null
    private val pictureActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        pushImageData(it.resultCode, it.data)
    }

    val checkBoxes = mutableListOf<LayoutOrderCheckPrepareItemBinding>()

    lateinit var photoAdapter: AddCartAddPhotoAdapter

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.reformData = navArgs.reformInfo
        photoAdapter = AddCartAddPhotoAdapter({
            showFileSelector()
        }, { iv, uri ->
            viewModel.setImage(iv, uri)
        }, { pos ->
            viewModel.deletePhoto(pos)
        })
        binding.rvImages.adapter = photoAdapter

        val prepareItemList = mutableListOf<PrepareItem>()
        viewModel.reformData?.let { d ->
            val codes = d.getItemCode()
            val names = d.getReformItemNameList()

            codes.forEachIndexed { index, s ->
                prepareItemList.add(PrepareItem("", name = names[index], code = s))
            }

            setWonText(binding.layoutOrderItemPrice.tvPrice, d.price)
            setWonText(binding.tvTotalPrice, d.price)
        }
        setPrepareItem()

        binding.btnOrder.setOnClickListener {
//            showSuccessDialog()
            if (viewModel.isValidate{
                    activityFuncFunction.showToast(it)
                }) {
                viewModel.requestAddCart(requireContext()) {
                    activityFuncFunction.showToast(it)
                }
            } else {
                activityFuncFunction.showToast("누락된 정보가 있어요.")
            }
        }

        binding.vm = viewModel
    }

    override fun initDataBinding() {
        viewModel.imageData.observe(viewLifecycleOwner) {
            it?.let { list ->
                if(list.isNotEmpty()){
                    photoAdapter.setData(list.toList())
                }else{
                    photoAdapter.initData()
                }
            }
        }
        viewModel.res.observe(viewLifecycleOwner) {
            it?.let {
                showSuccessDialog()
            }
        }
    }

    override fun initViewCreated() {

    }

    fun showSuccessDialog() {
        val dialog = TwoButtonDialog(
            "", "수선바구니에 담았어요", "보러가기", "닫기", {
                val option = navOptions {
                    popUpTo(R.id.homeFragment)
                }
                findNavController().navigate(R.id.action_global_cartFragment, null, option)
            }, { findNavController().popBackStack(R.id.homeFragment, false) }
        )
        dialog.isCancelable = false
        activity?.let { ay ->
            dialog.show(ay.supportFragmentManager, "success")
        }
    }

    fun setPrepareItem() {
        val data = viewModel.reformData
        data?.let { d ->
            val size = d.getIconImageIdList().size
            val itemCodeList = d.getItemCode()
            val itemNameList = d.getReformItemNameList()
            val reformItemId = d.getReformItemIdList()

            for (i in 0 until size) {
                val cbinding = LayoutOrderCheckPrepareItemBinding.inflate(
                    LayoutInflater.from(requireContext()),
                    binding.rgPrepareItem,
                    true
                )
                Glide.with(requireContext()).load(PrepareItemMappingStringList[itemCodeList[i]])
                    .into(cbinding.ivImage)
                cbinding.tvName.text = itemNameList[i]
                cbinding.clRoot.setOnClickListener {
                    cbinding.cbPrepare.isChecked = !cbinding.cbPrepare.isChecked

                    if (cbinding.cbPrepare.isChecked) {
                        viewModel.checkedReformItemId.postValue(reformItemId[i].toInt())
                    }

                    checkBoxes.forEach {
                        if (it != cbinding) {
                            it.cbPrepare.isChecked = !cbinding.cbPrepare.isChecked
                        }
                    }
                }
                cbinding.cbPrepare.setOnCheckedChangeListener { buttonView, isChecked ->
                    cbinding.clRoot.alpha = if (isChecked) 1f else 0.4f
                }
                checkBoxes.add(cbinding)
            }
        }
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
                    val mimeType = it.let {
                            returnUri-> context?.contentResolver?.getType(it)
                    }

                    if(mimeType == "image/jpeg"){
                        val oldList =viewModel.imageData.value
                        oldList?.let{l->
                            var oldSize = 0L
                            l.forEach { u->
                                oldSize += viewModel.getFileSize(requireContext(), u)
                            }

                            val currentSize = viewModel.getFileSize(requireContext(), it)

                            if(oldSize + currentSize > IMAGE_MAX_SIZE){
                                activityFuncFunction.showToast("용량이 큰 이미지 파일은 등록할 수 없어요.")
                            }else{
                                list.add(it)
                            }
                        }
                    }else{
                        activityFuncFunction.showToast("JPG 이미지만 등록할 수 있어요.")
                    }
                }
            }

            if (list.isEmpty()) {
                Log.e("#debug", "image list is null")
                return
            }

            if(!viewModel.isListContains(list[0])){
                viewModel.addPhoto(list[0])
            }else{
                activityFuncFunction.showToast("동일한 이미지가 있어요.")
            }
        }
    }
}