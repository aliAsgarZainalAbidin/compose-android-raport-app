package id.deval.raport.ui.akun.siswa

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import id.deval.raport.BuildConfig.BASE_URL
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddSiswaBinding
import id.deval.raport.db.models.Siswa
import id.deval.raport.db.models.request.SiswaUpdate
import id.deval.raport.utils.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*

class AddSiswaFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAddSiswaBinding
    private val binding get() = _binding
    private lateinit var id: String
    private var isValid = false
    private var pathImage: String? = null
    private lateinit var selectedImage: Bitmap
    private var localImage: String? = null
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            Log.d(
                "TAG",
                "requestPermission: ${it[Manifest.permission.READ_EXTERNAL_STORAGE]}  ${it[Manifest.permission.WRITE_EXTERNAL_STORAGE]}"
            )
        }

    private val startPickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data != null && it.data!!.data != null) {
                    val selectedImageUri = it.data!!.data!!
                    selectedImage = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        selectedImageUri
                    )

                    val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                    } else MediaStore.Images.Media.EXTERNAL_CONTENT_URI

                    //selection and projection in emulator not support
                    var projection: Array<String>? = null
                    var selection: String? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        projection = arrayOf(
                            MediaStore.Images.Media._ID,
                            MediaStore.Images.Media.DISPLAY_NAME,
                            MediaStore.Images.Media.RELATIVE_PATH
                        )
                        selection =
                            MediaStore.Images.ImageColumns.RELATIVE_PATH + " like '%" + "DCIM" + "%'"

                        selectedImageUri.let { uri ->
                            requireActivity().contentResolver.query(
                                collection,
                                projection,
                                null,
                                null,
                                null
                            ).use {
                                var id = 0L
                                var displayName = ""
                                var relativePath = ""
                                if (it == null) {
                                    pathImage = selectedImageUri.path.toString()
                                    localImage = selectedImageUri.path
                                } else {
                                    it.moveToFirst()
                                    val idColumn =
                                        it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                                    val displayNameColumn =
                                        it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                                    val relativePathColumn =
                                        it.getColumnIndexOrThrow(MediaStore.Images.Media.RELATIVE_PATH)
                                    while (it.moveToNext()) {
                                        id = it.getLong(idColumn)
                                        displayName = it.getString(displayNameColumn)
                                        relativePath = it.getString(relativePathColumn)
                                        Log.d(TAG, "RelativePATH : $relativePath")
                                        Log.d(TAG, "DisplayName : $displayName")
                                        Log.d(TAG, "ID: $id")

                                        if (uri.lastPathSegment?.replace("image:", "", true)
                                                .equals(id.toString())
                                        ) {
                                            Log.d(
                                                TAG,
                                                "PATH ?: /storage/emulated/0/$relativePath$displayName"
                                            )
                                            localImage =
                                                "/storage/emulated/0/$relativePath$displayName"
                                            pathImage = localImage
                                        }
                                    }
                                    it.close()
                                }
                                Log.d(TAG, "PATH Sementara: $pathImage")
                            }
                            Log.d(TAG, "PATH selectedImageUri: ${uri.lastPathSegment}")
                        }
                    } else {
                        projection = arrayOf(
                            MediaStore.Images.Media.DATA
                        )
                        selection = null
                        pathImage = getDataColumn(
                            requireContext(),
                            selectedImageUri,
                            projection,
                            selection,
                            null
                        )
                        Log.d(TAG, "TEST PATH ? : ${pathImage}")
                    }
                    binding.ivAddsiswaPhoto.setImageBitmap(selectedImage)
                }
            }
        }

    fun getDataColumn(
        context: Context,
        uri: Uri?,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        try {
            cursor = uri?.let {
                context.getContentResolver().query(it, projection, selection, selectionArgs, null)
            }
            if (cursor != null && cursor.moveToFirst()) {
                val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return cursor.getString(column_index)
            }
        } finally {
            if (cursor != null) cursor.close()
        }
        return null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSiswaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterReligion = ArrayAdapter(
            requireContext(),
            R.layout.list_item,
            resources.getStringArray(R.array.religion)
        )

        val adapterGender = ArrayAdapter(
            requireContext(),
            R.layout.list_item,
            resources.getStringArray(R.array.gender)
        )

        val role = arguments?.getString(Constanta.ROLE).toString()
        id = arguments?.getString(Constanta.ID) ?: ""
        with(binding) {
            ivAddsiswaBack.setOnClickListener {
                try {
                    mainNavController.popBackStack()
                } catch (e: Exception) {
                    Log.d(TAG, "onViewCreated: $e")
                }
            }

            tietAddsiswaTanggalLahir.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DATE)

                val datePickerDialog =
                    DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                        override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                            val bulan = p2.plus(1).parseIntToMonth()
                            tietAddsiswaTanggalLahir.setText("$p3 $bulan $p1")
                        }

                    }, year, month, day)
                datePickerDialog.show()
            }

            Log.d(TAG, "onViewCreated: $id")
            if (id.isNotEmpty()) {
                Log.d(TAG, "onViewCreated: LOAD IMAGE")
                tietAddsiswaNisn.isEnabled = false
                siswaViewModel.getSiswa(session.token.toString(), id).observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        val siswa = it.body()?.data!!
                        tietAddsiswaNamalengkap.setText(siswa.name)
                        tietAddsiswaNisn.setText(siswa.nis)
                        tietAddsiswaTempatlahir.setText(siswa.tempatLahir)
                        tietAddsiswaTanggalLahir.setText(siswa.tanggalLahir)
                        tietAddsiswaGender.setText(siswa.gender, false)
                        tietAddsiswaReligion.setText(siswa.religion, false)
                        tietAddsiswaEducation.setText(siswa.education)
                        tietAddsiswaAddress.setText(siswa.address)
                        tietAddsiswaNamaAyah.setText(siswa.namaAyah)
                        tietAddsiswaNamaIbu.setText(siswa.namaIbu)
                        tietAddsiswaPekerjaanAyah.setText(siswa.pekerjaanAyah)
                        tietAddsiswaPekerjaanIbu.setText(siswa.pekerjaanIbu)
                        tietAddsiswaNamaWali.setText(siswa.namaWali)
                        tietAddsiswaPekerjaanWali.setText(siswa.pekerjaanWali)
                        tietAddsiswaAlamatWali.setText(siswa.alamatWali)
                        tietAddsiswaHp.setText(siswa.phone)
                        val urlPhoto = "${BASE_URL}siswa/file/${siswa.photo}"
                        pathImage = siswa.photo
                        Log.d(TAG, "onViewCreated: $localImage")
                        if (!localImage.isNullOrEmpty()) {
                            Log.d(TAG, "onViewCreated: NON GLIDE")
                            pathImage = localImage
                            ivAddsiswaPhoto.setImageBitmap(selectedImage)
                        } else {
                            Log.d(TAG, "onViewCreated: GLIDE")
                            Glide.with(requireContext())
                                .load(urlPhoto)
                                .into(ivAddsiswaPhoto)
                        }

//                        Glide.with(requireContext())
//                            .load(urlPhoto)
//                            .into(ivAddsiswaPhoto)
                    } else {
                        requireContext().showToast(it.message())
                    }
                }
            }

            tietAddsiswaGender.setAdapter(adapterGender)
            tietAddsiswaReligion.setAdapter(adapterReligion)

            when (role) {
                "orangtua" -> viewAsOrangtua()
                "admin" -> viewAsAdmin()
            }
        }
    }

    fun viewAsAdmin() {
        with(binding) {
            Log.d("TAG", "viewAsAdmin: ADMIN")
            requestPermission.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )

            ivAddsiswaPhoto.setOnClickListener {
                val uriString =
                    Environment.getExternalStorageDirectory().path + File.separator + "DCIM" + File.separator
                val intent = Intent()
                intent.type = "image/jpeg"
                intent.action = Intent.ACTION_GET_CONTENT
                intent.setDataAndType(
                    Uri.parse(uriString), "image/jpeg"
                );
                Log.d(TAG, "viewAsAdmin: $uriString")
                startPickImageLauncher.launch(intent)
            }

            tietAddsiswaNamalengkap.hideError()
            tietAddsiswaNisn.hideError()
            tietAddsiswaTempatlahir.hideError()
            tietAddsiswaTanggalLahir.hideError()
            tietAddsiswaGender.hideError()
            tietAddsiswaReligion.hideError()
            tietAddsiswaEducation.hideError()
            tietAddsiswaAddress.hideError()
            tietAddsiswaNamaAyah.hideError()
            tietAddsiswaNamaIbu.hideError()
            tietAddsiswaPekerjaanAyah.hideError()
            tietAddsiswaPekerjaanIbu.hideError()
            tietAddsiswaNamaWali.hideError()
            tietAddsiswaPekerjaanWali.hideError()
            tietAddsiswaAlamatWali.hideError()
            tietAddsiswaHp.hideError()

            mbAddsiswaSimpan.setOnClickListener {
                val namaLengkap = tietAddsiswaNamalengkap.text.toString()
                val nisn = tietAddsiswaNisn.text.toString()
                val tempatLahir = tietAddsiswaTempatlahir.text.toString()
                val tanggallahir = tietAddsiswaTanggalLahir.text.toString()
                val gender = tietAddsiswaGender.text.toString()
                val religion = tietAddsiswaReligion.text.toString()
                val eduction = tietAddsiswaEducation.text.toString()
                val address = tietAddsiswaAddress.text.toString()
                val namaAyah = tietAddsiswaNamaAyah.text.toString()
                val namaIbu = tietAddsiswaNamaIbu.text.toString()
                val pekerjaanAyah = tietAddsiswaPekerjaanAyah.text.toString()
                val pekerjaanIbu = tietAddsiswaPekerjaanIbu.text.toString()
                val namaWali = tietAddsiswaNamaWali.text.toString()
                val pekerjaanWali = tietAddsiswaPekerjaanWali.text.toString()
                val alamatWali = tietAddsiswaAlamatWali.text.toString()
                val hp = tietAddsiswaHp.text.toString()

                if (namaLengkap.isEmpty()) {
                    tietAddsiswaNamalengkap.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (nisn.isEmpty()) {
                    tietAddsiswaNisn.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (tempatLahir.isEmpty()) {
                    tietAddsiswaTempatlahir.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (tanggallahir.isEmpty()) {
                    tietAddsiswaTanggalLahir.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (gender.isEmpty()) {
                    tietAddsiswaGender.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (religion.isEmpty()) {
                    tietAddsiswaReligion.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (eduction.isEmpty()) {
                    tietAddsiswaEducation.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (address.isEmpty()) {
                    tietAddsiswaAddress.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (namaAyah.isEmpty()) {
                    tietAddsiswaNamaAyah.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (namaIbu.isEmpty()) {
                    tietAddsiswaNamaIbu.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (pekerjaanAyah.isEmpty()) {
                    tietAddsiswaPekerjaanAyah.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (pekerjaanIbu.isEmpty()) {
                    tietAddsiswaPekerjaanIbu.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (namaWali.isEmpty()) {
                    tietAddsiswaNamaWali.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (pekerjaanWali.isEmpty()) {
                    tietAddsiswaPekerjaanWali.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (alamatWali.isEmpty()) {
                    tietAddsiswaAlamatWali.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (hp.isEmpty()) {
                    tietAddsiswaHp.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (namaLengkap.isNotEmpty() && nisn.isNotEmpty() && tempatLahir.isNotEmpty() && tanggallahir.isNotEmpty() && gender.isNotEmpty() && religion.isNotEmpty() && eduction.isNotEmpty() && address.isNotEmpty() && namaAyah.isNotEmpty() && namaIbu.isNotEmpty() && pekerjaanAyah.isNotEmpty() && pekerjaanIbu.isNotEmpty() && namaWali.isNotEmpty() && pekerjaanWali.isNotEmpty() && alamatWali.isNotEmpty() && hp.isNotEmpty()) {
                    isValid = true
                }

                Log.d(TAG, "viewAsAdmin: PRA VALID")
                if (isValid) {
                    Log.d(TAG, "viewAsAdmin: POST VALID")
                    val siswa = Siswa(
                        "",
                        address,
                        eduction,
                        gender,
                        pekerjaanWali,
                        tanggallahir,
                        alamatWali,
                        religion,
                        namaAyah,
                        null,
                        namaWali,
                        namaIbu,
                        hp,
                        namaLengkap,
                        nisn,
                        tempatLahir,
                        pekerjaanAyah,
                        pekerjaanIbu
                    )

                    if (id.isEmpty()) {
                        try {
                            val bundle = bundleOf()
                            bundle.putParcelable(Constanta.PARCELABLE_ITEM, siswa)
                            bundle.putString(Constanta.PATH_IMAGE, pathImage)
                            val checkImage = File(pathImage)
                            mainNavController.navigate(
                                R.id.action_addSiswaFragment_to_addOrangTuaFragment,
                                bundle
                            )
                        } catch (e: Exception) {
                            Log.d(TAG, "onViewCreated: PATH FOTO TIDAK VALID $pathImage")
                            requireContext().showToast("Foto tidak Valid. Silahkan pilih foto yang lain atau pastikan pilih foto dari galeri")
                        }
                    } else {
                        siswaViewModel.updateSiswa(session.token.toString(), id, siswa)
                            .observe(viewLifecycleOwner) {
                                Log.d(TAG, "viewAsAdmin: $pathImage")
                                if (!pathImage?.startsWith("siswa")!!) {
                                    val imageBitmap = File(pathImage)
                                    val requestImageBody =
                                        imageBitmap.asRequestBody("image/jpeg".toMediaTypeOrNull())
                                    val photo = MultipartBody.Part.createFormData(
                                        "photo",
                                        imageBitmap.name,
                                        requestImageBody
                                    )
                                    siswaViewModel.uploadPhotoSiswa(
                                        session.token.toString(),
                                        id,
                                        photo
                                    )
                                    Log.d(TAG, "viewAsAdmin: UPDATE PHOTO")
                                }
                                Log.d(TAG, "viewAsAdmin: UPDATE SISWA")

                                try {
                                    val bundle = bundleOf()
                                    bundle.putParcelable(Constanta.PARCELABLE_ITEM, siswa)
                                    bundle.putString(Constanta.USERNAME, nisn)
                                    mainNavController.navigate(
                                        R.id.action_addSiswaFragment_to_addOrangTuaFragment,
                                        bundle
                                    )
                                } catch (e: Exception) {
                                    Log.d(TAG, "onViewCreated: $e")
                                }
                            }
                    }
                }
            }
        }
    }

    fun viewAsOrangtua() {
        Log.d(TAG, "viewAsOrangtua: MASUK CUY")
        with(binding) {
            mtvAddsiswaName.text = "Profile Siswa"
            mbAddsiswaLogout.show()
            mbAddsiswaLogout.setOnClickListener {
                loginViewModel.logout().observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        try {
                            session.logout()
                            mainNavController.navigate(R.id.action_addSiswaFragment_to_loginFragment)
                        } catch (e: Exception) {
                            Log.d(TAG, "onViewCreated: $e")
                        }
                    } else {
                        requireContext().showToast(it.message())
                    }
                }
            }

            mbAddsiswaSimpan.hide()
            tilAddsiswaAddress.isEnabled = false
            tilAddsiswaAlamatWali.isEnabled = false
            tilAddsiswaEducation.isEnabled = false
            tilAddsiswaGender.isEnabled = false
            tilAddsiswaHp.isEnabled = false
            tilAddsiswaKelas.isEnabled = false
            tilAddsiswaNamaAyah.isEnabled = false
            tilAddsiswaNamaIbu.isEnabled = false
            tilAddsiswaNamaWali.isEnabled = false
            tilAddsiswaNamalengkap.isEnabled = false
            tilAddsiswaNisn.isEnabled = false
            tilAddsiswaPekerjaanAyah.isEnabled = false
            tilAddsiswaPekerjaanIbu.isEnabled = false
            tilAddsiswaPekerjaanWali.isEnabled = false
            tilAddsiswaReligion.isEnabled = false
            tilAddsiswaTanggalLahir.isEnabled = false
            tilAddsiswaTempatlahir.isEnabled = false
            ivAddsiswaPhoto.isEnabled = false

            siswaViewModel.getSiswaByNIS(session.token.toString(), session.username.toString())
                .observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        val siswa = it.body()?.data!!
                        tietAddsiswaNamalengkap.setText(siswa.name)
                        tietAddsiswaNisn.setText(siswa.nis)
                        tietAddsiswaTempatlahir.setText(siswa.tempatLahir)
                        tietAddsiswaTanggalLahir.setText(siswa.tanggalLahir)
                        tietAddsiswaGender.setText(siswa.gender, false)
                        tietAddsiswaReligion.setText(siswa.religion, false)
                        tietAddsiswaEducation.setText(siswa.education)
                        tietAddsiswaAddress.setText(siswa.address)
                        tietAddsiswaNamaAyah.setText(siswa.namaAyah)
                        tietAddsiswaNamaIbu.setText(siswa.namaIbu)
                        tietAddsiswaPekerjaanAyah.setText(siswa.pekerjaanAyah)
                        tietAddsiswaPekerjaanIbu.setText(siswa.pekerjaanIbu)
                        tietAddsiswaNamaWali.setText(siswa.namaWali)
                        tietAddsiswaPekerjaanWali.setText(siswa.pekerjaanWali)
                        tietAddsiswaAlamatWali.setText(siswa.alamatWali)
                        tietAddsiswaHp.setText(siswa.phone)
                        val urlPhoto = "${BASE_URL}siswa/file/${siswa.photo}"
                        pathImage = siswa.photo
                        Glide.with(requireContext())
                            .load(urlPhoto)
                            .into(ivAddsiswaPhoto)
                    } else {
                        requireContext().showToast(it.message())
                    }
                }
        }
    }
}