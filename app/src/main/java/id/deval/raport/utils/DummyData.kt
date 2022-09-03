package id.deval.raport.utils

import id.deval.raport.db.models.*
import id.deval.raport.db.response.DetailSiswaItem

class DummyData {
    fun setDummyGuru(): ArrayList<Account> {
        val dataGuru = arrayListOf<Account>()
        for (n in 1..5) {
            val guru = Account(
                "980213088132",
                "ArogaN61",
                "guru",
                "Jln. S. Sutoyo",
                "ArogaN61",
                "01288312",
                "Ali",
                "ali@gmail.com",
                "ArogaN61"
            )
            dataGuru.add(guru)
        }
        return dataGuru
    }

    fun setDummyDataSiswa(): ArrayList<Siswa> {
        val dataSiswa = arrayListOf<Siswa>()
        for (n in 1..5) {
            val siswa = Siswa(
                "980213088132",
                "Jln Sutoyo",
                "-",
                "Laki-laki",
                "-",
                "17 Juni 1999",
                "Jln. Wali",
                "Islam",
                "Ali",
                "asdasdas",
                "-",
                "Syam",
                "0192839",
                "Ali Asgar",
                "60200117039",
                "Onto",
                "PNS",
                "PNS"
            )
            dataSiswa.add(siswa)
        }
        return dataSiswa
    }

    fun setDummyDataKelas(): ArrayList<Kelas> {
        val dataKelas = arrayListOf<Kelas>()
        for (n in 1..5) {
            val siswa = Kelas(
                "980213088132",
                arrayListOf(),
                "Kelas IV A",
                arrayListOf(),
                "1",
                "2021/2022",
                "912039812m3",
                )
            dataKelas.add(siswa)
        }
        return dataKelas
    }

    fun setDummyDataMapel(): ArrayList<Mapel> {
        val dataMapel = arrayListOf<Mapel>()
        for (n in 1..5) {
            val siswa = Mapel(
                "asdasdasd",
                "Matematika",
                "Khusus"
                )
            dataMapel.add(siswa)
        }
        return dataMapel
    }

//    fun setDummyDataTanggal(): ArrayList<String> {
//        val dataString = arrayListOf<String>()
//        for (n in 1..5) {
//            dataString.add("Jumat, 18 Juni 2019")
//        }
//        return dataString
//    }

    fun setDummyAttendance(): ArrayList<Attendance>{
        val dataAttendance = arrayListOf<Attendance>()
        for (n in 1..5){
            val attendanceItem = AttendanceItem(
                "Izin",
                "siswa1"
            )

            val attendanceItem1 = AttendanceItem(
                "Sakit",
                "siswa2"
            )
            val attendanceItem2 = AttendanceItem(
                "Hadir",
                "siswa3"
            )

            val attendanceItem3 = AttendanceItem(
                "Tanpa Ket.",
                "siswa4"
            )

            val attendance = Attendance(
                "12938123",
                "asd;lkasd",
                "mapelasdasd",
                "2$n Juni 2022",
                arrayListOf(attendanceItem, attendanceItem1, attendanceItem2, attendanceItem3)
            )
            dataAttendance.add(attendance)
        }
        return dataAttendance
    }

    fun setDummyDataAbsenSiswa(): ArrayList<DetailSiswaItem> {
        val dataString = arrayListOf<DetailSiswaItem>()
        for (n in 1..5) {
            val absen = DetailSiswaItem(
                "Ali",
                "60200117039",
                "Hadir"
            )
            dataString.add(absen)
        }
        return dataString
    }

    fun setDummyDataGrowth(): ArrayList<Growth> {
        val dataString = arrayListOf<Growth>()
        for (n in 1..5) {
            val absen = Growth(
                64,
                "60200117039",
                "17 8 2022",
                "Selama jantungku masih berdetak,  berdetak aksksadwk askjda sdkajsd aksdja skdjasd aksjda sdkajsd aksdja skdjasdkas djkasd",
                132,
                12
            )
            dataString.add(absen)
        }
        return dataString
    }

    fun setDummyDataNote(): ArrayList<Note> {
        val dataString = arrayListOf<Note>()
        for (n in 1..5) {
            val absen = Note(
                "Selama jantungku masih berdetak aksksadwk askjda sdkajsd aksdja skdjasd aksjda sdkajsd aksdja skdjasdkas djkasd ",
                "60200117039",
                "17 8 2022",
            )
            dataString.add(absen)
        }
        return dataString
    }
    fun setDummyDataTugas(): ArrayList<Tugas> {
        val dataString = arrayListOf<Tugas>()
        for (n in 1..5) {
            val absen = Tugas(
                "Tugas $n",
                60,
                "17 8 2022",
            )
            dataString.add(absen)
        }
        return dataString
    }


}