package id.deval.raport.ui.raport

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import id.deval.raport.R
import id.deval.raport.databinding.FragmentRaportBinding
import id.deval.raport.db.event.CommonParams
import id.deval.raport.db.event.EventToDetailRaport
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.db.models.Tugas
import id.deval.raport.db.models.request.SiswaByNIS
import id.deval.raport.db.response.ResponseDetailKelas
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.*
import org.greenrobot.eventbus.Subscribe
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RaportFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentRaportBinding
    private val binding get() = _binding
    private lateinit var dataSiswa: ArrayList<Siswa>
    private lateinit var role: String
    private lateinit var dataMapel: ArrayList<Mapel>
    private lateinit var classId: String
    private var siswaId = ""
    private lateinit var tableRaport: PdfPTable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRaportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataMapel = arrayListOf()
        dataSiswa = arrayListOf()
        role = arguments?.getString(Constanta.ROLE).toString()
        tableRaport = PdfPTable(Constanta.headerTable.size)
        tableRaport.widthPercentage = 100f

        with(binding) {
            mtvRaportMapel.text = dataMapel.size.toString()
            mtvRaportSiswa.text = dataSiswa.size.toString()
            mtvRaportName.text = session.name

            includeRvMapel.mtvRvlayoutTitle.text = "Mata Pelajaran"
            includeRvMapel.mtvRvlayoutAdd.invisible()
            includeRvMapel.mtvRvlayoutViewmore.invisible()

            when (role) {
                "guru" -> viewAsGuru()
                "orangtua" -> viewAsOrangTua()
            }
        }
    }

    fun viewAsOrangTua() {
        var namaSiswa = ""
        var semester = ""
        var tahunAjaran = ""
        var siswaByNis = SiswaByNIS()
        var classByNis = ResponseDetailKelas()
        with(binding) {
            ivRaportPerson.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString(Constanta.ROLE, role)
                mainNavController.navigate(R.id.action_baseFragment_to_addSiswaFragment, bundle)
            }
            mtvRaportAdd.show()
            mtvRaportAdd.setOnClickListener {
                val document = Document(PageSize.A4)
                val sdf = SimpleDateFormat("ssmmhh")
                val date =sdf.format(Date())
                try {
                    val pdf = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "/Raport $namaSiswa-${date}.pdf"
                    )
                    PdfWriter.getInstance(document, FileOutputStream(pdf))
                    document.open()
                    document.add(Chunk(""));

                    val fontHeader = Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD)
                    val fontNormal = Font(Font.FontFamily.TIMES_ROMAN, 9f, Font.NORMAL)

                    val headerRaport = Paragraph(
                        "Laporan Perkembangan Peserta Didik \nSemester $semester Tahun Ajaran $tahunAjaran",
                        fontHeader
                    )
                    headerRaport.alignment = Element.ALIGN_CENTER
                    headerRaport.font = fontHeader

                    val paraNamaSiswa = Paragraph("Nama : ${siswaByNis.name}", fontNormal)
                    val paraNisnSiswa = Paragraph("NISN : ${siswaByNis.nis}", fontNormal)
                    val paraNamaKelasSiswa = Paragraph("Nama : ${classByNis.name}", fontNormal)
                    paraNamaSiswa.alignment = Element.ALIGN_LEFT
                    paraNamaSiswa.font = fontNormal
                    paraNisnSiswa.alignment = Element.ALIGN_LEFT
                    paraNisnSiswa.font = fontNormal
                    paraNamaKelasSiswa.alignment = Element.ALIGN_LEFT
                    paraNamaKelasSiswa.font = fontNormal

                    for (header in Constanta.headerTable) {
                        val cell = PdfPCell()
                        cell.grayFill = 0.9f
                        cell.phrase = Phrase(header, fontHeader)
                        cell.horizontalAlignment = Element.ALIGN_CENTER
                        tableRaport.addCell(cell)
                    }
                    tableRaport.completeRow()

                    raportViewModel.getListRaportSiswa(
                        session.token.toString(),
                        classId,
                        siswaId
                    ).observe(viewLifecycleOwner) {
                        if (it.isSuccessful) {
                            val raport = it.body()?.data!!
                            raport.forEach { raport ->
                                val nilaiSikap = raport.nilaiSikap?.times(0.3)!!.toFloat()
                                val nilaiUts = raport.nilaiUTS!!.toFloat()
                                val nilaiUas = raport.nilaiUAS!!.toFloat()
                                var nilaiAkhir = 0f
                                val nilaiReview =
                                    nilaiUts.plus(nilaiUas).div(2).times(0.4).toFloat()

                                val listTugas = arrayListOf<Tugas>()
                                var nilaiKeterampilan = 0f
                                raport.tugasDetail?.forEach { tugas ->
                                    if (tugas != null) {
                                        listTugas.add(tugas)
                                        nilaiKeterampilan += tugas.nilai!!
                                        Log.d(TAG, "viewAsOrangTua: ${tugas.nilai}")
                                    }
                                }
                                if (listTugas.size > 0) {
                                    nilaiKeterampilan =
                                        nilaiKeterampilan.div(listTugas.size)
                                }
                                nilaiKeterampilan = nilaiKeterampilan.times(0.3).toFloat()
                                nilaiAkhir = nilaiKeterampilan + nilaiReview + nilaiSikap
                                val namaMapel = Phrase(raport.mapleDetail?.getOrNull(0)?.name, fontNormal)
                                Log.d(TAG, "viewAsOrangTua: $namaMapel")
                                tableRaport.addCell(namaMapel)
                                val centerCell = PdfPCell()
                                centerCell.horizontalAlignment = Element.ALIGN_CENTER
                                val nilaiHuruf = Phrase(nilaiAkhir.parseIntToChar(), fontNormal)
                                centerCell.phrase = nilaiHuruf
                                tableRaport.addCell(centerCell)
                                val rerata = Phrase(nilaiAkhir.toString(), fontNormal)
                                centerCell.phrase = rerata
                                tableRaport.addCell(centerCell)
                                val deskripsi = Phrase(raport.deskripsi, fontNormal)
                                tableRaport.addCell(deskripsi)
                                tableRaport.completeRow()
                            }
                            document.addTitle("Raport Siswa Demo")
                            document.add(headerRaport)
                            document.add(paraNamaSiswa)
                            document.add(paraNisnSiswa)
                            document.add(paraNamaKelasSiswa)
                            document.add(Paragraph("\n"));
                            document.add(tableRaport)
                        } else {
                            requireContext().showToast(it.message())
                        }
                        document.close()
                    }
                } catch (e: DocumentException) {
                    e.printStackTrace()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } finally {
                    requireContext().showToast(
                        "Raport ${namaSiswa}-${date}.pdf was successfully created"
                    )
                }
            }

            siswaViewModel.getSiswaByNIS(session.token.toString(), session.username.toString())
                .observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        siswaId = it.body()?.data!!.id
                        namaSiswa = it.body()?.data?.name.toString()
                        siswaByNis = it.body()?.data!!
                    } else {
                        requireContext().showToast(it.message())
                    }
                }



            kelasViewModel.getClassByNis(session.token.toString(), session.username.toString())
                .observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        mtvRaportMapel.text = it.body()?.data!!.mapelDetail?.size.toString()
                        mtvRaportSiswa.text = it.body()?.data!!.siswaId?.size.toString()
                        mtvRaportTitletotal.text = "Total Mapel & Siswa ${it.body()?.data!!.name}"
                        classId = it.body()?.data!!.id.toString()
                        semester = it.body()?.data?.semester.toString()
                        tahunAjaran = it.body()?.data?.tahunAjaran.toString()
                        classByNis = it.body()?.data!!
                        it.body()?.data!!.mapelDetail?.forEach { mapel ->
                            if (mapel != null) {
                                dataMapel.add(mapel)
                            }
                        }

                        includeRvMapel.rvRvlayoutContainer.apply {
                            val adapter = RvAdapter<Mapel>(
                                "mapel-raport-orangtua",
                                OperationsTypeRv.READ,
                                mainNavController
                            )
                            adapter.setData(dataMapel)
                            adapter.notifyDataSetChanged()
                            this.adapter = adapter
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        }
                    } else {
                        requireContext().showToast(it.message())
                    }
                }
        }
    }

    fun viewAsGuru() {
        with(binding) {
            ivRaportPerson.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString(Constanta.ID, session.id)
                bundle.putString(Constanta.ROLE, "admin")
                mainNavController.navigate(R.id.action_baseFragment_to_addGuruFragment, bundle)
            }

            kelasViewModel.getClassByIdGuru(session.token.toString(), session.id.toString())
                .observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        mtvRaportMapel.text = it.body()?.data!!.mapelDetail?.size.toString()
                        mtvRaportSiswa.text = it.body()?.data!!.siswaId?.size.toString()
                        mtvRaportTitletotal.text = "Total Mapel & Siswa ${it.body()?.data!!.name}"
                        classId = it.body()?.data!!.id.toString()

                        it.body()?.data!!.mapelDetail?.forEach { mapel ->
                            if (mapel != null) {
                                dataMapel.add(mapel)
                            }
                        }

                        includeRvMapel.rvRvlayoutContainer.apply {
                            val adapter = RvAdapter<Mapel>(
                                "mapel-raport",
                                OperationsTypeRv.READ,
                                mainNavController
                            )
                            adapter.setData(dataMapel)
                            adapter.notifyDataSetChanged()
                            this.adapter = adapter
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        }
                    } else {
                        requireContext().showToast(it.message())
                    }
                }
        }
    }

    @Subscribe
    fun navigateToListRaportFragment(commonParams: CommonParams) {
        val bundle = bundleOf()
        bundle.putString(Constanta.CLASS_ID, classId)
        bundle.putString(Constanta.MAPEL_ID, commonParams.id)
        bundle.putString(Constanta.MAPEL_NAME, commonParams.username)
        mainNavController.navigate(R.id.action_baseFragment_to_listRaportFragment, bundle)
    }

    @Subscribe
    fun navigateToDetailRaportFragment(eventToDetailRaport: EventToDetailRaport) {
        val bundle = bundleOf()
        bundle.putString(Constanta.SISWA_ID, siswaId)
        bundle.putString(Constanta.CLASS_ID, classId)
        bundle.putString(Constanta.MAPEL_ID, eventToDetailRaport.id)
        bundle.putString(Constanta.MAPEL_NAME, eventToDetailRaport.username)
        bundle.putString(Constanta.ROLE, role)
        mainNavController.navigate(R.id.action_baseFragment_to_detailRaportFragment, bundle)
    }

}