package com.oldee.user.custom

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FilUtil {
    companion object{
        val MAX_LENGTH = 1048

        fun optimizeBitmap(context:Context, uri: Uri):File?{
            try {
                val timeStamp: String = UUID.randomUUID().toString()
                val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val tempFile =  File.createTempFile(
                    "JPEG_${timeStamp}_",
                    ".jpg",
                    storageDir
                ).apply {
                    currentPhotoPath = absolutePath
                }
            }catch (e:IOException){

            }
        }

        fun resizeBitmap(context: Context, uri:Uri): Bitmap?{
            val input = context.contentResolver.openInputStream(uri)

            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }

            var bitmap: Bitmap?
            BitmapFactory.Options().run {
                inSampleSize = calculateInSampleSize(options)
                bitmap = BitmapFactory.decodeStream(input, null, this)
            }

            // 아래에 회전된 이미지 되돌리기에서 다시 언급할게용 :)
            bitmap = bitmap?.let {
                rotateImageIfRequired(context, bitmap!!, uri)
            }

            input?.close()

            return bitmap
        }

        fun calculateInSampleSize(options: BitmapFactory.Options): Int {
            var height = options.outHeight
            var width = options.outWidth

            var inSampleSize = 1

            while (height > MAX_LENGTH || width > MAX_LENGTH) {
                height /= 2
                width /= 2
                inSampleSize *= 2
            }

            return inSampleSize
        }

        private fun rotateImageIfRequired(context: Context, bitmap: Bitmap, uri: Uri): Bitmap? {
            val input = context.contentResolver.openInputStream(uri) ?: return null

            val exif = if (Build.VERSION.SDK_INT > 23) {
                ExifInterface(input)
            } else {
                ExifInterface(uri.path!!)
            }

            val orientation =
                exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

            return when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270)
                else -> bitmap
            }
        }

        private fun rotateImage(bitmap: Bitmap, degree: Int): Bitmap? {
            val matrix = Matrix()
            matrix.postRotate(degree.toFloat())
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
    }
}